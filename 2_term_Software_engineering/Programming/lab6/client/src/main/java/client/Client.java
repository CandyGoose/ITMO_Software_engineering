package client;

import client.utility.UserHandler;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;
import common.utility.Outputer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    private final String host;
    private final int port;
    private final int reconnectionTimeout;
    private int reconnectionAttempts;
    private final int maxReconnectionAttempts;
    private final UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }


    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Outputer.printError("Превышено количество попыток подключения.");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        Outputer.printError("Время ожидания подключения'" + reconnectionTimeout +
                                "' находится за пределами возможных значений.");
                        Outputer.printLn("Повторное подключение будет выполнено немедленно.");
                    } catch (Exception timeoutException) {
                        Outputer.printError("Произошла ошибка при попытке дождаться подключения.");
                        Outputer.printLn("Повторное подключение будет выполнено немедленно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            Outputer.printLn("Работа клиента была успешно завершена.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printError("Клиент не может быть запущен.");
        } catch (IOException exception) {
            Outputer.printError("Произошла ошибка при попытке прервать соединение с сервером.");
        }
    }


    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) Outputer.printLn("Повторное подключение к серверу...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            Outputer.printLn("Соединение с сервером было успешно установлено.");
            Outputer.printLn("Ожидание разрешения на обмен данными...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            Outputer.printLn("Разрешение на обмен данными получено.");
        } catch (IllegalArgumentException exception) {
            Outputer.printError("Адрес сервера введен некорректно.");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            Outputer.printError("Произошла ошибка при соединении с сервером.");
            throw new ConnectionErrorException();
        }
    }

    private boolean processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseResult()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                Outputer.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printError("Произошла ошибка при отправке данных на сервер.");
                Outputer.printError(exception);
                Outputer.printError(serverReader);
            } catch (ClassNotFoundException exception) {
                Outputer.printError("Произошла ошибка при чтении полученных данных.");
            } catch (IOException exception) {
                Outputer.printError("Соединение с сервером разорвано.");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        Outputer.printLn("Команда не будет зарегистрирована на сервере.");
                    else Outputer.printLn("Попробуйте повторить команду позднее.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }
}