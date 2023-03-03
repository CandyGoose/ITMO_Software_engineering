package commands;

import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Удаление организации из коллекции
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    /**
     * Удаление организации из коллекции
     *
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(argument.isEmpty()) throw new WrongAmountOfElementsException();
            Long id = Long.parseLong(argument);
            if(collectionManager.getById(id) == null) throw new MustBeNotEmptyException();
            collectionManager.removeByIdFromCollection(id);
            Console.printLn("Организация была успешно удалена");
            return true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("Нет аргументов в " + getName());
        } catch (NumberFormatException e) {
            Console.printError("Поле должно быть Long");
        } catch (MustBeNotEmptyException e) {
            Console.printError("Нет организации с таким идентификатором");
        }
        return false;
    }
}
