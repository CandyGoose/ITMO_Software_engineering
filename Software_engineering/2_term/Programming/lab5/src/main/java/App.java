import commands.*;
import managers.*;

import java.util.Scanner;
import java.util.NoSuchElementException;


/**
 * Главный класс, запускающий приложение
 * @author Касьяненко Вера (P3120)
 * @version 2.0
 */

public class App {
    public static void main(String[] args) {

        System.out.println("Добро пожаловать!\n\nЧтение данных...");

        try (Scanner userScanner = new Scanner(System.in)) {

            String filename = FileManager.getName();
            CollectionManager collectionManager = new CollectionManager();
            OrganizationAsker organizationAsker = new OrganizationAsker(collectionManager, userScanner);
            FileManager fileManager = new FileManager(filename);
            CommandManager commandManager = new CommandManager(
                    new AddCommand(collectionManager, organizationAsker),
                    new ClearCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new HistoryCommand(),
                    new InfoCommand(collectionManager),
                    new PrintFieldDescendingAnnualTurnoverCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new PrintUniqueEmployeesCountCommand(collectionManager),
                    new RemoveFirstCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new SortCommand(collectionManager),
                    new SaveCommand(fileManager, collectionManager),
                    new ShowCommand(collectionManager),
                    new ShuffleCommand(collectionManager),
                    new UpdateCommand(collectionManager, organizationAsker),
                    new HelpCommand()
            );

            fileManager.setFilename(filename);
            if (fileManager.readCollection().size() != 0) {
                Console.printLn("Используемый файл: " + filename);
                collectionManager.setCollection(fileManager.readCollection());
                Console.printLn("Коллекция была успешно считана из файла.");
            } else if (filename.length() != 0L) {
                Console.printLn("Используемый файл: " + filename + "\nКоллекция на данный момент пуста.");}
            Console console = new Console(commandManager, userScanner, organizationAsker);
            console.interactiveMode();

        } catch (NoSuchElementException e) {
            Console.printError("Входной поток закрыт, остановка приложения.");
            System.exit(0);
        }
    }
}

