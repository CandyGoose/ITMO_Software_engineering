package data;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Класс Organization представляет организацию
 */
public class Organization implements Comparable<Organization> {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Float annualTurnover;
    private String fullName;
    private long employeesCount;
    private OrganizationType type;
    private Address postalAddress;

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
     * Устанавливает id объекта
     *
     * @param id id организации
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
     * Возвращает координату
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
     * @param o Объект для сравнения
     * @return id
     */
    @Override
    public int compareTo(Organization o) {
        return (int) (this.getId() - o.getId());
    }



    /**
     * Печатает информацию об организации
     *
     * @return Метод toString() возвращает строковое представление объекта Organization.
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