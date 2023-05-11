package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.db.DBManager;
import Server.util.CollectionManager;

/**
 * Класс SortCommand случайным образом перемешивает элементы коллекции
 */
public class SortCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     * @param dbManager менеджер БД.
     */
    public SortCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("sort", "отсортировать коллекцию в естественном порядке", 0, collectionManager, dbManager);
    }

    /**
     * Сортирует элементы коллекции
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            return new Response("Коллекция была отсортирована.", collectionManager.sortCollection());
        }
    }
}
