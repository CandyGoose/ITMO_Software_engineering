package common.network;

import java.io.Serializable;

/**
 * Класс, представляющий ответ на запрос.
 */
public class Response implements Serializable {
    /**
     * Сообщение
     */
    private String message = "";

    /**
     * Ключ локализации
     */
    private String localeKey = null;

    /**
     * Параметры
     */
    private Object[] params = new Object[] {};

    /**
     * Создает новый объект ответа.
     */
    public Response() {
    }

    /**
     * Создает новый объект ответа с указанным сообщением.
     *
     * @param message сообщение ответа.
     */
    public Response(String message) {
        this.message = message;
    }

    /**
     * Создает новый объект ответа с указанным сообщением, ключом локализации и параметрами.
     *
     * @param message сообщение ответа.
     * @param localeKey ключ локализации.
     * @param params параметры ответа.
     */
    public Response(String message, String localeKey, Object[] params) {
        this(message);
        this.localeKey = localeKey;
        this.params = params;
    }

    /**
     * Возвращает ключ локализации.
     *
     * @return ключ локализации.
     */
    public String getLocaleKey() {
        return localeKey;
    }

    /**
     * Возвращает параметры ответа.
     *
     * @return параметры ответа.
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * Возвращает сообщение ответа.
     *
     * @return сообщение ответа.
     */
    public String getMessage() {
        return message;
    }
}
