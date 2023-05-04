package Server.Commands;

import Common.exception.DatabaseException;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.db.DBManager;
import Server.util.CollectionManager;

/**
 * Класс команды "print_descending" - вывод всех элементов коллекции в строковом представлении в порядке убывания.
 */
public class PrintDescendingCommand extends AbstractCommand {

    /**
     * Конструктор класса.
     * @param collectionManager менеджер коллекции.
     * @param dbManager менеджер БД.
     */
    public PrintDescendingCommand(CollectionManager collectionManager, DBManager dbManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания",
                0, collectionManager, dbManager);
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
                    return new Response(TextWriter.getWhiteText("Коллекция была отсортирована в обратном порядке."),
                            collectionManager.getDescending());
                }
            } else {
                return new Response("Несоответствие логина и пароля.");
            }

        } catch (DatabaseException e) {
            return new Response(e.getMessage());
        }
    }
}

