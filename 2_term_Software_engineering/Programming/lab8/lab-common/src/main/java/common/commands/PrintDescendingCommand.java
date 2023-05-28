package common.commands;

import common.util.CollectionManager;
import common.data.Organization;
import common.network.Request;
import common.network.Response;
import common.network.ResponseWithOrganizations;
import common.util.TerminalColors;

/**
 * Команда вывода организации в обратном порядке
 */
public class PrintDescendingCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public PrintDescendingCommand(CollectionManager col) {
        super("print_descending");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("print_descending", TerminalColors.GREEN)
                + " - вывести элементы коллекции в порядке убывания";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        Organization[] a = new Organization[col.getDescendingCollection().size()];
        return new ResponseWithOrganizations(col.getDescendingCollection().toArray(a));
    }
}
