package client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import common.util.CommandHandler;
import common.exceptions.CommandArgumentException;
import common.exceptions.CommandNotFoundException;
import common.network.Request;
import common.network.Response;
import common.network.ResponseWithAuthCredentials;
import common.network.ResponseWithException;
import common.network.ResponseWithOrganizations;
import common.network.BasicUserIO;
import common.util.AuthCredentials;
import common.util.TerminalColors;

/**
 * Класс ConsoleClient представляет консольного клиента для взаимодействия с сервером.
 */
public class ConsoleClient {
    /**
     * Таймаут
     */
    private static final int TIMEOUT = 10;

    /**
     * Секунда
     */
    private static final int MILLIS_IN_SECONDS = 1000;

    /**
     * Данные аутентификации
     */
    private AuthCredentials auth = null;

    /**
     * Объект для ввода-вывода
     */
    private BasicUserIO io;

    /**
     * Обработчик команд
     */
    private CommandHandler ch;

    /**
     * Префикс
     */
    private String inputPrefix = "$ ";

    /**
     * Обертка для объекта SocketChannel для взаимодействия с сервером.
     */
    private ObjectSocketChannelWrapper remote;

    /**
     * Адрес
     */
    private SocketAddress addr;

    /**
     * Конструктор класса ConsoleClient.
     *
     * @param addr адрес сервера
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public ConsoleClient(SocketAddress addr) throws IOException {
        this.io = new BasicUserIO();
        this.ch = CommandHandler.standardCommandHandler(null, null);
        this.addr = addr;
    }

    /**
     * Конструктор класса ConsoleClient.
     *
     * @param addr адрес сервера
     * @param io объект для ввода-вывода
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public ConsoleClient(SocketAddress addr, BasicUserIO io) throws IOException {
        this(addr);
        this.io = io;
    }

    /**
     * Метод для установки аутентификационных данных клиента.
     *
     * @param auth аутентификационные данные
     */
    public void setAuth(AuthCredentials auth) {
        this.auth = auth;
    }

    /**
     * Метод для вывода трассировки исключения.
     *
     * @param e исключение
     */
    private void writeTrace(Exception e) {
        Throwable t = e;

        while (t != null) {
            io.writeln(TerminalColors.colorString(t.toString(), TerminalColors.RED));
            t = t.getCause();
        }

        io.writeln("Напишите "
                + TerminalColors.colorString("help [command name]", TerminalColors.GREEN)
                + " чтобы получить больше информации об использовании команд"
        );
    }

    /**
     * Метод для ожидания и получения ответа от сервера.
     *
     * @return ответ от сервера
     * @throws IOException если произошла ошибка ввода-вывода
     */
    private Response waitForResponse() throws IOException {
        int seconds = 0;
        long start = System.currentTimeMillis();

        while (seconds < TIMEOUT) {
            if (remote.checkForMessage()) {
                Object received = remote.getPayload();

                if (received != null && received instanceof Response) {
                    return (Response) received;
                } else {
                    io.writeln("Получен ошибочный ответ от сервера.");
                    break;
                }
            }

            if (System.currentTimeMillis() >= start + (seconds + 1) * MILLIS_IN_SECONDS) {
                io.write('.');
                seconds++;
            }
        }

        io.writeln("Время ожидания истечет после " + TIMEOUT + " секунд.");
        return null;
    }

    /**
     * Метод для обработки ответа от сервера.
     *
     * @param response ответ от сервера
     */
    private void handleResponse(Response response) {
        io.writeln(response.getMessage());

        if (response instanceof ResponseWithOrganizations) {
            ResponseWithOrganizations rwr = (ResponseWithOrganizations) response;

            for (int i = 0; i < rwr.getOrganizationsCount(); i++) {
                io.writeln(rwr.getOrganization(i));
            }
        } else if (response instanceof ResponseWithAuthCredentials) {
            ResponseWithAuthCredentials rwa = (ResponseWithAuthCredentials) response;
            auth = rwa.getAuthCredentials();
            inputPrefix = auth.getLogin() + " > ";
        } else if (response instanceof ResponseWithException) {
            ResponseWithException rwe = (ResponseWithException) response;

            writeTrace(rwe.getException());
        }
    }

    /**
     * Метод для цикла ввода команд клиента.
     */
    private void inputCycle() {
        String input;
        while ((input = io.read(inputPrefix)) != null) {
            try {
                Request request = ch.handleString(input, io, auth);

                if (request != null) {
                    remote.sendMessage(request);
                    Response response = waitForResponse();

                    if (response != null) {
                        handleResponse(response);
                    } else {
                        io.writeln("Запрос не выполнен.");
                    }

                    remote.clearInBuffer();
                }
            } catch (
                CommandNotFoundException
                | CommandArgumentException ignored
            ) {
            } catch (IOException e) {
                io.writeln("Перехвачена ошибка при попытке отправить запрос.");
                return;
            }
        }
    }

    /**
     * Метод для запуска клиента.
     *
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public void run() throws IOException {
        try (SocketChannel socket = SocketChannel.open()) {
            socket.connect(addr);
            socket.configureBlocking(false);
            remote = new ObjectSocketChannelWrapper(socket);

            inputCycle();
        }
    }
}
