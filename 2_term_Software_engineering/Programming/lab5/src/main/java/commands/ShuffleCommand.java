package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
/**
 * Класс ShuffleCommand случайным образом перемешивает элементы коллекции
 */

public class ShuffleCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса ShuffleCommand.
     * @param collectionManager менеджер коллекции.
     */
    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке");
        this.collectionManager = collectionManager;
    }

    /**
     * Перемешивает коллекции
     *
     * @param argument строка аргументов команды
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.shuffleCollection();
            Console.printLn("Коллекция была перемешана");
            return true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование аргумента '" + argument + "' в команде '" + getName() + "'");
        }
        return false;
    }
}