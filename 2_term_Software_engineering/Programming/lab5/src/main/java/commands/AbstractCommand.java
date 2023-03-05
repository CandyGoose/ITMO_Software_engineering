package commands;

/**
 * Класс AbstractCommand - это абстрактный класс, который реализует интерфейс ICommand
 */
public abstract class AbstractCommand implements ICommand{

    /**
     * Задает объект с именем
     */
    private final String name;
    /**
     * Задает объект с описанием
     */
    private final String description;

    /**
     * Задает объекты для использования
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

    /**
     * Возвращает строковое представление объекта
     * 
     * @return Название и описание команды
     */
    @Override
    public String toString() {
        return name + " (" + description + ")";
    }

    /**
     * Метод hashCode возвращает хэш-код для объекта
     * 
     * @return hash code
     */
    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    /**
     * Метод equals сравнивает имя и описание команд
     *
     * @param obj Объект для сравнения.
     * @return boolean type
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
}

