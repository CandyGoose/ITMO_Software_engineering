package Server;

import Common.util.TextWriter;
import Server.util.FileManager;

import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Главный класс, запускающий приложение сервера.
 * Содержит метод main, отвечающий за запуск приложения.
 *
 * @author Касьяненко Вера (P3120)
 */
public final class Server {
    /**
     * Имя файла, в котором хранится коллекция.
     */
    static String fileName;
    /**
     * Объект, который используется для прослушивания входящих клиентских подключений на сервере.
     */
    private static ServerSocket serverSocket;
    public static void main(String[] args) {
        try {
            fileName = FileManager.getFileName();
            inputPort();
            ServerApp.fileManager.readCollection();
            ConsoleThread consoleThread = new ConsoleThread();
            consoleThread.start();
            ServerApp.startServer(args, serverSocket);
        } catch (IOException e) {
            ServerApp.logger.severe("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод inputPort запрашивает данные для создания соединения.
     */
    private static void inputPort() {
        Scanner sc = ServerApp.scanner;
        TextWriter.printInfoMessage("Вы хотите использовать порт по умолчанию? [y/n]");
        try {
            String s = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if ("n".equals(s)) {
                TextWriter.printInfoMessage("Введите порт (1-65535):");
                String port = sc.nextLine();
                try {
                    int portInt = Integer.parseInt(port);
                    if (portInt > 0 && portInt <= 65535) {
                        ServerApp.PORT = portInt;
                        serverSocket = new ServerSocket(portInt);
                    } else {
                        TextWriter.printErr("Число не входит в установленные пределы, повторите ввод.");
                        inputPort();
                    }
                } catch (IllegalArgumentException e) {
                    TextWriter.printErr("Ошибка при обработке номера, повторите ввод.");
                    inputPort();
                }
            } else if ("y".equals(s)) {
                serverSocket = new ServerSocket(ServerApp.PORT);
            } else {
                TextWriter.printErr("Вы ввели недопустимый символ, попробуйте еще раз.");
                inputPort();
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            ServerApp.logger.info("Работа сервера завершена.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            TextWriter.printErr("Ошибка при обработке номера, повторите ввод.");
            inputPort();
        } catch (BindException e) {
            TextWriter.printErr("Этот порт недоступен. Введите другой.");
            inputPort();
        } catch (IOException e) {
            TextWriter.printErr("Возникли проблемы с IO.");
            inputPort();
        }
    }
}
