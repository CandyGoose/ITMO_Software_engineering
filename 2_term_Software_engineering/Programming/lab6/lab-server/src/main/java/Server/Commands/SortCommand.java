package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Класс SortCommand случайным образом перемешивает элементы коллекции
 */
public class SortCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public SortCommand(CollectionManager collectionManager) {
        super("sort", "отсортировать коллекцию в естественном порядке", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Сортирует элементы коллекции
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            collectionManager.sortCollection();
            return new Response(TextWriter.getWhiteText("Коллекция отсортирована."));
        }
    }
}
