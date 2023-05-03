package Server.util;

import Common.util.TextWriter;
import Server.ServerConfig;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleThread extends Thread {
    private static final Scanner scanner = ServerConfig.scanner;

    @Override
    public void run() {
        try {
            ServerConfig.logger.info("Консоль запущена.");
            while (ServerConfig.isRunning) {
                String line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line)) {
                    ServerConfig.logger.info("Работа сервера завершена.");
                    System.exit(0);
                } else {
                    TextWriter.printErr("Такой команды не существует. Напишите 'exit'.");
                }
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            ServerConfig.logger.severe("Работа сервера завершена.");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            ServerConfig.logger.warning(e.getMessage());
        }
    }
}
