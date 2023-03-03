package commands;

import data.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Класс PrintDescendingCommand выводит список организаций в обратном порядке
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;


    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }


    
    /**
     * Выводит список организаций в обратном порядке
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        copyOfCollection.sort(Collections.reverseOrder());
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString() + "=====");
        }
        return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Нет аргументов в " + getName());
        }
        return false;
    }
}



