package Server.commands;

import Common.exception.DatabaseException;
import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;

import java.util.List;

/**
 * Класс команды "show" - вывод всех элементов коллекции в строковом представлении.
 */
public class ShowCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     * @param dbManager менеджер БД.
     */
    public ShowCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, collectionManager, dbManager);
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
                if (collectionManager.getCollection().isEmpty()) {
                    return new Response("Коллекция пуста.");
                } else {
                    List<Long> ids = dbManager.getIdsOfUsersElements(request.getLogin());
                    return new Response("Элементы коллекции:",
                            collectionManager.getUsersElements(ids),
                            collectionManager.getAlienElements(ids));
                }
            } else {
                return new Response("Несоответствие логина и пароля.");
            }

        } catch (DatabaseException e) {
            return new Response(e.getMessage());
        }
    }
}

