package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;


public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", " завершить программу", 0);
    }

    @Override
    public Response execute(Request request) {
        return new Response(TextWriter.getWhiteText("Отключение клиента."));
    }
}
