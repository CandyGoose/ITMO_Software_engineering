package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;


public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", " вывести элементы коллекции в порядке убывания", 0);
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else
            return new Response(collectionManager.printDescending());
    }
}

