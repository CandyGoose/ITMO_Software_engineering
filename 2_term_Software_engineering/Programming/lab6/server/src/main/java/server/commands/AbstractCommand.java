package server.commands;

/**
 * Класс AbstractCommand - это абстрактный класс, который реализует интерфейс ICommand
 */
public abstract class AbstractCommand implements ICommand {
    private final String name;
    private final String usage;
    private final String description;

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

    @Override
    public String toString() {
        return name + " " + usage + " (" + description + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + usage.hashCode() + description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.getName()) && usage.equals(other.getUsage()) &&
                description.equals(other.getDescription());
    }
}