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

    public InfoCommand(CollectionManager collectionManager) {
        super("info","", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ZonedDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "инициализация еще не произошла в этом сеансе" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            ZonedDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в этой сессии еще не было сохранения" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            ResponseOutputer.appendLn("Данные о коллекции:");
            ResponseOutputer.appendLn(" Тип: " + collectionManager.collectionType());
            ResponseOutputer.appendLn(" Количество элементов: " + collectionManager.collectionSize());
            ResponseOutputer.appendLn(" Последнее время сохранения: " + lastSaveTimeString.format(String.valueOf(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss"))));
            ResponseOutputer.appendLn(" Последнее время инициализации: " + lastInitTimeString.format(String.valueOf(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss"))));
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
