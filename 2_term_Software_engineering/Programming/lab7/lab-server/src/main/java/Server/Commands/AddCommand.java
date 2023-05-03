package Server.Commands;


import Common.exception.DatabaseException;
import Common.data.Organization;
import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;

public class AddCommand extends AbstractCommand {

    public AddCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("add", "добавить новый элемент в коллекцию", 0, collectionManager, dbManager);
    }

    @Override
    public Response execute(Request request) {
        try {
            if (dbManager.validateUser(request.getLogin(), request.getPassword())) {
                Organization organizationToAdd = request.getOrganizationArgument();
                Long id = dbManager.addElement(organizationToAdd, request.getLogin());
                organizationToAdd.setId(id);
                collectionManager.addToCollection(organizationToAdd);
                return new Response("Элемент был успешно добавлен с ИД: " + id);
            } else {
                return new Response("Несоответствие логина и пароля.");
            }
        } catch (DatabaseException e) {
            return new Response(e.getMessage());
        }
    }

}
