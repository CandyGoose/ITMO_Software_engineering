package Client.CommandDispatcher;

import Common.exception.WrongAmountOfArgumentsException;
import Common.exception.WrongArgException;

import java.util.function.Function;
import java.util.function.Predicate;

public class CommandValidators {
    public static void validateAmountOfArgs(String[] args, int amountOfArgs) throws WrongAmountOfArgumentsException {
        if (args.length != amountOfArgs) {
            throw new WrongAmountOfArgumentsException("Неверное количество аргументов, количество аргументов для этой команды: " + amountOfArgs + ".");
        }
    }

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
