import commands.*;
import managers.*;

import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Главный класс, запускающий приложение.
 * Класс, который содержит метод main, отвечающий за запуск приложения. Создаётся объект класса FileManager для
 * работы с файлами, объект класса CollectionManager для работы с коллекцией, объект класса OrganizationAsker для
 * работы с вводом данных, объект класса CommandManager для обработки команд, объект класса Console для работы с
 * консолью.
 *
 * @author Касьяненко Вера (P3120)
 * @version 2.0
 */

public class App {
    public static void main(String[] args) {

        System.out.println("Добро пожаловать!\nЧтение данных...");

        try {
            String filename = FileManager.getName();
            Scanner userScanner = new Scanner(System.in);
            CollectionManager collectionManager = new CollectionManager();
            OrganizationAsker organizationAsker = new OrganizationAsker(collectionManager, userScanner);
            FileManager fileManager = new FileManager(filename);
            CommandManager commandManager = new CommandManager(
                    new AddCommand(collectionManager, organizationAsker),
                    new ClearCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new HelpCommand(),
                    new HistoryCommand(),
                    new InfoCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new PrintFieldDescendingAnnualTurnoverCommand(collectionManager),
                    new PrintUniqueEmployeesCountCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new RemoveFirstCommand(collectionManager),
                    new SaveCommand(fileManager, collectionManager),
                    new ShowCommand(collectionManager),
                    new ShuffleCommand(collectionManager),
                    new SortCommand(collectionManager),
                    new UpdateCommand(collectionManager, organizationAsker)

            );

            fileManager.setFilename(filename);
            if (fileManager.readCollection().size() != 0) {
                Console.printLn("Используемый файл: " + filename);
                collectionManager.setCollection(fileManager.readCollection());
            } else if (filename.length() != 0L) {
                Console.printLn("Используемый файл для сохранения: " + filename + "\nКоллекция на данный момент пуста.");}
            Console console = new Console(commandManager, userScanner, organizationAsker);
            console.interactiveMode();
        } catch (NoSuchElementException e) {
            Console.printError("Входной поток закрыт, остановка приложения.");
            System.exit(0);
        }
    }
}

