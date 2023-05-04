package Common.exception;

/**
 * Этот класс используется для создания исключения, когда количество переданных элементов не соответствует ожидаемому значению.
 */
public class WrongAmountOfArgumentsException extends Exception {
    /**
     * Создает новый экземпляр исключения с заданным сообщением.
     * @param message сообщение, описывающее причину возникновения исключения
     */
    public WrongAmountOfArgumentsException(String message) {
        super(message);
    }
}
