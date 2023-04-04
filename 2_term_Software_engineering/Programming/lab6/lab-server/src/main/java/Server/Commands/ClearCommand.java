package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", " очистить коллекцию", 0);
        this.collectionManager = collectionManager;
    }


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
