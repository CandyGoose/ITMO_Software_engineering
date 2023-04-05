package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;

/**
 * Класс команды, завершающей программу.
 */
public class ExitCommand extends AbstractCommand {

    /**
     * Создает новый объект команды.
     */
    public ExitCommand() {
        super("exit", "завершить программу", 0);
    }

    /**
     * Завершает выполнение программы на сервере.
     * @param request объект запроса
     * @return сообщение об отключении клиента
     */
    @Override
    public Response execute(Request request) {
        return new Response(TextWriter.getWhiteText("Отключение клиента."));
    }
}
