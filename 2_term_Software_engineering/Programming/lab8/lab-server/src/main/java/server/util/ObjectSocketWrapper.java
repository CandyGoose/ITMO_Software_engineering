package server.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import common.network.ObjectEncoder;

/**
 * Класс ObjectSocketWrapper представляет обертку над сокетом для работы с объектами
 */
public class ObjectSocketWrapper {

    /**
     * Сокет для обертывания
     */
    private final Socket socket;

    /**
     * Буфер размера сообщения
     */
    private byte[] sizeInBuffer;

    /**
     * Буфер сообщения
     */
    private byte[] payloadBuffer;

    /**
     * Позиция в буфере размера сообщения
     */
    private int sizeInBufferPos = 0;

    /**
     * Позиция в буфере сообщения
     */
    private int payloadBufferPos = 0;

    /**
     * Конструктор класса ObjectSocketWrapper.
     *
     * @param socket сокет для обертывания
     */
    public ObjectSocketWrapper(Socket socket) {
        this.socket = socket;
        this.sizeInBuffer = new byte[Integer.BYTES];
        this.payloadBuffer = null;
    }

    /**
     * Отправляет сообщение через сокет.
     *
     * @param object объект для отправки
     * @return true, если сообщение успешно отправлено, иначе false
     */
    public boolean sendMessage(Object object) {
        try {
            byte[] msg = ObjectEncoder.encodeObject(object).array();

            socket.getOutputStream().write(msg);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Проверяет наличие сообщения для чтения из сокета.
     *
     * @return true, если сообщение доступно для чтения, иначе false
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public boolean checkForMessage() throws IOException {
        try {
            if (payloadBuffer != null && payloadBufferPos >= payloadBuffer.length) {
                return true;
            }

            int readBytes = socket.getInputStream().read(sizeInBuffer, sizeInBufferPos, Integer.BYTES - sizeInBufferPos);

            if (readBytes == -1) {
                throw new IOException("Не удалось прочитать байты из сокета.");
            }

            sizeInBufferPos += readBytes;
            if (sizeInBufferPos < Integer.BYTES) {
                return false;
            }

            if (payloadBuffer == null) {
                payloadBuffer = new byte[ByteBuffer.wrap(sizeInBuffer).getInt()];
            }

            readBytes = socket.getInputStream().read(payloadBuffer, payloadBufferPos, payloadBuffer.length - payloadBufferPos);
            payloadBufferPos += readBytes;

            return payloadBufferPos >= payloadBuffer.length;
        } catch (SocketTimeoutException e) {
            return false;
        }
    }

    /**
     * Получает сообщение из сокета.
     *
     * @return объект-сообщение
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public Object getPayload() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(payloadBuffer);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Очищает буферы чтения.
     */
    public void clearInBuffer() {
        sizeInBuffer = new byte[Integer.BYTES];
        payloadBuffer = null;
        sizeInBufferPos = 0;
        payloadBufferPos = 0;
    }

    /**
     * Возвращает сокет.
     *
     * @return сокет
     */
    public Socket getSocket() {
        return socket;
    }
}
