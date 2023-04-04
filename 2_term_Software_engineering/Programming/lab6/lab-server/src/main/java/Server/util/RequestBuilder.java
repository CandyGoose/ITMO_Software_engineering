package Server.util;

import Common.util.Request;
import Common.util.Response;
import Server.Commands.AbstractCommand;
import Server.ServerApp;
import Common.exception.DisconnectInitException;

public class RequestBuilder {
    public static Response build(AbstractCommand command, Request request) throws DisconnectInitException {
        if (request.getOrganizationArgument() != null)
            request.getOrganizationArgument().setId(ServerApp.collectionManager.generateNextId());
        return command.execute(request);
    }
}
