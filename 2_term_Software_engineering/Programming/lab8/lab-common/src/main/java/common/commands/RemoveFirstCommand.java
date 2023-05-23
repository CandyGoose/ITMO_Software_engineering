package common.commands;

import common.util.CollectionManager;
import common.exceptions.InvalidRequestException;
import common.exceptions.UnauthorizedException;
import common.data.Organization;
import common.network.Request;
import common.network.Response;
import common.network.ResponseWithException;
import common.network.ResponseWithOrganizations;
import common.util.TerminalColors;

/**
 * Команда удаления первой организации
 */
public class RemoveFirstCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public RemoveFirstCommand(CollectionManager col) {
        super("remove_first");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("remove_first", TerminalColors.GREEN)
             + " - удалить первый элемент из коллекции";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     * @throws InvalidRequestException если запрос некорректен
     */
    @Override
    public Response execute(Request request) throws InvalidRequestException {
        if (col.removeFirst(x -> x.getOwner().equals(request.getAuth().getLogin())) == 1) {
            return new Response("Ваша первая организация была удалена.");
        } else {
            return new Response("У вас еще нет организаций.");
        }

    }
}
