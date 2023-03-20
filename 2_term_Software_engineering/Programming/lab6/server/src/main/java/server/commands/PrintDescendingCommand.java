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


    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_ascending","", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }

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



