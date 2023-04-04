package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;


public class SortCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса SortCommand.
     * @param collectionManager менеджер коллекции.
     */
    public SortCommand(CollectionManager collectionManager) {
        super("sort", "отсортировать коллекцию в естественном порядке", 0);
        this.collectionManager = collectionManager;
    }

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
