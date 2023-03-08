package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

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
        super("remove_first", "удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, который выполняет команду.
     * Удаляет первый элемент из коллекции
     *
     * @param argument аргумент команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.removeFirstInCollection();
            Console.printLn("Организация была успешно удалена");
            return true;
        } catch (NumberFormatException e) {
            Console.printError("Поле должно быть Long");
        } catch (ArrayIndexOutOfBoundsException exception){
            Console.printError("Идентификатор должен быть привязан к коллекции");
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование '" + argument + "' в " + getName());
        }
        return false;
    }
}
