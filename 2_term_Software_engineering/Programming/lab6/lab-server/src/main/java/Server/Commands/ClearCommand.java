package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Команда очистки коллекции.
 */
public class ClearCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Очищает коллекцию, если она не пуста.
     * @param request объект запроса
     * @return объект Response с сообщением об успешном выполнении или ошибке, если коллекция пуста
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            collectionManager.clearCollection();
            return new Response(TextWriter.getWhiteText("Коллекция очищена."));
        }
    }
}
