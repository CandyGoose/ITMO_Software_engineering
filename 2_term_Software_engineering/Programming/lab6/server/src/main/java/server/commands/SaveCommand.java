package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Записывает коллекции в файл
 */
public class SaveCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save","", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.saveCollection();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
