package common.exceptions;

/**
 * Исключение, выбрасываемое при ошибке использования команды.
 */
public class CommandUsageException extends Exception {
    /**
     * Создает исключение без сообщения об ошибке.
     */
    public CommandUsageException() {
        super();
    }

    /**
     * Создает исключение с заданным сообщением об ошибке.
     * @param message сообщение об ошибке
     */
    public CommandUsageException(String message) {
        super(message);
    }
}