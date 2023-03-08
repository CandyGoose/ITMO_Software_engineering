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
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса PrintDescendingCommand.
     * @param collectionManager менеджер коллекции.
     */
    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }


    /**
     * Метод execute выполняет команду "print_descending", которая выводит все элементы коллекции в порядке убывания.
     *
     * @param argument строка аргументов команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        copyOfCollection.sort(Collections.reverseOrder());
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString() + "\n=====");
        }
        return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Нет аргументов в " + getName());
        }
        return false;
    }
}



