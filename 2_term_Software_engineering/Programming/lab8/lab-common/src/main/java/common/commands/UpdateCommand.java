package common.commands;

import common.util.CollectionManager;
import common.exceptions.CommandArgumentException;
import common.exceptions.InvalidFieldException;
import common.exceptions.InvalidRequestException;
import common.data.OrganizationMaker;
import common.data.Organization;
import common.network.Request;
import common.network.RequestBody;
import common.network.RequestBodyWithOrganization;
import common.network.Response;
import common.network.BasicUserIO;
import common.util.TerminalColors;

/**
 * Команда обновления
 */
public class UpdateCommand extends AbstractCommand {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public UpdateCommand(CollectionManager col) {
        super("update");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("update [id]", TerminalColors.GREEN)
             + " - обновить значение элемента коллекции, id которого равен заданному";
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
        if (args.length != 1) {
            throw new CommandArgumentException(getName(), 1, args.length);
        }

        try {
            Long id = Long.parseLong(args[0]);
            Organization organization = OrganizationMaker.parseOrganization(io, id);
            return new RequestBodyWithOrganization(args, organization);
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("Идентификатор не является действительным номером", e);
        } catch (InvalidFieldException e) {
            io.writeln(TerminalColors.colorString("Не удалось обновить организацию.", TerminalColors.RED));
            io.writeln(e);
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
            throw new InvalidRequestException("К запросу должна быть прикреплена организация.", null);
        }

        Organization organization = ((RequestBodyWithOrganization) request.getBody()).getOrganization();
        organization.setOwner(request.getAuth().getLogin());

        if (!col.update(organization)) {
            throw new InvalidRequestException(new CommandArgumentException("В коллекции не было найдено ни одного элемента с указанным идентификатором."), null);
        }
        return new Response("Updated");
    }
}
