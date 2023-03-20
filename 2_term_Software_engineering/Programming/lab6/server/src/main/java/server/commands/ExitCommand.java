package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Завершает работу программы
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit","", "завершить программу (без сохранения в файл)");
    }


    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
