package server.commands;

import common.data.*;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.OrganizationNotFound;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.OrganizationRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * Класс используется для обновления значения элемента коллекции, id которого равен заданному
 */
public class UpdateCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса UpdateCommand.
     * @param collectionManager менеджер коллекции.
     */
    public UpdateCommand(CollectionManager collectionManager) {
        super("update" ,"<id> {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Команда для обновления значения элемента коллекции по id.
     * @param stringArgument строковый аргумент (id элемента коллекции).
     * @param objectArgument объектный аргумент (элемент коллекции).
     * @return true, если выполнение команды успешно завершено, false в противном случае.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            Organization oldOrganization = collectionManager.getById(id);
            if (oldOrganization == null) throw new OrganizationNotFound();

            OrganizationRaw organizationRaw = (OrganizationRaw) objectArgument;

            String name = (organizationRaw.getName() == null || organizationRaw.getName().equals("")) ? oldOrganization.getName() : organizationRaw.getName();
            Coordinates coordinates = (organizationRaw.getCoordinates() == null || organizationRaw.getCoordinates().getX() > 741 || organizationRaw.getCoordinates().getY() == null) ? oldOrganization.getCoordinates() : organizationRaw.getCoordinates();
            ZonedDateTime creationDate = oldOrganization.getCreationDate();
            Float annualTurnover = (organizationRaw.getAnnualTurnover() == null || organizationRaw.getAnnualTurnover() <= 0) ? oldOrganization.getAnnualTurnover() : organizationRaw.getAnnualTurnover();
            String fullName = (organizationRaw.getFullName() == null || organizationRaw.getFullName().equals("")) ? oldOrganization.getFullName() : organizationRaw.getFullName();
            long employeesCount = organizationRaw.getEmployeesCount() <= 0 ? oldOrganization.getEmployeesCount() : organizationRaw.getEmployeesCount();
            OrganizationType organizationType = organizationRaw.getType() == null ? oldOrganization.getType() : organizationRaw.getType();
            Address address = organizationRaw.getPostalAddress() != null ? oldOrganization.getPostalAddress() : organizationRaw.getPostalAddress();

            collectionManager.removeFromCollection(oldOrganization);
            collectionManager.addToCollection(new Organization(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    annualTurnover,
                    fullName,
                    employeesCount,
                    organizationType,
                    address
            ));
            collectionManager.getCollection().sort(Comparator.comparing(Organization::getName, String.CASE_INSENSITIVE_ORDER));
            ResponseOutputer.appendLn("Организация была успешно обновлена");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendError("Коллекция пуста");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appendError("Значение поля должно быть Long");
        } catch (OrganizationNotFound exception) {
            ResponseOutputer.appendError("Нет организации с таким id");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("Объект, переданный клиентом, неверен");
        }
        return false;
    }
}
