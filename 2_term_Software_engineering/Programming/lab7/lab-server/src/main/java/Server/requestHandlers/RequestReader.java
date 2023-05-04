package Server.requestHandlers;

import Common.util.Request;
import Common.util.Serializers;
import Server.ServerConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Supplier;

/**
 * Класс RequestReader представляет собой поставщика запроса, который десериализует полученный буфер данных в объект типа Request.
 */
public class RequestReader implements Supplier<Request> {

    /**
     * Ключ
     */
    private final SelectionKey key;

    /**
     * Создает новый объект типа RequestReader с указанным ключом выборки.
     * @param key ключ выборки
     */
    public RequestReader(SelectionKey key) {
        this.key = key;
    }

    /**
     * Десериализует полученный буфер данных в объект типа Request.
     * @return объект типа Request
     */
    @Override
    public Request get() {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(4096);
        try {
            socketChannel.read(readBuffer);
            return Serializers.deSerializeRequest(readBuffer.array());
        } catch (IOException e) {
            ServerConfig.logger.info("Клиент отключился.");
            try {
                socketChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.warning("Попытка десериализовать неправильный объект.");
            return null;
        }
    }
}
