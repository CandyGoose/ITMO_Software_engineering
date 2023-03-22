package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Удаление организации из коллекции по ID
 */
public class RemoveByIdCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса RemoveByIdCommand.
     * @param collectionManager менеджер коллекции.
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "<id>", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    /**
     * Команда удаления элемента коллекции по id.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(stringArgument);
            if(collectionManager.getById(id) == null) throw new MustBeNotEmptyException();
            collectionManager.removeByIdFromCollection(id);
            ResponseOutputer.appendLn("Организация была успешно удалена");
            return true;

        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        } catch (NumberFormatException e) {
            ResponseOutputer.appendError("Поле должно быть Long");
        } catch (MustBeNotEmptyException e) {
            ResponseOutputer.appendError("Нет организации с таким id");
        } catch (CollectionIsEmptyException e) {
            ResponseOutputer.appendError("Коллекция пуста");
        }
        return false;
    }
}
