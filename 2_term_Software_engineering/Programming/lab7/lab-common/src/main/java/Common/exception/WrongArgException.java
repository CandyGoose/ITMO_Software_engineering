package Common.exception;

/**
 * Исключение, возникающее при передаче неверного аргумента.
 */
public class WrongArgException extends Exception {
    /**
     * Создает новый экземпляр исключения с заданным сообщением.
     * @param message сообщение, описывающее ошибку.
     */
    public WrongArgException(String message) {
        super(message);
    }
}
