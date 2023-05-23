package common.exceptions;

/**
 * Исключение, которое указывает на то, что команда с заданным именем не найдена.
 */
public class CommandNotFoundException extends Exception {
    /**
     * Создает новый объект исключения CommandNotFoundException с заданным именем команды.
     *
     * @param name имя команды, которая не найдена
     */
    public CommandNotFoundException(String name) {
        super("Не удается найти команду с таким именем " + name + ".");
    }
}
