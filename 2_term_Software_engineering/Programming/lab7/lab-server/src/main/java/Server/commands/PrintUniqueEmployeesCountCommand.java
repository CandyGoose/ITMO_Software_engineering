package Server.commands;

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
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public PrintUniqueEmployeesCountCommand(CollectionManager collectionManager) {
        super("print_unique_employees_count", "вывести уникальные значения поля employeesCount всех элементов в коллекции", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду для вывода всех уникальных значений количества сотрудников организаций в системе.
     * @param request объект запроса
     * @return ответ на запрос с выводом всех уникальных значений количества сотрудников организаций в системе
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            return new Response("Уникальные значения поля employeesCount: " + collectionManager.getEmployeesCount());
        }
    }
}
