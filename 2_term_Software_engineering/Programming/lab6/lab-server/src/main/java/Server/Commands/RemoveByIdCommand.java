package Server.Commands;

import Common.data.Organization;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", " удалить элемент из коллекции по его id", 1);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Long id = request.getNumericArgument();
        Organization organizationToRemove = collectionManager.getById(id);
        if (organizationToRemove == null)
            return new Response(TextWriter.getRedText("Организации с таким id не существует."));
        else {
            collectionManager.removeById(id);
            return new Response(TextWriter.getWhiteText("Организация была удалена."));
        }
    }
}