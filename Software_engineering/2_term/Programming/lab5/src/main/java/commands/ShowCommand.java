package commands;

import data.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;

/**
 * Класс `Show Command` выводит все элементы коллекции в виде строки
 */
public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит все организации
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
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
            Console.printError("Нет аргументов в " + getName());
        }
        return false;
    }
}
