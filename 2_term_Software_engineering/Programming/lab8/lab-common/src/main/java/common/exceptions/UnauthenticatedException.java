package common.exceptions;

/**
 * Исключение, которое указывает на неаутентифицированное состояние пользователя при выполнении команды.
 */
public class UnauthenticatedException extends Exception {
    /**
     * Создает новый объект исключения UnauthenticatedException с сообщением по умолчанию.
     */
    public UnauthenticatedException() {
        super("Войдите в систему для выполнения команд.");
    }
}
