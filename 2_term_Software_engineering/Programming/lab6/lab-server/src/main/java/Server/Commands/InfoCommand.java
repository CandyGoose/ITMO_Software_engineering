package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;


public class InfoCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", " вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(collectionManager.getInfo());
    }
}