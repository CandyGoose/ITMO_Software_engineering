package common.commands;

import common.util.CollectionManager;
import common.exceptions.CommandArgumentException;
import common.exceptions.InvalidFieldException;
import common.exceptions.InvalidRequestException;
import common.data.OrganizationMaker;
import common.network.Request;
import common.network.RequestBody;
import common.network.RequestBodyWithOrganization;
import common.network.Response;
import common.network.BasicUserIO;
import common.util.TerminalColors;

/**
 * Команда добавления
 */
public class AddCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public AddCommand(CollectionManager col) {
        super("add");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("add", TerminalColors.GREEN)
             + " - добавить новый элемент в коллекцию";
    }

    /**
     * Упаковывает аргументы команды и данные организации в объект RequestBody.
     *
     * @param args массив аргументов команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return объект RequestBody с упакованными данными
     * @throws CommandArgumentException если аргументы команды некорректны
     */
    @Override
    public RequestBody packageBody(String[] args, BasicUserIO io) throws CommandArgumentException {
        if (args.length != 0) {
            throw new CommandArgumentException(this.getName(), args.length);
        }

        try {
            return new RequestBodyWithOrganization(args, OrganizationMaker.parseOrganization(io, 1L));
        } catch (InvalidFieldException e) {
            io.writeln(TerminalColors.colorString("Ошибка при создании новой коллекции.", TerminalColors.RED));
            return null;
        }
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
        if (request.getBody() == null || !(request.getBody() instanceof RequestBodyWithOrganization)) {
            throw new InvalidRequestException("К запросу должна быть прикреплена организация.", "invalidValue");
        }
        RequestBodyWithOrganization body = (RequestBodyWithOrganization) request.getBody();
        body.getOrganization().setOwner(request.getAuth().getLogin());
        long newId = col.add(body.getOrganization());
        if (newId == 0) {
            return new Response("Не удалось добавить организацию.");
        } else {
            return new Response("Добавлена организация, присвоен идентификатор " + newId);
        }
    }
}
