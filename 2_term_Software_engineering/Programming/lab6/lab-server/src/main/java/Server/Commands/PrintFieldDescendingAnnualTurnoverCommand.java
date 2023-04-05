package Server.Commands;

import Common.util.Request;
import Common.util.Response;
import Common.util.TextWriter;
import Server.util.CollectionManager;

/**
 * Выводит все значения годового оборота организаций в системе в порядке убывания
 */
public class PrintFieldDescendingAnnualTurnoverCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public PrintFieldDescendingAnnualTurnoverCommand(CollectionManager collectionManager) {
        super("print_field_descending_annual_turnover", "вывести значения поля annualTurnover всех элементов в порядке убывания", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду для вывода всех значений годового оборота организаций в системе в порядке убывания.
     * @param request объект запроса
     * @return ответ на запрос с выводом всех значений годового оборота организаций в системе в порядке убывания
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Коллекция пуста."));
        else {
            return new Response(TextWriter.getWhiteText("Все значения поля annualTurnover в порядке убывания: " + collectionManager.getListAnnualTurnover()));
        }
    }
}
