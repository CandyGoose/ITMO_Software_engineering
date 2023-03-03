package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Удаление организации из коллекции
 */
public class RemoveFirstCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Удаление первой организации из коллекции
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
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
