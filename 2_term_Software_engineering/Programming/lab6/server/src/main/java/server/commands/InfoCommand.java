package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Выводит информацию о коллекции
 */
public class InfoCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса InfoCommand.
     * @param collectionManager менеджер коллекции.
     */
    public InfoCommand(CollectionManager collectionManager) {
        super("info","", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }
    /**
     * Команда вывода информации о коллекции.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ZonedDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "инициализация еще не произошла в этом сеансе" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            ResponseOutputer.appendLn("Данные о коллекции:");
            ResponseOutputer.appendLn(" Тип: " + collectionManager.collectionType());
            ResponseOutputer.appendLn(" Количество элементов: " + collectionManager.collectionSize());
            ResponseOutputer.appendLn(" Последнее время инициализации: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
