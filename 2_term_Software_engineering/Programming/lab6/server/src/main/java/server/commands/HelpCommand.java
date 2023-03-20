package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Класс "HelpCommand" отображает справку по доступным командам
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help","", "вывести справку по доступным командам");
    }

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
