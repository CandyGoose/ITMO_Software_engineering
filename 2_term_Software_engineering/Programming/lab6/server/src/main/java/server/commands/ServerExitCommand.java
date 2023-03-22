package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Выключает сервер
*/
public class ServerExitCommand extends AbstractCommand {
    /**
     * Конструктор класса ServerExitCommand.
     */
    public ServerExitCommand() {
        super("server_exit", "", "выключить сервер");
    }

    /**
     * Команда завершения работы сервера.
     * @param stringArgument аргумент команды.
     * @param objectArgument аргумент команды.
     * @return true, если команда выполнена успешно, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendLn("Работа сервера успешно завершена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
