package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;

/**
 * Класс команды для выполнения скрипта из указанного файла.
 */
public class ExecuteScriptCommand extends AbstractCommand {

    /**
     * Создает новый объект команды.
     */
    public ExecuteScriptCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла", 1);
    }

    /**
     * Исполняет скрипт.
     * @param request объект запроса
     * @return объект Response с сообщением о результате выполнения команды
     */
    @Override
    public Response execute(Request request) {
        return new Response(TextWriter.getWhiteText("Исполнение скрипта."));
    }
}
