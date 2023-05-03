package Server.Commands;

import Common.exception.DatabaseException;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.db.DBManager;
import Server.util.CollectionManager;

public class ClearCommand extends AbstractCommand {

    public ClearCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("clear", "очистить коллекцию", 0, collectionManager, dbManager);
    }

    @Override
    public Response execute(Request request) {
        try {
            getDbManager().clear(request.getLogin());

            if (collectionManager.getCollection().isEmpty())
                return new Response(TextWriter.getRedText("Коллекция пуста."));
            else {
                collectionManager.clearCollection();
                collectionManager.setOrganizationCollection(dbManager.loadCollection());
                return new Response(TextWriter.getWhiteText("Коллекция успешно очищена."));
            }
        } catch (DatabaseException e) {
            return new Response(e.getMessage());
        }
    }
}
