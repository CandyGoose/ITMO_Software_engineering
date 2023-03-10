package commands;

/**
 * Класс AbstractCommand - это абстрактный класс, который реализует интерфейс ICommand
 */
public abstract class AbstractCommand implements ICommand{

    /**
     * Имя команды
     */
    private final String name;

    /**
     * Описание команды
     */
    private final String description;

    /**
     * Конструктор для класса
     */
    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Возвращает имя команды
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }
}

