package data;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, описывающий организацию. Объекты этого класса являются элементами коллекции
 */
public class Organization implements Comparable<Organization> {

    /**
     * Уникальный идентификатор организации.
     * Поле не может быть null.
     * Значение поля должно быть больше 0.
     * Значение этого поля должно быть уникальным.
     * Значение этого поля должно генерироваться автоматически.
     */
    private Long id;

    /**
     * Наименование организации.
     * Поле не может быть null.
     * Строка не может быть пустой.
     */
    private String name;

    /**
     * Координаты организации.
     * Поле не может быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания организации.
     * Поле не может быть null.
     * Значение этого поля должно генерироваться автоматически.
     */
    private ZonedDateTime creationDate;

    /**
     * Годовой оборот организации.
     * Поле не может быть null.
     * Значение поля должно быть больше 0.
     */
    private Float annualTurnover;

    /**
     * Полное наименование организации.
     * Поле не может быть null.
     */
    private String fullName;

    /**
     * Количество сотрудников организации.
     * Значение поля должно быть больше 0.
     */
    private long employeesCount;

    /**
     * Тип организации.
     * Поле не может быть null.
     */
    private OrganizationType type;

    /**
     * Адрес организации.
     * Поле может быть null.
     */
    private Address postalAddress;

    /**
     * Конструктор, задающий параметры для создания организации
     * @param id - уникальный номер
     * @param name - имя
     * @param coordinates - координаты
     * @param creationDate - дата создания
     * @param annualTurnover - годовой оборот
     * @param fullName - полное имя
     * @param employeesCount - количество сотрудников
     * @param type - тип
     * @param postalAddress - почтовый адрес
     */
    public Organization(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Float annualTurnover, String fullName, long employeesCount, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }


    /**
     * Возвращает id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }


    /**
     * Устанавливает значение id объекта
     *
     * @param id - id организации
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Возвращает имя
     *
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Возвращает координаты
     *
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }


    /**
     * Получает дату создания объекта
     *
     * @return creationDate
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    /**
     * Возвращает годовой оборот
     *
     * @return annualTurnover
     */
    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Возвращает полное название
     *
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Возвращает количество сотрудников
     *
     * @return employeesCount
     */
    public long getEmployeesCount() {
        return employeesCount;
    }


    /**
     * Возвращает тип организации
     *
     * @return type
     */
    public OrganizationType getType() {
        return type;
    }


    /**
     * Возвращает адрес
     *
     * @return postalAddress
     */
    public Address getPostalAddress() {
        return postalAddress;
    }


    /**
     * Этот метод используется для сравнения двух объектов
     *
     * @param o - объект для сравнения
     * @return id - id организации
     */
    @Override
    public int compareTo(Organization o) {
        return (int) (this.getId() - o.getId());
    }


    /**
     * Печатает информацию об организации пользователю в понятном формате
     *
     * @return toString() возвращает строковое представление объекта Organization
     */
    @Override
    public String toString() {
        String result = String.format("ID: %d\nИмя: %s\nКоординаты: {x: %f, y: %f}\nВремя создания: %s\nГодовой оборот: %f\nПолное название: %s\nКоличество сотрудников: %d\nТип организации: %s\n",
                getId(), getName(), getCoordinates().getX(), getCoordinates().getY(), getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss")), getAnnualTurnover(), getFullName(), getEmployeesCount(), getType());
        if(getPostalAddress() == null) result += "Адрес: null";
        else result += String.format("Адрес: %s", getPostalAddress().getStreet());
        return result;
    }
}