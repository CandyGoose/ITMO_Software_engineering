package Server.util;

import Common.util.Request;
import Common.util.Response;
import Server.Commands.AbstractCommand;
import Server.ServerApp;
import Common.exception.DisconnectInitException;

/**
 * Класс для построения ответа на запрос клиента, используя переданную команду и запрос.
 */
public class RequestBuilder {
    /**
     * Строит ответ на запрос клиента, используя переданную команду и запрос.
     * Если команда требует аргумента организации, устанавливает у организации новый id, сгенерированный менеджером коллекции.
     * @param command команда для выполнения
     * @param request запрос клиента
     * @return ответ на запрос клиента
     * @throws DisconnectInitException если возникли проблемы с инициализацией отключения
     */
    public static Response build(AbstractCommand command, Request request) throws DisconnectInitException {
        if (request.getOrganizationArgument() != null)
            request.getOrganizationArgument().setId(ServerApp.collectionManager.generateNextId());
        return command.execute(request);
    }
}
