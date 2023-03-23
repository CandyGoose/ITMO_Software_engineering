package common.interaction;

import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;

import java.io.Serializable;

/**
 * ����� ��� �������� ����� ������ �� �����������
 */
public class OrganizationRaw implements Serializable {
    /**
     * �������� �����������
     */
    private final String name;
    /**
     * ���������� �����������
     */
    private final Coordinates coordinates;
    /**
     * ������� ������ �����������
     */
    private final Float annualTurnover;
    /**
     * ������ �������� �����������
     */
    private final String fullName;
    /**
     * ���������� ����������� �����������
     */
    private final long employeesCount;
    /**
     * ��� �����������
     */
    private final OrganizationType type;
    /**
     * �������� ����� �����������
     */
    private final Address postalAddress;

    /**
     * ����������� ������
     * @param name - �������� �����������
     * @param coordinates - ���������� �����������
     * @param annualTurnover - ������� ������ �����������
     * @param fullName - ������ �������� �����������
     * @param employeesCount - ���������� ����������� �����������
     * @param type - ��� �����������
     * @param postalAddress - �������� ����� �����������
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
     * ����� ��� ��������� �������� �����������
     * @return �������� �����������
     */
    public String getName() {
        return name;
    }

    /**
     * ����� ��� ��������� ��������� �����������
     * @return ���������� �����������
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * ����� ��� ��������� �������� ������� �����������
     * @return ������� ������ �����������
     */
    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * ����� ��� ��������� ������� �������� �����������
     * @return ������ �������� �����������
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * ����� ��� ��������� ���������� ����������� �����������
     * @return ���������� ����������� �����������
     */
    public long getEmployeesCount() {
        return employeesCount;
    }

    /**
     * ����� ��� ��������� ���� �����������
     * @return ��� �����������
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * ����� ��� ��������� ��������� ������ �����������
     * @return �������� ����� �����������
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * ���������� ������ � ��������� �������, ��������� �� �������� ���� �����.
     * @return ������ � ��������� �������
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
