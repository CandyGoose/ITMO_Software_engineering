package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;


/**
 * Очищает коллекцию
 */
public class ClearCommand extends AbstractCommand{
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса ClearCommand.
     * @param collectionManager менеджер коллекции.
     */
    public ClearCommand(CollectionManager collectionManager) {
        super("clear","", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Команда очистки коллекции.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            ResponseOutputer.appendLn("Коллекция была очищена");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
