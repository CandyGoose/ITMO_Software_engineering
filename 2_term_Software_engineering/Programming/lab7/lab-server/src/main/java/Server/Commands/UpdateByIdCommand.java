package Server.Commands;

import Common.exception.DatabaseException;
import Common.data.Organization;import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;


public class UpdateByIdCommand extends AbstractCommand {

    public UpdateByIdCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", 1,
                collectionManager, dbManager);
    }

    @Override
    public Response execute(Request request) {
        try {
            if (dbManager.validateUser(request.getLogin(), request.getPassword())) {
                Long id = request.getNumericArgument();
                if (dbManager.checkOrganizationExistence(id)) {
                    if (dbManager.updateById(request.getOrganizationArgument(), id, request.getLogin())) {
                        Organization updatedOrganization = request.getOrganizationArgument();
                        updatedOrganization.setId(id);
                        collectionManager.removeById(id);
                        collectionManager.addToCollection(request.getOrganizationArgument());
                        return new Response("Элемент с ИД " + id + " юыл успешно обновлен.");
                    } else {
                        return new Response("Элемент был создан другим пользователем. У вас нет прав для его обновления.");
                    }
                } else {
                    return new Response("Нет элемента с таким идентификатором.");
                }
            } else {
                return new Response("Несоответствие логина и пароля.");
            }
        } catch (DatabaseException e) {
            return new Response(e.getMessage());
        }
    }
}
