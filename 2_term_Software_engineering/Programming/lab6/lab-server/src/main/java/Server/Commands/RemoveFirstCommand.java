package Server.Commands;

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
     * Конструктор класса RemoveFirstCommand.
     * @param collectionManager менеджер коллекции.
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, который выполняет команду.
     * Удаляет первый элемент из коллекции
     *
     * @return true, если выполнение команды прошло успешно, и false, если произошла ошибка.
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
