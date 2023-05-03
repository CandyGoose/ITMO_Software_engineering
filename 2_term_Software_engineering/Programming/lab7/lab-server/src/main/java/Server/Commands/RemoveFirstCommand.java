package Server.Commands;

import Common.exception.DatabaseException;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.db.DBManager;
import Server.util.CollectionManager;

/**
 * Удаление первой организации из коллекции
 */
public class RemoveFirstCommand extends AbstractCommand {

    /**
     * Создает новый объект команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirstCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("remove_first", "удалить первый элемент из коллекции", 0, collectionManager, dbManager);
    }

    /**
     * Выполняет удаление первого элемента коллекции.
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        try {
            if (dbManager.validateUser(request.getLogin(), request.getPassword())) {
                if (collectionManager.getCollection().isEmpty())
                    return new Response("Коллекция пуста.");
                else {
                    Long firstOrganizationId = collectionManager.getCollection().getFirst().getId();
                    if (dbManager.checkOrganizationExistence(firstOrganizationId)) {
                        if (dbManager.removeById(collectionManager.getFirstId(), request.getLogin())) {
                            collectionManager.removeById(request.getNumericArgument());
                            return new Response(TextWriter.getWhiteText("Элемент был удален."));
                        } else {
                            return new Response("Элемент был создан другим пользователем. У вас нет прав для его обновления.");
                        }
                    } else {
                        return new Response("Нет элемента с таким идентификатором.");
                    }
                }
            } else {
                return new Response("Несоответствие логина и пароля.");
            }
        } catch (DatabaseException e) {
            return new Response(e.getMessage());
        }
    }
}

