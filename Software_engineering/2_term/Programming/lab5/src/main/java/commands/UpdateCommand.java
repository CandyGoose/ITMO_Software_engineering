package commands;

import data.Organization;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

/**
 * Класс используется для обновления значения элемента коллекции, id которого равен заданному
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final OrganizationAsker organizationAsker;

    public UpdateCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    /**
     * Функция принимает аргумент, который является id организации
     * Если он не существует, функция выдает исключение
     * Если организация существует, функция запрашивает пользователя ввести новые значения
     * 
     * @param argument аргумент, введенный пользователем
     * @return boolean type
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(argument.isEmpty()) throw new WrongAmountOfElementsException();
            Long id = Long.parseLong(argument);
            if(collectionManager.getById(id) == null) throw new MustBeNotEmptyException();
            Organization newOrganization = new Organization(
                    organizationAsker.setId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    organizationAsker.askCreationDate(),
                    organizationAsker.askAnnualTurnover(),
                    organizationAsker.askFullName(),
                    organizationAsker.askEmployeesCount(),
                    organizationAsker.askOrganizationType(),
                    organizationAsker.askAddress()
            );
            collectionManager.replaceById(id, newOrganization);
            Console.printLn("Организация была успешно обновлена");
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Нет аргументов в " + getName());
        } catch (NumberFormatException e) {
            Console.printError("Значение поля должно быть Long");
        } catch (MustBeNotEmptyException e) {
            Console.printError("Нет организации с таким id");
        } catch (IncorrectInputInScriptException ignored) {
        }
        return false;
    }
}
