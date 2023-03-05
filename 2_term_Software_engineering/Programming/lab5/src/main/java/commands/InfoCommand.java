package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Выводит информацию о коллекции
 */
public class InfoCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит информацию о коллекции
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn(collectionManager.infoAboutCollection());
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование '" + argument + "' в " + getName());
        }
        return false;
    }
}
