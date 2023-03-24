package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс ShuffleCommand случайным образом перемешивает элементы коллекции
 */
public class ShuffleCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса ShuffleCommand.
     * @param collectionManager менеджер коллекции.
     */
    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle","", "перемешать элементы коллекции в случайном порядке");
        this.collectionManager = collectionManager;
    }
    /**
     * Команда перемешивания коллекции.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.shuffleCollection();
            ResponseOutputer.appendLn("Коллекция была перемешана.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
