package common.exceptions;

/**
 * Исключение, которое указывает на отсутствие необходимых разрешений для выполнения действия.
 */
public class UnauthorizedException extends Exception {
    /**
     * Создает новый объект исключения UnauthorizedException с сообщением по умолчанию.
     */
    public UnauthorizedException() {
        super("У вас нет необходимых разрешений для выполнения этого действия.");
    }
}
