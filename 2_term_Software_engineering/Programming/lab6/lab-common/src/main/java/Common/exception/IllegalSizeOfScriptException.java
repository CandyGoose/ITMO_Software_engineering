package Common.exception;

/**
 * Исключение, возникающее при попытке выполнения скрипта, который превышает заданный размер.
 */
public class IllegalSizeOfScriptException extends Exception{
    /**
     * Создает новый экземпляр исключения с заданным сообщением.
     * @param m сообщение, описывающее причину возникновения исключения
     */
    public IllegalSizeOfScriptException(String m) {
        super(m);
    }
}
