package common.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Утилитный класс для кодирования объектов.
 */
public final class ObjectEncoder {
    private ObjectEncoder() {
    }

    /**
     * Кодирует объект в ByteBuffer.
     *
     * @param object объект для кодирования.
     * @return ByteBuffer, содержащий закодированный объект.
     * @throws IOException если возникла ошибка при кодировании объекта.
     */
    public static ByteBuffer encodeObject(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);

        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + baos.size());

        buffer.putInt(baos.size());
        buffer.put(baos.toByteArray());

        return buffer;
    }
}
