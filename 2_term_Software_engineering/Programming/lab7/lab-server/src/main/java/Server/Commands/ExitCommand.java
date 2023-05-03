package Server.Commands;

import Common.util.Request;
import Common.util.Response;

public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "завершить программу", 0);
    }

    @Override
    public Response execute(Request request) {
        return new Response("Отключение клиента.");
    }
}
