package common.network;

/**
 * Класс, представляющий ответ на запрос с информацией об исключении.
 */
public class ResponseWithException extends Response {
    /**
     * Ошибка
     */
    private Exception e;

    /**
     * Создает новый объект ответа с информацией об исключении и сообщением об ошибке.
     *
     * @param e исключение.
     */
    public ResponseWithException(Exception e) {
        super("The server responded with an error.");
        this.e = e;
    }

    /**
     * Возвращает исключение.
     *
     * @return исключение.
     */
    public Exception getException() {
        return e;
    }
}
