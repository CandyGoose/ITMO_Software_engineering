package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс "Show Command" выводит все элементы коллекции в виде строки
 */
public class ShowCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show","", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendLn(collectionManager.showCollection());
            return true;
        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
