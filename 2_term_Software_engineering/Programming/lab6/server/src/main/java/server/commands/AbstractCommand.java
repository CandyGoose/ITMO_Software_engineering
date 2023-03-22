package server.commands;

/**
 * Класс AbstractCommand - это абстрактный класс, который реализует интерфейс ICommand
 */
public abstract class AbstractCommand implements ICommand {
    /**
     * Название команды.
     */
    private final String name;

    /**
     * Пример использования команды.
     */
    private final String usage;

    /**
     * Описание команды.
     */
    private final String description;

    /**
     * Конструктор класса, который устанавливает значения полей name, usage и description.
     * @param name название команды.
     * @param usage пример использования команды.
     * @param description описание команды.
     */
    public AbstractCommand(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    /**
     * @return Название команды.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Применение команды.
     */
    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * @return Описание команды.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Метод, возвращающий строковое представление команды.
     * @return строковое представление команды.
     */
    @Override
    public String toString() {
        return name + " " + usage + " (" + description + ")";
    }
}