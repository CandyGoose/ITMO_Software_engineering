package common.interaction;

import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;

import java.io.Serializable;
import java.util.Objects;


public class OrganizationRaw implements Serializable {
    private final String name;
    private final Coordinates coordinates;
    private final Float annualTurnover;
    private final String fullName;
    private final long employeesCount;
    private final OrganizationType type;
    private final Address postalAddress;

    public OrganizationRaw(String name, Coordinates coordinates, Float annualTurnover, String fullName, long employeesCount, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }


    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public String getFullName() {
        return fullName;
    }

    public long getEmployeesCount() {
        return employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationRaw that = (OrganizationRaw) o;
        return Float.compare(that.employeesCount, employeesCount) == 0 && name.equals(that.name) && coordinates.equals(that.coordinates) && annualTurnover.equals(that.annualTurnover) && fullName.equals(that.fullName) && type == that.type && Objects.equals(postalAddress, that.postalAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, annualTurnover, fullName, employeesCount, type, postalAddress);
    }
}
