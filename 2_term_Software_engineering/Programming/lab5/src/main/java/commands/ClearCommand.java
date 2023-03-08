package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;


/**
 * Очищает коллекцию
 */
public class ClearCommand extends AbstractCommand{
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор создает новый объект команды и задает ее имя и описание.
     * @param collectionManager менеджер коллекции
     */
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }
    
    /**
     * Метод execute() вызывает метод clearCollection() у объекта CollectionManager для удаления всех элементов в коллекции.
     * В случае, если методу был передан аргумент, будет вызвано исключение WrongAmountOfElementsException,
     * которое указывает на неправильное количество аргументов в команде.
     * После успешного выполнения метод выведет на консоль сообщение об успешном удалении элементов из коллекции.
     * @param argument Аргумент команды.
     * @return true, если команда была выполнена успешно, иначе false.
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
