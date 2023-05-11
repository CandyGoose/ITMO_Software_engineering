package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;

/**
 * Класс команды "info" - вывод информации о коллекции.
 */
public class InfoCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     */
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды.
     * @param request объект запроса.
     * @return объект ответа.
     */
    @Override
    public Response execute(Request request) {
        return new Response(collectionManager.getInfo());
    }
}