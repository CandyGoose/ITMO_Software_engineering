package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import Common.exception.DisconnectInitException;
import Common.util.Request;
import Common.util.Response;
import Server.Commands.*;
import Server.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

/**
 * Класс для установки соединения с клиентами.
 */
public class ServerApp {
    /**
     * Сканер для чтения ввода.
     */
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Файловый менеджер.
     */
    public static final FileManager fileManager = new FileManager(Server.fileName);

    /**
     * Менеджер коллекции.
     */
    public static CollectionManager collectionManager = new CollectionManager();

    /**
     * Состояние сервера.
     */
    public static boolean running = true;

    /**
     * Логгер для записи сообщений о работе сервера.
     */
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/log.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            ServerApp.logger.addHandler(fileHandler);
        } catch (IOException e) {
            ServerApp.logger.warning("Не удалось открыть файл журнала: " + e.getMessage());
        } catch (SecurityException e) {
            ServerApp.logger.warning("Отказано в доступе при открытии файла журнала: " + e.getMessage());
        } catch (Exception e) {
            ServerApp.logger.warning("Произошла ошибка при открытии обработчика файла журнала: " + e.getMessage());
        }
    }

    /**
     * Командный менеджер, содержащий список команд для обработки запросов клиентов.
     */
    public static CommandManager commandManager = new CommandManager(
            new AddCommand(collectionManager),
            new ClearCommand(collectionManager),
            new ExecuteScriptCommand(),
            new ExitCommand(),
            new HelpCommand(),
            new InfoCommand(collectionManager),
            new PrintDescendingCommand(collectionManager),
            new PrintFieldDescendingAnnualTurnoverCommand(collectionManager),
            new PrintUniqueEmployeesCountCommand(collectionManager),
            new RemoveByIdCommand(collectionManager),
            new RemoveFirstCommand(collectionManager),
            new ShowCommand(collectionManager),
            new ShuffleCommand(collectionManager),
            new SortCommand(collectionManager),
            new UpdateByIdCommand(collectionManager)
            );

    /**
     * Стандартный порт.
     */
    protected static int PORT = 65435;

    /**
     * Метод для запуска сервера.
     * @param args аргументы командной строки.
     * @param serverSocket объект ServerSocket, прослушивающий порт.
     * @throws IOException если возникли проблемы с вводом-выводом.
     */
    static void startServer(String[] args, ServerSocket serverSocket) throws IOException {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (running) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        ServerApp.logger.info("Подключение клиента.");
                        startSelectorLoop(clientSocket, serverSocket);
                    } catch (SocketException e) {
                        if (e.getMessage().contains("Connection reset"));
                        else { ServerApp.logger.severe(e.getMessage()); }
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        ServerApp.logger.severe(e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            ServerApp.logger.warning("Не удается принять клиентское соединение: " + e.getMessage());
        }
    }

    /**
     * Метод, который запускает бесконечный цикл обработки запросов клиента.
     * @param socket сокет для подключения клиента.
     * @param serverSocket серверный сокет для принятия клиентских подключений.
     * @throws IOException если возникает ошибка ввода/вывода при работе с сокетами.
     * @throws ClassNotFoundException если класс не был найден при чтении объекта из потока.
     * @throws InterruptedException если поток был прерван во время ожидания ответа от клиента.
     */
    private static void startSelectorLoop(Socket socket, ServerSocket serverSocket) throws IOException, ClassNotFoundException, InterruptedException {
        while (socket.isConnected()) {
            startIteratorLoop(socket, serverSocket);
        }
    }

    /**
     * Метод, который обрабатывает запросы клиента.
     * @param socket сокет для подключения клиента.
     * @param serverSocket серверный сокет для принятия клиентских подключений.
     * @throws IOException если возникает ошибка ввода/вывода при работе с сокетами.
     * @throws ClassNotFoundException если класс не был найден при чтении объекта из потока.
     */
    private static void startIteratorLoop(Socket socket, ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        boolean isClientDisconnected = false;
        try {
            ServerSocketIO socketIO = new ServerSocketIO(socket);
            Request request = (Request) socketIO.receive();
            AbstractCommand command = ServerApp.commandManager.initCommand(request);
            ServerApp.logger.info("Сервер получил команду [" + request.getCommandName() + "].");
            if (request.getCommandName().equals("exit")) {
                ServerApp.logger.info("Отключение клиента.");
                try {
                    socket.close();
                } catch (IOException e) {
                    ServerApp.logger.warning("Ошибка при закрытии сокета: " + e.getMessage());
                }
            } else {
                Response response = RequestBuilder.build(command, request);
                socketIO.send(response);
                ServerApp.logger.info("Сервер отправил ответ клиенту.");
            }
        } catch (DisconnectInitException e) {
            socket.close();
            serverSocket.close();
            ServerApp.logger.severe("Работа сервера завершена.");
            System.exit(1);
        } catch (IOException e) {
            if (e.getMessage().equals("Connection reset")) {
                isClientDisconnected = true;
                ServerApp.logger.warning("Клиент внезапно отключился."); }
        } finally {
            if (isClientDisconnected) {
                try {
                    socket.close();
                } catch (IOException e) {
                    ServerApp.logger.warning("Ошибка при закрытии сокета: " + e.getMessage());
                }
            }
        }
    }
}
