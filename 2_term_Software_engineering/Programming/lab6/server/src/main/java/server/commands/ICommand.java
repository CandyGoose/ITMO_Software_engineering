package server.commands;

/**
 * ICommand - интерфейс, который определяет методы, необходимые для реализации любой команды. Он содержит три метода:
 * getDescription, getName, getUsage и execute.
 */
public interface ICommand {
    /**
     * Метод getDescription() возвращает описание команды.
     * @return описание команды.
     */
    String getDescription();
    /**
     * Метод getName() возвращает имя команды.
     * @return имя команды.
     */
    String getName();
    /**
     * Метод getUsage() возвращает пример использования команды.
     * @return пример использования команды.
     */
    String getUsage();
    /**
     * Метод execute() выполняет команду.
     * @param commandStringArgument строковый аргумент команды.
     * @param commandObjectArgument объектный аргумент команды.
     * @return true, если команда была выполнена успешно, и false в противном случае.
     */
    boolean execute(String commandStringArgument, Object commandObjectArgument);
}
