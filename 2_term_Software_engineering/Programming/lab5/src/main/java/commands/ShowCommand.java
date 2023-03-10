package commands;

import data.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;

/**
 * Класс "Show Command" выводит все элементы коллекции в виде строки
 */
public class ShowCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса ShowCommand.
     * @param collectionManager менеджер коллекции.
     */
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит на экран пользователя все организации
     *
     * @param argument строка аргументов команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
            for (Organization organization : copyOfCollection) {
                Console.printLn(organization.toString() + "\n============");
            }
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование аргумента '" + argument + "' в команде '" + getName() + "'");
        }
        return false;
    }
}
