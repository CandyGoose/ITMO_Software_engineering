package Common.util;

import Common.interfaces.Data;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Утилитарный класс, предоставляющий методы для сериализации и десериализации объектов типа Data
 */
public class Serializers {

    /**
     * Приватный конструктор, чтобы предотвратить создание экземпляров класса
     */
    private Serializers() {
    }

    /**
     * Сериализует запрос типа Data в ByteBuffer для отправки по сети
     * @param request запрос, который нужно сериализовать
     * @return ByteBuffer, содержащий сериализованный запрос
     * @throws IOException если возникла ошибка ввода/вывода в процессе сериализации
     */
    public static ByteBuffer serializeRequest(Data request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.flush();
        ByteBuffer bufToSend = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bufToSend;
    }

    /**
     * Сериализует ответ типа Data в ByteBuffer для отправки по сети
     * @param response ответ, который нужно сериализовать
     * @return ByteBuffer, содержащий сериализованный ответ
     * @throws IOException если возникла ошибка ввода/вывода в процессе сериализации
     */
    public static ByteBuffer serializeResponse(Data response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        ByteBuffer bufToSend = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bufToSend;
    }

    /**
     * Десериализует запрос из массива байтов, принятых по сети
     * @param acceptedBuf массив байтов, содержащий сериализованный запрос
     * @return запрос типа Request, десериализованный из массива байтов
     * @throws IOException если возникла ошибка ввода/вывода в процессе десериализации
     * @throws ClassNotFoundException если класс Request не найден при десериализации
     */
    public static Request deSerializeRequest(byte[] acceptedBuf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(acceptedBuf);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    /**
     * Десериализует ответ из массива байтов, принятых по сети
     * @param acceptedBuf массив байтов, содержащий сериализованный ответ
     * @return ответ типа Response, десериализованный из массива байтов
     * @throws IOException если возникла ошибка ввода/вывода в процессе десериализации
     * @throws ClassNotFoundException если класс Response не найден при десериализации
     */
    public static Response deSerializeResponse(byte[] acceptedBuf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(acceptedBuf);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return response;
    }
}
