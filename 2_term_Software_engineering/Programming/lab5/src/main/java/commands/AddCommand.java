package commands;

import data.Organization;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;


/**
 * Класс отвечает за добавление организации в коллекцию
 */
public class AddCommand extends AbstractCommand{

    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;
    /**
     Объект, задающий вопросы пользователю для ввода информации об организации.
     */
    private final OrganizationAsker organizationAsker;

    /**
     * Конструктор создает новый объект команды и задает ее имя и описание.
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param organizationAsker объект, который задает параметры нового элемента коллекции.
     */
    public AddCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    /**
     Реализует метод execute, который вызывает метод addToCollection у объекта CollectionManager,
     добавляя новую организацию с помощью методов класса OrganizationAsker.
     @param argument аргумент команды, которая не принимает никаких аргументов
     @return true, если команда была успешно выполнена, иначе - false
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.addToCollection(new Organization(
                    organizationAsker.setId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    organizationAsker.askCreationDate(),
                    organizationAsker.askAnnualTurnover(),
                    organizationAsker.askFullName(),
                    organizationAsker.askEmployeesCount(),
                    organizationAsker.askOrganizationType(),
                    organizationAsker.askAddress()
            ));
            Console.printLn("Организация была создана успешно");
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Использование '" + argument + "' в " + getName());
        } catch (IncorrectInputInScriptException ignored) {
        }
        return false;
    }
}
