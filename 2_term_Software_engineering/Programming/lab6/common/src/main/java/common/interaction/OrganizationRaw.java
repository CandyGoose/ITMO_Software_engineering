package common.interaction;

import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;

import java.io.Serializable;

/**
 *  ласс дл€ хранени€ сырых данных об организации
 */
public class OrganizationRaw implements Serializable {
    /**
     * Ќазвание организации
     */
    private final String name;
    /**
     *  оординаты организации
     */
    private final Coordinates coordinates;
    /**
     * √одовой оборот организации
     */
    private final Float annualTurnover;
    /**
     * ѕолное название организации
     */
    private final String fullName;
    /**
     *  оличество сотрудников организации
     */
    private final long employeesCount;
    /**
     * “ип организации
     */
    private final OrganizationType type;
    /**
     * ѕочтовый адрес организации
     */
    private final Address postalAddress;

    /**
     *  онструктор класса
     * @param name - название организации
     * @param coordinates - координаты организации
     * @param annualTurnover - годовой оборот организации
     * @param fullName - полное название организации
     * @param employeesCount - количество сотрудников организации
     * @param type - тип организации
     * @param postalAddress - почтовый адрес организации
     */
    public OrganizationRaw(String name, Coordinates coordinates, Float annualTurnover, String fullName, long employeesCount, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    /**
     * ћетод дл€ получени€ названи€ организации
     * @return название организации
     */
    public String getName() {
        return name;
    }

    /**
     * ћетод дл€ получени€ координат организации
     * @return координаты организации
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * ћетод дл€ получени€ годового оборота организации
     * @return годовой оборот организации
     */
    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * ћетод дл€ получени€ полного названи€ организации
     * @return полное название организации
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * ћетод дл€ получени€ количества сотрудников организации
     * @return количество сотрудников организации
     */
    public long getEmployeesCount() {
        return employeesCount;
    }

    /**
     * ћетод дл€ получени€ типа организации
     * @return тип организации
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * ћетод дл€ получени€ почтового адреса организации
     * @return почтовый адрес организации
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * ¬озвращает строку с описанием объекта, состо€щую из значений всех полей.
     * @return строка с описанием объекта
     */
    @Override
    public String toString() {
        return "OrganizationRaw{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", annualTurnover=" + annualTurnover +
                ", fullName=" + fullName +
                ", employeesCount=" + employeesCount +
                ", type=" + type +
                ", postalAddress=" + postalAddress +
                '}';
    }
}
