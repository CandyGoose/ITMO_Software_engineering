package server;

import server.commands.*;
import server.utility.*;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Класс App - основной класс приложения.
 * Запускает сервер, загружает коллекцию из файла, инициализирует команды и менеджеры.
 */
public class App {

    /**
     * Порт, на котором будет работать сервер.
     */
    public static final int PORT = 8080;

    /**
     * Таймаут соединения в миллисекундах.
     */
    public static final int CONNECTION_TIMEOUT = 60 * 1000;

    /**
     * Логгер для класса Server.
     */
    public static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Основной метод приложения. Загружает коллекцию из файла, инициализирует команды и менеджеры, запускает сервер.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "EN"));
        String filename = CollectionFileManager.getName();
        CollectionFileManager collectionFileManager = new CollectionFileManager(filename);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new AddCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(collectionManager),
                new HelpCommand(),
                new HistoryCommand(),
                new InfoCommand(collectionManager),
                new PrintDescendingCommand(collectionManager),
                new PrintFieldDescendingAnnualTurnoverCommand(collectionManager),
                new PrintUniqueEmployeesCountCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new RemoveFirstCommand(collectionManager),
                new ShowCommand(collectionManager),
                new ShuffleCommand(collectionManager),
                new SortCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new ServerExitCommand(collectionManager)
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
