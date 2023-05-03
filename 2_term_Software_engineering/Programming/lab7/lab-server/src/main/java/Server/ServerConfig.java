package Server;


import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import Common.data.Organization;
import Common.exception.DatabaseException;
import Server.Commands.*;
import Server.db.DBManager;
import Server.db.UsersManager;
import Server.util.CollectionManager;
import Server.util.CommandManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 Класс, представляющий конфигурацию сервера.
 */
public class ServerConfig {

    /**
     Переменная, указывающая на то, работает ли сервер.
     */
    public static boolean isRunning = true;

    /**
     Объект класса Scanner для считывания ввода пользователя.
     */
    public static final Scanner scanner = new Scanner(System.in);

    /**
     Объект класса CollectionManager для работы с коллекцией элементов.
     */
    public static CollectionManager collectionManager = new CollectionManager();

    /**
     Порт, используемый сервером.
     */
    public static int PORT = 65435;

    /**
     Объект класса DBManager для работы с базой данных.
     */
    public static DBManager dbManager;

    /**
     Объект класса UsersManager для работы с пользователями.
     */
    public static UsersManager usersManager;

    /**
     * Логгер для записи сообщений о работе сервера.
     */
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Server.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/log.log", true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());
            ServerConfig.logger.addHandler(fileHandler);
        } catch (IOException e) {
            ServerConfig.logger.warning("Не удалось открыть файл журнала: " + e.getMessage());
        } catch (SecurityException e) {
            ServerConfig.logger.warning("Отказано в доступе при открытии файла журнала: " + e.getMessage());
        } catch (Exception e) {
            ServerConfig.logger.warning("Произошла ошибка при открытии обработчика файла журнала: " + e.getMessage());
        }
    }

    static {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); // Создание объекта ApplicationContext для конфигурации приложения
            dbManager = context.getBean(DBManager.class);
            usersManager = context.getBean(UsersManager.class); // Получение объектов DBManager и UsersManager из контекста приложения
            collectionManager.setOrganizationCollection(dbManager.loadCollection()); // Загрузка коллекции элементов из базы данных
        } catch (DatabaseException e) {
            logger.warning(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Объект класса CommandManager для работы с командами.
     */
    public static CommandManager commandManager = new CommandManager(
            new AddCommand(collectionManager, dbManager),
            new ClearCommand(collectionManager, dbManager),
            new ExecuteScriptCommand(),
            new ExitCommand(),
            new HelpCommand(),
            new InfoCommand(collectionManager),
            new PrintDescendingCommand(collectionManager, dbManager),
            new PrintFieldDescendingAnnualTurnoverCommand(collectionManager),
            new PrintUniqueEmployeesCountCommand(collectionManager),
            new RemoveByIdCommand(collectionManager, dbManager),
            new RemoveFirstCommand(collectionManager, dbManager),
            new ShowCommand(collectionManager, dbManager),
            new ShuffleCommand(collectionManager, dbManager),
            new SortCommand(collectionManager, dbManager),
            new UpdateByIdCommand(collectionManager, dbManager)
    );

    /**
     * Метод для изменения переменной isRunning, указывающего на то, работает ли сервер.
     */
    public static void toggleStatus() {
        isRunning = !isRunning;
    }

}
