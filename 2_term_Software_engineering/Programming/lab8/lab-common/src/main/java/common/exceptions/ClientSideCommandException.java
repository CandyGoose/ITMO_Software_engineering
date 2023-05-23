package common.exceptions;

/**
 * Исключение, которое указывает на ошибку в выполнении команды на стороне клиента.
 */
public class ClientSideCommandException extends RuntimeException {
    /**
     * Создает новый объект исключения ClientSideCommandException.
     */
    public ClientSideCommandException() {
        super("Эта команда поддерживается только на стороне клиента.");
    }
}
