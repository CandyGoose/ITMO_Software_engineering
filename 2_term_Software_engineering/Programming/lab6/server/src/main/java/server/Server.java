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


public class Server {
    private final int port;
    private final int soTimeout;
    private ServerSocket serverSocket;
    private final RequestHandler requestHandler;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }

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

    private void stop() {
        try{
            App.logger.info("Завершение работы сервера...");
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
        }catch (InvalidClassException | NotSerializableException exception) {
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