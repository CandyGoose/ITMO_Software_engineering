package Server.commands;

import Common.util.Request;
import Common.util.Response;

/**
 * Класс команды "exit" - выход из приложения.
 */
public class ExitCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     */
    public ExitCommand() {
        super("exit", "завершить программу", 0);
    }

    /**
     * Выполнение команды.
     * @param request объект запроса.
     * @return объект ответа.
     */
    @Override
    public Response execute(Request request) {
        return new Response("Отключение клиента.");
    }
}
