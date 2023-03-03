package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;


/**
 * Очищает коллекцию
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    
    /**
     * Очищает коллекцию
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            Console.printLn("Коллекция была очищена");
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование '" + argument + "' в " + getName());
        }
        return false;
    }
}
