package Common.data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Organization implements Comparable<Organization>, Serializable{
    @NotNull
    @Positive(message = "ID должен быть больше нуля!")
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull(message = "Имя не может быть null")
    @NotBlank(message = "Имя должно содержать хотя бы 1 символ")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull(message = "Координаты не могут быть null")
    private Coordinates coordinates; //Поле не может быть null
    @NotNull(message = "Дата не может быть null")
    @PastOrPresent
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull(message = "Годовой оборот не может быть null")
    @Positive(message = "Годовой оборот должен быть больше нуля!")
    private Float annualTurnover;
    @NotNull(message = "Полное имя не может быть null")
    private String fullName;
    @Positive(message = "Количество сотрудников должно быть больше нуля!")
    private long employeesCount;
    @NotNull(message = "Тип не может быть null")
    private OrganizationType type; //Поле не может быть null
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

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

    @Override
    public int compareTo(Organization o) {
        return Long.compare(this.getId(), o.getId());
    }


    /**
     * Печатает информацию об организации пользователю в понятном формате
     *
     * @return toString() возвращает строковое представление объекта Organization
     */
    @Override
    public String toString() {
        String result = String.format("ID: %d\nНазвание: %s\nКоординаты: {x: %.2f, y: %.2f}\nВремя создания: %s\nГодовой оборот: %.2f\nПолное название: %s\nКоличество сотрудников: %d\nТип организации: %s\n",
                getId(), getName(), getCoordinates().getX(), getCoordinates().getY(), getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss")), getAnnualTurnover(), getFullName(), getEmployeesCount(), getType());
        if(getPostalAddress() == null) result += "Адрес: null\n";
        else result += String.format("Адрес: %s", getPostalAddress().getStreet());
        return result;
    }
}
