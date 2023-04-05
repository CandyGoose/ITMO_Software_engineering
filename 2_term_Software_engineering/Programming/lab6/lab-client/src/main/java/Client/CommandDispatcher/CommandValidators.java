package Client.CommandDispatcher;

import Common.exception.WrongAmountOfArgumentsException;
import Common.exception.WrongArgException;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Класс CommandValidators предоставляет методы для валидации аргументов команд.
 */
public class CommandValidators {
    /**
     * Проверяет, что количество аргументов команды соответствует заданному количеству.
     *
     * @param args аргументы команды
     * @param amountOfArgs ожидаемое количество аргументов
     * @throws WrongAmountOfArgumentsException если количество аргументов не совпадает с ожидаемым
     */
    public static void validateAmountOfArgs(String[] args, int amountOfArgs) throws WrongAmountOfArgumentsException {
        if (args.length != amountOfArgs) {
            throw new WrongAmountOfArgumentsException("Неверное количество аргументов, количество аргументов для этой команды: " + amountOfArgs + ".");
        }
    }

    /**
     * Проверяет аргумент команды на соответствие предикату.
     *
     * @param predicate функция, принимающая значение аргумента и возвращающая true, если оно валидно
     * @param wrong сообщение об ошибке, которое будет выброшено, если аргумент не валиден
     * @param function функция, которая преобразует строковое представление аргумента в нужный тип
     * @param argument строковое представление аргумента
     * @param <T> тип аргумента
     * @return значение аргумента, если оно валидно
     * @throws WrongArgException если аргумент не валиден
     * @throws IllegalArgumentException если не удалось преобразовать строку аргумента в нужный тип
     */
    public static <T> T validateArg(Predicate<Object> predicate,
                                    String wrong,
                                    Function<String, T> function,
                                    String argument) throws WrongArgException, IllegalArgumentException {
        T value = function.apply(argument);
        if (predicate.test(value)) {
            return value;
        } else {
            throw new WrongArgException(wrong);
        }
    }
}
