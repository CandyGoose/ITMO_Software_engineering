package common.interaction;

import java.io.Serializable;

/**
 * Класс, представляющий запрос на выполнение команды в сетевом взаимодействии.
 * Реализует интерфейс Serializable для возможности передачи объекта по сети.
 */
public class Request implements Serializable {
    /**
     * Имя команды
     */
    private final String commandName;
    /**
     * Строковый аргумент команды
     */
    private final String commandStringArgument;
    /**
     * Объектный аргумент команды
     */
    private final Serializable commandObjectArgument;

    /**
     * Конструктор класса Request с объектным аргументом.
     * @param commandName имя команды.
     * @param commandStringArgument строковый аргумент команды.
     * @param commandObjectArgument объектный аргумент команды.
     */
    public Request(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
    }

    /**
     * Конструктор класса Request без объектного аргумента.
     * @param commandName имя команды.
     * @param commandStringArgument строковый аргумент команды.
     */
    public Request(String commandName, String commandStringArgument) {
        this(commandName, commandStringArgument, null);
    }

    /**
     * Конструктор класса Request без аргументов.
     */
    public Request() {
        this("","");
    }

    /**
     * Возвращает имя команды.
     * @return имя команды.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Возвращает строковый аргумент команды.
     * @return строковый аргумент команды.
     */
    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    /**
     * Возвращает объектный аргумент команды.
     * @return объектный аргумент команды.
     */
    public Object getCommandObjectArgument() {
        return commandObjectArgument;
    }

    /**
     * Проверяет, является ли запрос пустым (не содержит аргументов).
     * @return true, если запрос пустой, иначе - false.
     */
    public boolean isEmpty() {
        return commandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument == null;
    }

    /**
     * Возвращает строковое представление объекта Request.
     * @return строковое представление объекта Request.
     */
    @Override
    public String toString() {
        return "Request[" + commandName + ", " + commandStringArgument + ", " + commandObjectArgument + "]";
    }
}
