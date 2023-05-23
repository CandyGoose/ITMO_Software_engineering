package server;

import common.network.Request;
import common.network.Response;
import org.springframework.stereotype.Component;
import common.util.CommandHandler;
import common.util.CollectionManager;
import common.util.UserManager;
import server.util.ObjectSocketWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Класс, представляющий серверный экземпляр.
 */
@Component
public class ServerInstance {
    /**
     * Таймаут сокета.
     */
    private static final int SOCKET_TIMEOUT = 10;

    /**
     * Пул потоков для обработки запросов.
     */
    private final ExecutorService requestHandlerPool = Executors.newFixedThreadPool(10);

    /**
     * Пул потоков для отправки ответов.
     */
    private final ExecutorService responseSenderPool = Executors.newFixedThreadPool(10);

    /**
     * Обработчик команд.
     */
    private CommandHandler ch;

    /**
     * Менеджер коллекции.
     */
    private CollectionManager cm;

    /**
     * Входной поток для чтения с консоли.
     */
    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Менеджер пользователей.
     */
    private final UserManager users;

    /**
     * Логгер для записи логов.
     */
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/log.log", true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());
            ServerInstance.LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            ServerInstance.LOGGER.warning("Не удалось открыть файл журнала.");
        } catch (SecurityException e) {
            ServerInstance.LOGGER.warning("Отказано в доступе при открытии файла журнала.");
        } catch (Exception e) {
            ServerInstance.LOGGER.warning("Произошла ошибка при открытии обработчика файла журнала.");
        }
    }

    /**
     * Конструктор класса ServerInstance.
     *
     * @param cm менеджер коллекции
     * @param users менеджер пользователей
     */
    public ServerInstance(CollectionManager cm, UserManager users) {
        ch = CommandHandler.standardCommandHandler(cm, users);
        this.cm = cm;
        this.users = users;
    }

    /**
     * Принимает команды с консоли.
     *
     * @return true, если введена команда завершения работы сервера, иначе false
     * @throws IOException если возникла ошибка ввода/вывода
     */
    private boolean acceptConsoleInput() throws IOException {
        if (System.in.available() > 0) {
            String command = in.readLine();
            if (command.equals("exit")) {
                System.out.println("Завершение работы сервера.");
                return true;
            } else {
                System.out.println("Такой команды не существует. Напишите: exit.");
            }
        }
        return false;
    }

    /**
     * Запускает сервер на указанном порту.
     *
     * @param port порт для прослушивания
     * @throws IOException если возникла ошибка ввода/вывода
     */
    public void run(int port) throws IOException {
        Set<ClientThread> clients = new HashSet<>();

        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(SOCKET_TIMEOUT);

            LOGGER.info("Сервер прослушивает порт " + port);

            while (true) {
                clients.removeIf(x -> !x.isRunning());

                if (acceptConsoleInput()) {
                    clients.forEach(ClientThread::stop);
                    requestHandlerPool.shutdown();
                    responseSenderPool.shutdown();
                    return;
                }

                try {
                    while (true) {
                        Socket newClient = socket.accept();
                        newClient.setSoTimeout(SOCKET_TIMEOUT);
                        LOGGER.info("Получено соединение от " + newClient.getRemoteSocketAddress());
                        ClientThread client = new ClientThread(new ObjectSocketWrapper(newClient));
                        clients.add(client);
                        client.start();
                    }
                } catch (SocketTimeoutException ignore) {}
            }
        }
    }

    /**
     * Класс, представляющий поток для клиента.
     */
    private class ClientThread {
        /**
         * Объект-обертка для сокета.
         */
        private final ObjectSocketWrapper socket;

        /**
         * Поток выполнения.
         */
        private final Thread thread;

        /**
         * Флаг, указывающий, выполняется ли поток.
         */
        private boolean running = false;

        /**
         * Конструктор класса ClientThread.
         *
         * @param socket объект-обертка для сокета клиента
         */
        ClientThread(ObjectSocketWrapper socket) {
            this.socket = socket;
            this.thread = new Thread(this::handleRequests);
            this.thread.setName("Клиент " + this.socket.getSocket().getRemoteSocketAddress());
        }

        /**
         * Проверяет, выполняется ли поток клиента.
         *
         * @return true, если поток выполняется, иначе false
         */
        boolean isRunning() {
            return running;
        }

        /**
         * Запускает поток клиента.
         */
        void start() {
            thread.start();
            running = true;
        }

        /**
         * Останавливает поток клиента.
         */
        void stop() {
            running = false;
            LOGGER.info("Клиент " + socket.getSocket().getRemoteSocketAddress() + " отключился.");
            try {
                socket.getSocket().close();
            } catch (IOException e) {
                LOGGER.warning("Не удалось закрыть соединение с клиентом.");
            }
        }

        /**
         * Обрабатывает запросы клиента.
         */
        void handleRequests() {
            while (running) {
                try {
                    if (socket.checkForMessage()) {
                        Object received = socket.getPayload();

                        if (received instanceof Request) {
                            Request request = (Request) received;
                            if (!request.getCommandName().equals("show")) {
                                LOGGER.info("Запрос от " + socket.getSocket().getRemoteSocketAddress() + " для команды \"" + request.getCommandName() + '"');
                            }
                            requestHandlerPool.submit(() -> {
                                Response resp = ch.handleRequest(request, users);
                                responseSenderPool.submit(() -> {
                                    if (socket.sendMessage(resp)) {
                                        // LOGGER.info("Отправлен ответ для " + socket.getSocket().getRemoteSocketAddress());
                                    } else {
                                        LOGGER.severe("Не удалось отправить ответ на " + socket.getSocket().getRemoteSocketAddress());
                                        stop();
                                    }
                                });
                            });
                        } else {
                            LOGGER.warning("Получен неверный запрос от " + socket.getSocket().getRemoteSocketAddress());
                        }

                        socket.clearInBuffer();
                    }
                } catch (IOException e) {
                    stop();
                }
            }
        }
    }
}
