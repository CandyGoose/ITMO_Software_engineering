package Server.Commands;

import Common.data.Organization;
import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

import java.util.Comparator;


public class UpdateByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public UpdateByIdCommand(CollectionManager collectionManager) {
        super("update", " обновить значение элемента коллекции, id которого равен заданному", 1);
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {
        Long id = request.getNumericArgument();
        Organization organizationToUpdate = collectionManager.getById(id);
        if (organizationToUpdate == null)
            return new Response(TextWriter.getRedText("Организации с таким id не существует."));
        else {
            Organization updatedOrganization = request.getOrganizationArgument();
            updatedOrganization.setId(id); // сохраняем id старой организации
            collectionManager.removeById(id);
            collectionManager.addToCollection(updatedOrganization); // добавляем обновленную организацию
            collectionManager.getCollection().sort(Comparator.comparing(Organization::getName, String.CASE_INSENSITIVE_ORDER));
            return new Response(TextWriter.getWhiteText("Данные организации были обновлены."));
        }
    }
}
