package exceptions;

public class CreatedMoreThanOneException extends Exception {

    public CreatedMoreThanOneException(Class<?> classIn){
        super("Попытка создать еще один объект класса, который уже создан ранее: " + classIn);

    }

}
