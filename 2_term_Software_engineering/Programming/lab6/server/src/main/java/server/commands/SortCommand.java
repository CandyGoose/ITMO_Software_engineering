package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Класс SortCommand сортирует элементы коллекции
 */
public class SortCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса SortCommand.
     * @param collectionManager менеджер коллекции.
     */
    public SortCommand(CollectionManager collectionManager) {
        super("sort","", "отсортировать коллекцию в естественном порядке");
        this.collectionManager = collectionManager;
    }
    /**
     * Команда сортировки коллекции.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.sortCollection();
            ResponseOutputer.appendLn("Коллекция была отсортирована");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
