package Server.Commands;

import Common.util.Request;
import Common.util.Response;

/**
 * Класс команды "execute_script" - исполнение скрипта.
 */
public class ExecuteScriptCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     */
    public ExecuteScriptCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла", 0);
    }

    /**
     * Выполнение команды.
     * @param request объект запроса.
     * @return объект ответа.
     */
    @Override
    public Response execute(Request request) {
        return new Response("Исполнение скрипта.");
    }
}
