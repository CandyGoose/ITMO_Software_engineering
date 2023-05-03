package Server.requestHandlers;

import Common.util.Request;
import Common.util.Serializers;
import Server.ServerConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Supplier;

public class RequestReader implements Supplier<Request> {

    private final SelectionKey key;

    public RequestReader(SelectionKey key) {
        this.key = key;
    }

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
