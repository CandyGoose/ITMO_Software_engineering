package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * 'add' command. Add organization in collection.
 */

public class AddCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", " добавить новый элемент в коллекцию", 0);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        collectionManager.addToCollection(request.getOrganizationArgument());
        return new Response(TextWriter.getWhiteText("Организация добавлена в коллекцию."));
    }
}
