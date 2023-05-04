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

/**
 * Обработчик отправки ответа клиенту.
 */
public class ResponseSender implements Consumer<Response> {

    /**
     * Ключ сокета для связи с клиентом.
     */
    private final SelectionKey key;

    /**
     * Множество рабочих ключей для управления селектором.
     */
    private final Set<SelectionKey> workingKeys;

    /**
     * Конструктор обработчика отправки ответа клиенту.
     * @param key ключ сокета для связи с клиентом
     * @param workingKeys множество рабочих ключей для управления селектором
     */
    public ResponseSender(SelectionKey key, Set<SelectionKey> workingKeys) {
        this.key = key;
        this.workingKeys = workingKeys;
    }

    /**
     * Отправка ответа клиенту
     * @param response ответ сервера
     */
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
