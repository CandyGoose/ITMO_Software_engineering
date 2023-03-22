package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Удаление первой организации из коллекции
 */
public class RemoveFirstCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса RemoveFirstCommand.
     * @param collectionManager менеджер коллекции.
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "<id>", "удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Команда удаления первого элемента коллекции.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            collectionManager.removeFirstInCollection();
            ResponseOutputer.appendLn("Организация была успешно удалена");
            return true;

        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        } catch (NumberFormatException e) {
            ResponseOutputer.appendError("Поле должно быть Long");
        } catch (ArrayIndexOutOfBoundsException exception) {
            ResponseOutputer.appendError("Недопустимый индекс");
        } catch (CollectionIsEmptyException e) {
            ResponseOutputer.appendError("Коллекция пуста");
        }
        return false;
    }
}
