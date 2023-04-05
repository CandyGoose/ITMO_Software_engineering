package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Класс ShuffleCommand случайным образом перемешивает элементы коллекции
 */
public class ShuffleCommand extends AbstractCommand {

    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Перемешивает элементы коллекции
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            collectionManager.shuffleCollection();
            return new Response(TextWriter.getWhiteText("Коллекция перемешана."));
        }
    }
}