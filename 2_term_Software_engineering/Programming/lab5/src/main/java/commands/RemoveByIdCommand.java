package commands;

import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

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
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, который выполняет команду.
     * Удаляет элемент из коллекции по его id.
     * @param argument аргумент команды (id элемента)
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
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
