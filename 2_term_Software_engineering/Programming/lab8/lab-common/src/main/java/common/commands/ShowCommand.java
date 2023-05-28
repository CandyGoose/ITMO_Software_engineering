package common.commands;

import common.util.CollectionManager;
import common.data.Organization;
import common.network.Request;
import common.network.Response;
import common.network.ResponseWithOrganizations;
import common.util.TerminalColors;

/**
 * Команда вывода коллекции
 */
public class ShowCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public ShowCommand(CollectionManager col) {
        super("show");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("show", TerminalColors.GREEN)
                + " - вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        Organization[] a = new Organization[col.getCollection().size()];
        return new ResponseWithOrganizations(col.getCollection().toArray(a));
    }
}
