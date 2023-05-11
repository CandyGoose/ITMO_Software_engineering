package Server.commands;

import Common.exception.DatabaseException;
import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;
/**
 * Класс команды "remove_by_id" - удаление организации по ID.
 */
public class RemoveByIdCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     * @param dbManager менеджер БД.
     */
    public RemoveByIdCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1,
                collectionManager, dbManager);
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
                if (dbManager.checkOrganizationExistence(request.getNumericArgument())) {
                    if (dbManager.removeById(request.getNumericArgument(), request.getLogin())) {
                        collectionManager.removeById(request.getNumericArgument());
                        return new Response("Элемент с ИД " + request.getNumericArgument() + " был удален из коллекции.");
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