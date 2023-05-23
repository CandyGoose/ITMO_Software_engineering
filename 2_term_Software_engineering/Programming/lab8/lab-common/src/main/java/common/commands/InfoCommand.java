package common.commands;

import common.util.CollectionManager;
import common.exceptions.InvalidRequestException;
import common.network.Request;
import common.network.Response;

/**
 * Команда вывода информации
 */
public class InfoCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public InfoCommand(CollectionManager col) {
        super("info");

        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return " - вывести в стандартный поток вывода информацию о коллекции";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     * @throws InvalidRequestException если запрос некорректен
     */
    @Override
    public Response execute(Request request) throws InvalidRequestException {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип коллекции: " + col.getCollection().getClass().getSimpleName() + '\n');
        sb.append("Количество элементов: " + col.getCollection().size() + '\n');
        return new Response(sb.toString(), "infoResponse", new Object[] {col.getCollection().getClass().getSimpleName(), col.getCollection().size()});
    }
}
