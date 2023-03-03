package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
/**
 * Класс ShuffleCommand случайным образом перемешивает элементы коллекции
 */

public class ShuffleCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке");
        this.collectionManager = collectionManager;
    }

    /**
     * Перемешивает коллекцию
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.shuffleCollection();
            Console.printLn("Коллекция была перемешана");
            return true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование '" + argument + "' в " + getName());
        }
        return false;
    }
}