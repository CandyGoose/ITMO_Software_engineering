package Server.Commands;


import Common.exception.DatabaseException;
import Common.data.Organization;
import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;

/**
 * Класс команды "add" - добавление новой организации.
 */
public class AddCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     * @param dbManager менеджер БД.
     */
    public AddCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("add", "добавить новый элемент в коллекцию", 0, collectionManager, dbManager);
    }

    /**
     * Выполнение команды.
     * @param request объект запроса.
     * @return объект ответа.
     */
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
