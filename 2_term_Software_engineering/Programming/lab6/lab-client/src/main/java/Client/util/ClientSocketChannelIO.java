package Client.util;

import Common.interfaces.Data;
import Common.interfaces.IOController;
import Common.util.Serializers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Класс ClientSocketChannelIO для обработки ввода/вывода через сетевой канал.
 * Позволяет отправлять и получать данные с сервера.
 */
public class ClientSocketChannelIO implements IOController {
    /**
     * Канал сокета, используемый для связи с сервером.
     */
    private final SocketChannel channel;

    /**
     * Конструктор ClientSocketChannelIO.
     * @param channel канал SocketChannel, через который происходит взаимодействие с сервером.
     */
    public ClientSocketChannelIO(SocketChannel channel) {
        this.channel = channel;
    }

    /**
     * Отправляет данные на сервер.
     * @param data объект типа Data, который нужно отправить.
     * @throws IOException если произошла ошибка при передаче данных на сервер.
     */
    @Override
    public void send(Data data) throws IOException {
        ByteBuffer buffer = Serializers.serializeRequest(data);
        channel.write(buffer);
    }

    /**
     * Получает данные с сервера.
     * @return объект типа Data, полученный с сервера.
     * @throws IOException если произошла ошибка при передаче данных от сервера.
     * @throws ClassNotFoundException если не удалось распаковать полученные данные.
     */
    @Override
    public Data receive() throws IOException, ClassNotFoundException {
        ByteBuffer readBuffer = ByteBuffer.allocate(channel.socket().getReceiveBufferSize());
        channel.read(readBuffer);
        return Serializers.deSerializeResponse(readBuffer.array());
    }
}
