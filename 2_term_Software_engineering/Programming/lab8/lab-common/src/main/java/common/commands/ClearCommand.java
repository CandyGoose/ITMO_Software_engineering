package common.commands;

import common.util.CollectionManager;
import common.network.Request;
import common.network.Response;
import common.util.TerminalColors;

/**
 * Команда очистки
 */
public class ClearCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public ClearCommand(CollectionManager col) {
        super("clear");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("clear", TerminalColors.GREEN) + " - очистить коллекцию";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        col.removeIf(x -> x.getOwner().equals(request.getAuth().getLogin()));
        return new Response("Коллекция очищена.");
    }
}
