package server.commands;

import common.data.Organization;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Класс PrintDescendingCommand выводит список организаций в обратном порядке
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса PrintDescendingCommand.
     * @param collectionManager менеджер коллекции.
     */

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending","", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }
    /**
     * Команда вывода коллекции в порядке убывания элементов.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
            copyOfCollection.sort(Collections.reverseOrder());
            StringBuilder output = new StringBuilder();
            for(Organization organization : copyOfCollection){
                output.append(organization.toString()).append("\n");
            }
            ResponseOutputer.appendLn(output);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}



