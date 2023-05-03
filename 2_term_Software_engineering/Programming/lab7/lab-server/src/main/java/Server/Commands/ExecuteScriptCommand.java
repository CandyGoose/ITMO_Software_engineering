package Server.Commands;

import Common.util.Request;
import Common.util.Response;

public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла", 0);
    }

    @Override
    public Response execute(Request request) {
        return new Response("Исполнение скрипта.");
    }
}
