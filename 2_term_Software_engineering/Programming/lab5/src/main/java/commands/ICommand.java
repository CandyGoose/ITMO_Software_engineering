package commands;

/**
 * Интерфейс ICommand определяет методы, которые необходимы для реализации любой команды. Интерфейс содержит три метода:
 * getDescription, getName и execute.
 */
public interface ICommand {

    /**
     * Получает описание команды.
     * @return Описание команды в виде строки.
     */
    String getDescription();

    /**
     * Получает имя команды.
     * @return Имя команды в виде строки.
     */
    String getName();

    /**
     Метод execute выполняет действия, связанные с данной командой.
     @param argument аргумент, который необходим для выполнения команды.
     @return true, если команда была успешно выполнена, иначе false.
     */
    boolean execute(String argument);
}
