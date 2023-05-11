package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;

/**
 * Класс, представляющий команду, которая выводит информацию о коллекции.
 */
public class InfoCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Получает информацию о коллекции.
     *
     * @param request объект запроса
     * @return ответ на выполнение команды
     */
    @Override
    public Response execute(Request request) {
        return new Response(collectionManager.getInfo());
    }
}