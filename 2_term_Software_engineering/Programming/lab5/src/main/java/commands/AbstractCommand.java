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

    /**
     * Метод toString возвращает имя и описание команды в формате строки.
     *
     * @return строковое представление команды
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
     * Метод equals сравнивает текущий объект с переданным объектом.
     * Он Переопределяет метод equals() для класса AbstractCommand. Метод сравнивает текущий объект с переданным
     * объектом и возвращает true, если они равны, и false в противном случае. Сначала метод проверяет, являются ли
     * сравниваемые объекты одним и тем же объектом, затем проверяет, не является ли переданный объект null, после
     * проверяет, принадлежат ли сравниваемые объекты к одному и тому же классу. Если все проверки пройдены успешно,
     * метод сравнивает имена и описания объектов с помощью методов equals() для соответствующих строковых значений и
     * возвращает результат сравнения.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, и false в противном случае
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

