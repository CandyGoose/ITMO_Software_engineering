package client.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import common.network.ObjectEncoder;

/**
 * Класс ObjectSocketChannelWrapper обеспечивает обертку для SocketChannel и предоставляет методы для отправки и получения объектов.
 */
public class ObjectSocketChannelWrapper {

    /**
     * Сокет
     */
    private final SocketChannel socket;

    /**
     * Буфер для хранения размера сообщения в байтах.
     */
    private ByteBuffer sizeIntBuffer = ByteBuffer.allocate(Integer.BYTES);

    /**
     * Буфер для хранения полезной нагрузки сообщения.
     */
    private ByteBuffer payloadBuffer = null;

    /**
     * Конструктор класса.
     *
     * @param socket SocketChannel для обертки
     */
    public ObjectSocketChannelWrapper(SocketChannel socket) {
        this.socket = socket;
    }

    /**
     * Отправка объекта через SocketChannel.
     *
     * @param object отправляемый объект
     * @throws IOException при ошибке ввода-вывода
     */
    public void sendMessage(Object object) throws IOException {
        ByteBuffer outBuffer = ObjectEncoder.encodeObject(object);
        outBuffer.flip();

        while (outBuffer.hasRemaining()) {
            socket.write(outBuffer);
        }
    }

    /**
     * Проверка на наличие полученного сообщения в SocketChannel.
     *
     * @return true, если получено полное сообщение, иначе false
     * @throws IOException при ошибке ввода-вывода
     */
    public boolean checkForMessage() throws IOException {
        if (payloadBuffer != null && !payloadBuffer.hasRemaining()) {
            return true;
        }

        socket.read(sizeIntBuffer);
        if (sizeIntBuffer.hasRemaining()) {
            return false;
        }

        if (payloadBuffer == null) {
            payloadBuffer = ByteBuffer.allocate(sizeIntBuffer.getInt(0));
        }

        socket.read(payloadBuffer);
        return !payloadBuffer.hasRemaining();
    }

    /**
     * Получение полученного объекта из SocketChannel.
     *
     * @return полученный объект
     * @throws IOException при ошибке ввода-вывода
     */
    public Object getPayload() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(payloadBuffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Очистка буферов.
     */
    public void clearInBuffer() {
        sizeIntBuffer.clear();
        payloadBuffer = null;
    }

    /**
     * Получение SocketChannel из обертки.
     *
     * @return SocketChannel
     */
    public SocketChannel getSocket() {
        return socket;
    }
}
