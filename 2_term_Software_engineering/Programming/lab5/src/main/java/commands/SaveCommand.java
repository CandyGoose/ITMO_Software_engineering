package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.FileManager;

/**
 * Записывает коллекции в файл
 */
public class SaveCommand extends AbstractCommand {
    /**
     Менеджер файлов.
     */
    private final FileManager fileManager;
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор SaveCommand создает новый объект команды "save".
     * @param fileManager объект для работы с файлами.
     * @param collectionManager объект для работы с коллекцией.
     */
    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    /**
     * Записывает коллекции в файл
     *
     * @param argument строка аргументов команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
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
