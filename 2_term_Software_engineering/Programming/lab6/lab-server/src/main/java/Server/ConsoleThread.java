package Server;

import Common.data.Organization;
import Common.util.TextWriter;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleThread extends Thread {
    private static final Scanner scanner = ServerApp.scanner;
    public volatile boolean running = true;

    @Override
    public void run() {
        try {
            ServerApp.logger.info("Консоль запущена.");
            while (running) {
                String line = scanner.nextLine();
                if ("save".equalsIgnoreCase(line)) {
                    ServerApp.fileManager.writeCollection((LinkedList<Organization>) ServerApp.collectionManager.getCollection());
                }
                if ("exit".equalsIgnoreCase(line)) {
                    ServerApp.logger.info("Работа сервера завершена.");
                    shutdown();
                    System.exit(0);
                }
            }

        } catch (NoSuchElementException e) {
            TextWriter.printErr("Принудительное завершение работы.");
            ServerApp.logger.warning("Работа сервера завершена.");
            shutdown();
            System.exit(1);
        }
    }

    public void shutdown() {
        this.running = false;
    }
}
