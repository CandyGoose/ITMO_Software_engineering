package server;

import common.exceptions.ClosingSocketException;
import common.exceptions.ConnectionErrorException;
import common.exceptions.OpeningServerSocketException;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import common.utility.Outputer;
import server.utility.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *  Класс Server - класс, представляющий сервер.
 */
public class Server {

    /**
     * Порт, на котором работает сервер.
     */
    private final int port;

    /**
     * Таймаут соединения в миллисекундах.
     */
    private final int soTimeout;

    /**
     * Сокет сервера.
     */
    private ServerSocket serverSocket;

    /**
     * Обработчик запросов.
     */
    private final RequestHandler requestHandler;

    /**
     * Конструктор класса Server.
     * @param port порт, на котором будет работать сервер.
     * @param soTimeout таймаут соединения в миллисекундах.
     * @param requestHandler обработчик запросов.
     */
    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }
    /**
     * Метод, запускающий сервер.
     */
    public void run() {
        try{
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus){
                try (Socket clientSocket = connectToClient()){
                    processingStatus = processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException exception){
                    break;
                } catch (IOException exception){
                    Outputer.printError("Произошла ошибка при попытке разорвать соединение с клиентом.");
                    App.logger.severe("Произошла ошибка при попытке разорвать соединение с клиентом.");
                }
            }
            stop();
        } catch (OpeningServerSocketException exception){
            Outputer.printError("Сервер не может быть запущен.");
            App.logger.severe("Сервер не может быть запущен.");
        }
    }

    /**
     * Метод, останавливающий сервер.
     */
    private void stop() {
        try{
            App.logger.info("Завершение работы сервера.");
            if(serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            Outputer.printLn("Работа сервера успешно завершена.");
        } catch (ClosingSocketException exception) {
            Outputer.printError("Выключить еще не запущенный сервер невозможно.");
            App.logger.severe("Выключить еще не запущенный сервер невозможно.");
        } catch (IOException exception) {
            Outputer.printError("Произошла ошибка при выключении сервера.");
            App.logger.severe("Произошла ошибка при выключении сервера.");
        }
    }

    /**
     * Метод, открывающий серверный сокет.
     * @throws OpeningServerSocketException если произошла ошибка при попытке открытия серверного сокета
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try{
            App.logger.info("Запуск сервера...");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
            App.logger.info("Сервер успешно запущен.");
        } catch (IllegalArgumentException exception) {
            Outputer.printError("Порт '" + port + "' выходит за пределы возможных значений.");
            App.logger.severe("Порт '" + port + "' выходит за пределы возможных значений.");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputer.printError("Произошла ошибка при попытке использовать порт '" + port + "'.");
            App.logger.severe("Произошла ошибка при попытке использовать порт '" + port + "'.");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * Метод для установки соединения с клиентом.
     * @return экземпляр Socket, устанавливающий соединение с клиентом
     * @throws ConnectionErrorException если произошла ошибка при подключении к клиенту
     * @throws SocketTimeoutException если превышено время ожидания подключения
     */
    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try{
            Outputer.printLn("Порт прослушивания '" + port + "'...");
            App.logger.info("Порт прослушивания '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Outputer.printLn("Соединение с клиентом успешно установлено.");
            App.logger.info("Соединение с клиентом успешно установлено.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            Outputer.printError("Превышено время ожидания подключения.");
            App.logger.warning("Превышено время ожидания подключения.");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            Outputer.printError("Произошла ошибка при подключении к клиенту.");
            App.logger.severe("Произошла ошибка при подключении к клиенту.");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Метод для обработки запросов клиента и отправки ответов.
     * @param clientSocket экземпляр Socket, устанавливающий соединение с клиентом
     * @return true, если клиент был успешно обработан и отключен от сервера, false в случае ошибки обработки запросов
     */
    private boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestHandler.handle(userRequest);
                App.logger.info("Запрос '" + userRequest.getCommandName() + "' был успешно обработан.");
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while(responseToUser.getResponseResult() != ResponseResult.SERVER_EXIT);
            return false;
        } catch (ClassNotFoundException exception){
            Outputer.printError("Произошла ошибка при чтении полученных данных.");
            App.logger.severe("Произошла ошибка при чтении полученных данных.");
        } catch (InvalidClassException | NotSerializableException exception) {
            Outputer.printError("Произошла ошибка при отправке данных клиенту.");
            App.logger.severe("Произошла ошибка при отправке данных клиенту.");
        } catch (IOException exception) {
            if (userRequest == null) {
                Outputer.printError("Неожиданное отключение от клиента.");
                App.logger.warning("Неожиданное отключение от клиента.");
            } else {
                Outputer.printLn("Клиент успешно отключен от сервера.");
                App.logger.info("Клиент успешно отключен от сервера.");
            }
        }
        return true;
    }
}