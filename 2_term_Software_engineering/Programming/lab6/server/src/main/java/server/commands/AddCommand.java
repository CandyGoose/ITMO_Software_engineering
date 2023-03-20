package server.commands;

import common.data.Organization;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.OrganizationRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.ZonedDateTime;


/**
 * Класс отвечает за добавление организации в коллекцию
 */
public class AddCommand extends AbstractCommand{

    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;


    public AddCommand(CollectionManager collectionManager) {
        super("add" ,"{element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            OrganizationRaw organizationRaw = (OrganizationRaw) objectArgument;
            collectionManager.addToCollection(new Organization(
                    collectionManager.generateNextId(),
                    organizationRaw.getName(),
                    organizationRaw.getCoordinates(),
                    ZonedDateTime.now(),
                    organizationRaw.getAnnualTurnover(),
                    organizationRaw.getFullName(),
                    organizationRaw.getEmployeesCount(),
                    organizationRaw.getType(),
                    organizationRaw.getPostalAddress()
            ));

            ResponseOutputer.appendLn("Организация была создана успешно");
            return true;
        } catch (WrongAmountOfElementsException e) {
            ResponseOutputer.appendLn("Применение: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("Объект, переданный клиентом, неверен");
        }
        return false;
    }
}
