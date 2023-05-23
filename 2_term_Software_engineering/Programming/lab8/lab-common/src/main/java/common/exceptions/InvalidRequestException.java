package common.exceptions;

/**
 * Исключение, которое указывает на неверный запрос или недопустимое состояние при выполнении команды.
 */
public class InvalidRequestException extends Exception {
    /**
     * Ключ локализации
     */
    private final String localeKey;

    /**
     * Создает новый объект исключения InvalidRequestException без сообщения и ключа локализации.
     */
    public InvalidRequestException() {
        localeKey = null;
    }

    /**
     * Создает новый объект исключения InvalidRequestException с заданным сообщением и ключом локализации.
     *
     * @param message сообщение об ошибке
     * @param localeKey ключ локализации для локализованного сообщения об ошибке
     */
    public InvalidRequestException(String message, String localeKey) {
        super(message);
        this.localeKey = localeKey;
    }

    /**
     * Создает новый объект исключения InvalidRequestException с заданным сообщением, причиной и ключом локализации.
     *
     * @param cause причина исключения
     * @param localeKey ключ локализации для локализованного сообщения об ошибке
     */
    public InvalidRequestException(Throwable cause, String localeKey) {
        super(cause);
        this.localeKey = localeKey;
    }

    /**
     * Создает новый объект исключения InvalidRequestException с заданным сообщением, причиной, ключом локализации.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     * @param localeKey ключ локализации для локализованного сообщения об ошибке
     */
    public InvalidRequestException(String message, Throwable cause, String localeKey) {
        super(message, cause);
        this.localeKey = localeKey;
    }

    /**
     * Создает новый объект исключения InvalidRequestException с заданным сообщением, причиной, флагами
     * разрешения подавления и записи трассировки стека, а также без ключа локализации.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     * @param enableSuppression флаг разрешения подавления
     * @param writableStackTrace флаг записи трассировки стека
     */
    public InvalidRequestException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        localeKey = null;
    }

    /**
     * Возвращает локализованное сообщение об ошибке. Если ключ локализации не задан, будет возвращено
     * обычное сообщение об ошибке.
     *
     * @return локализованное сообщение об ошибке или обычное сообщение об ошибке, если ключ локализации не задан
     */
    @Override
    public String getLocalizedMessage() {
        if (localeKey == null) {
            return getMessage();
        } else {
            return localeKey;
        }
    }
}
