package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.db.DBManager;
import Server.util.CollectionManager;

/**
 * Класс ShuffleCommand случайным образом перемешивает элементы коллекции
 */
public class ShuffleCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     * @param dbManager менеджер БД.
     */
    public ShuffleCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке", 0, collectionManager, dbManager);
    }

    /**
     * Перемешивает элементы коллекции
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            return new Response("Коллекция была перемешана.", collectionManager.shuffleCollection());
        }
    }
}