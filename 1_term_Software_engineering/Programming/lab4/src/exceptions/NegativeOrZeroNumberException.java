package exceptions;

public class NegativeOrZeroNumberException extends RuntimeException{
    public NegativeOrZeroNumberException(){
        super("Получили отрицательное число или ноль, которых тут не должно быть");
    }
}
