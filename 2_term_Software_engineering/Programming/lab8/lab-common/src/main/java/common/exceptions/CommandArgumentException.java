package common.exceptions;

/**
 * Исключение, которое указывает на ошибку в аргументах команды.
 */
public class CommandArgumentException extends Exception {
    /**
     * Создает новый объект исключения CommandArgumentException.
     */
    public CommandArgumentException() {
    }

    /**
     * Создает новый объект исключения CommandArgumentException с заданным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public CommandArgumentException(String message) {
        super(message);
    }

    /**
     * Создает новый объект исключения CommandArgumentException с заданной причиной.
     *
     * @param cause причина исключения
     */
    public CommandArgumentException(Throwable cause) {
        super(cause);
    }

    /**
     * Создает новый объект исключения CommandArgumentException с заданным сообщением и причиной.
     *
     * @param message сообщение об ошибке
     * @param cause   причина исключения
     */
    public CommandArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Создает новый объект исключения CommandArgumentException с заданным сообщением, причиной, флагом активации подавления и
     * флагом возможности записи стека вызовов.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     * @param enableSuppression флаг активации подавления
     * @param writableStackTrace флаг возможности записи стека вызовов
     */
    public CommandArgumentException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Создает новый объект исключения CommandArgumentException для команды с заданным именем, указывая ожидаемое количество
     * аргументов и фактическое количество полученных аргументов.
     *
     * @param commandName имя команды
     * @param need ожидаемое количество аргументов
     * @param received фактическое количество полученных аргументов
     */
    public CommandArgumentException(String commandName, int need, int received) {
        super(commandName + " необходимо всего аргументов: " + need + ". Получено " + received);
    }

    /**
     * Создает новый объект исключения CommandArgumentException для команды с заданным именем и фактическим количеством полученных
     * аргументов.
     *
     * @param commandName имя команды
     * @param received фактическое количество полученных аргументов
     */
    public CommandArgumentException(String commandName, int received) {
        super(commandName + " не нуждается в аргументах. Получено " + received);
    }
}
