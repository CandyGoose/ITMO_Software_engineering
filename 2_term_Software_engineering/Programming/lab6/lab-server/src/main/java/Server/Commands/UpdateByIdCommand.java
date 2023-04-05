package Server.Commands;

import Common.data.Organization;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Команда для обновления значения элемента коллекции, id которого равен заданному.
 */
public class UpdateByIdCommand extends AbstractCommand {

    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public UpdateByIdCommand(CollectionManager collectionManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", 1);
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, который выполняет команду.
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        Long id = request.getNumericArgument();
        Organization organizationToUpdate = collectionManager.getById(id);
        if (organizationToUpdate == null)
            return new Response(TextWriter.getRedText("Организации с таким id не существует."));
        else {
            Organization updatedOrganization = request.getOrganizationArgument();
            updatedOrganization.setId(id);
            collectionManager.removeById(id);
            collectionManager.addToCollection(updatedOrganization);
            return new Response(TextWriter.getWhiteText("Данные организации были обновлены."));
        }
    }
}
