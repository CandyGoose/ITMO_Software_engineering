package client;

import client.utility.UserHandler;
import client.utility.ConnectionInitializer;

import java.util.Scanner;

/**
 * Класс для запуска клиентской части приложения.
 *
 * @author Касьяненко Вера (P3120)
 */
public class App {
    /**
     * PS1 - символ первичного приглашения для ввода команд в консоли
     */
    public static final String PS1 = "$ ";

    /**
     * PS2 - символ вторичного приглашения для ввода команд в консоли
     */
    public static final String PS2 = "> ";

    /**
     * Главный метод для запуска приложения. Производит инициализацию адреса подключения к серверу
     * создает экземпляр объекта клиента и запускает его.
     * @param args аргументы командной строки (адрес сервера и номер порта)
     */
    public static void main(String[] args) {
        ConnectionInitializer connectionInitializer = new ConnectionInitializer(args);
        if (connectionInitializer.getHost() == null || connectionInitializer.getPort() == 0) return;

        Scanner userScanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler(userScanner);
        Client client = new Client(connectionInitializer.getHost(),
                connectionInitializer.getPort(),
                ConnectionInitializer.getReconnectionTimeout(),
                connectionInitializer.getMaxReconnectionAttempts(),
                userHandler);
        client.run();
        userScanner.close();
    }
}