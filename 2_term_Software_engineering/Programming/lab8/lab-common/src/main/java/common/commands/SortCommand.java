package common.commands;

import common.util.CollectionManager;
import common.data.Organization;
import common.network.Request;
import common.network.Response;
import common.network.ResponseWithOrganizations;
import common.util.TerminalColors;

/**
 * Команда сортировки
 */
public class SortCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public SortCommand(CollectionManager col) {
        super("sort");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("sort", TerminalColors.GREEN)
                + " - отсортировать коллекцию в естественном порядке";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        Organization[] a = new Organization[col.getSortedCollection().size()];
        return new ResponseWithOrganizations(col.getSortedCollection().toArray(a));
    }
}
