package Server.requestHandlers;

import Common.util.Response;
import Common.util.Serializers;
import Server.ServerConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.function.Consumer;

public class ResponseSender implements Consumer<Response> {

    private final SelectionKey key;

    private final Set<SelectionKey> workingKeys;

    public ResponseSender(SelectionKey key, Set<SelectionKey> workingKeys) {
        this.key = key;
        this.workingKeys = workingKeys;
    }

    @Override
    public void accept(Response response) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (response == null) {
            response = new Response("Сервер не смог обработать запрос.");
        }
        try {
            ByteBuffer buffer = Serializers.serializeResponse(response);
            socketChannel.write(buffer);
        } catch (IOException e) {
            return;
        }
        ServerConfig.logger.info("Сервер отправил ответ клиенту.");
        workingKeys.remove(key);
    }
}
