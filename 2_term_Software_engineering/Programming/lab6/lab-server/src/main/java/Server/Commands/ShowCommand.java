package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;


public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", " вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
       return new Response(collectionManager.showCollection());
    }
}

