package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Выводит уникальные значения количества сотрудников
 */
public class PrintUniqueEmployeesCountCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса PrintUniqueEmployeesCountCommand.
     * @param collectionManager менеджер коллекции.
     */
    public PrintUniqueEmployeesCountCommand(CollectionManager collectionManager) {
        super("print_unique_employees_count", "вывести уникальные значения поля employeesCount всех элементов в коллекции", 0);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            return new Response(TextWriter.getWhiteText("Уникальные значения поля employeesCount: " + collectionManager.getEmployeesCount()));
        }
    }
}
