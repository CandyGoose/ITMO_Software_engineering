package Server.commands;

import Common.data.Organization;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Удаление первой организации из коллекции
 */
public class RemoveFirstCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет удаление первого элемента коллекции.
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            Organization organizationToRemove = collectionManager.getFirst();
            if (organizationToRemove == null)
                return new Response(TextWriter.getRedText("Организации с таким id не существует."));
            else {
                collectionManager.removeFirst();
                return new Response(TextWriter.getWhiteText("Организация была удалена."));
            }
        }
    }
}
