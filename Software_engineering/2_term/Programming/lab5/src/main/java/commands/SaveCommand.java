package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.FileManager;

/**
 * Записывает коллекции в файл
 */
public class SaveCommand extends AbstractCommand {
    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    /**
     * Записывает коллекции в файл
     * 
     * @param argument аргумент, переданный команде
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            fileManager.writeCollection(collectionManager.getCollection());
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Нет в аргументов в " + getName());
        }
        return false;
    }
}
