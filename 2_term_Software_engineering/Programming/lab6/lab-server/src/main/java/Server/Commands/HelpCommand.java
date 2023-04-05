package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CommandManager;

/**
 * Класс команды для вывода справки.
 */
public class HelpCommand extends AbstractCommand {

    /**
     * Создает новый объект команды.
     */
    public HelpCommand() {
        super("help", "вывести справку по доступным командам", 0);

    }

    /**
     * Выполняет команду вывода списка доступных команд.
     *
     * @param request объект запроса
     * @return объект ответа с информацией о доступных командах
     */
    @Override
    public Response execute(Request request) {
        StringBuilder sb = new StringBuilder();
        for (AbstractCommand command : CommandManager.AVAILABLE_COMMANDS.values()) {
            sb.append(command.toString()).append("\n");
        }
        sb = new StringBuilder(sb.substring(0, sb.length() - 1));
        return new Response(TextWriter.getWhiteText("Доступные команды:\n") + sb);
    }
}