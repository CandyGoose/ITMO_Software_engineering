package Common.exception;

/**
 * Исключение, возникающее при ошибке работы с базой данных
 */
public class DatabaseException extends Exception {
    /**
     * Конструктор исключения с заданным сообщением об ошибке
     * @param message Сообщение об ошибке
    */
    public DatabaseException(String message) {
        super(message);
    }
}