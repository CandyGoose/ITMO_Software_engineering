package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;
/**
 * Класс ShuffleCommand случайным образом перемешивает элементы коллекции
 */

public class ShuffleCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Конструктор класса ShuffleCommand.
     * @param collectionManager менеджер коллекции.
     */
    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке", 0);
        this.collectionManager = collectionManager;
    }

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