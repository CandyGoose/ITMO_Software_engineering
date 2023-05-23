package common.exceptions;

/**
 * Исключение, которое указывает на недопустимое поле или значение поля.
 */
public class InvalidFieldException extends Exception {
    /**
     * Ключ локализации
     */
    private final String localeKey;

    /**
     * Создает новый объект исключения InvalidFieldException без сообщения и ключа локализации.
     */
    public InvalidFieldException() {
        localeKey = null;
    }

    /**
     * Создает новый объект исключения InvalidFieldException с заданным сообщением и без ключа локализации.
     *
     * @param message сообщение об ошибке
     */
    public InvalidFieldException(String message) {
        super(message);
        localeKey = null;
    }

    /**
     * Создает новый объект исключения InvalidFieldException с заданным сообщением и ключом локализации.
     *
     * @param message сообщение об ошибке
     * @param localeKey ключ локализации для локализованного сообщения об ошибке
     */
    public InvalidFieldException(String message, String localeKey) {
        super(message);
        this.localeKey = localeKey;
    }

    /**
     * Создает новый объект исключения InvalidFieldException с заданной причиной и без ключа локализации.
     *
     * @param cause причина исключения
     */
    public InvalidFieldException(Throwable cause) {
        super(cause);
        localeKey = null;
    }

    /**
     * Создает новый объект исключения InvalidFieldException с заданным сообщением и причиной и без ключа локализации.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     */
    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
        localeKey = null;
    }

    /**
     * Создает новый объект исключения InvalidFieldException с заданным сообщением, причиной, флагами
     * разрешения подавления и записи трассировки стека, а также без ключа локализации.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     * @param enableSuppression флаг разрешения подавления
     * @param writableStackTrace флаг записи трассировки стека
     */
    public InvalidFieldException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        localeKey = null;
    }

    /**
     * Возвращает ключ локализации для локализованного сообщения об ошибке. Если ключ локализации не задан,
     * будет возвращено null.
     *
     * @return ключ локализации или null, если ключ не задан
     */
    public String getLocaleKey() {
        return localeKey;
    }
}
