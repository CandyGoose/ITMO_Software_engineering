package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Этот класс используется для вывода списка последних команд
 */
public class HistoryCommand extends AbstractCommand{
    /**
     * Конструктор класса HistoryCommand.
     */
    public HistoryCommand() {
        super("history","", "показать список последних команд");
    }

    /**
     * Команда вывода истории команд.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return true;
    }
}
