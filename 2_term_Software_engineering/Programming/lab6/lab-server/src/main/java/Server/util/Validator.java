package Server.util;

import Common.data.Organization;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Класс Validator предназначен для валидации списка организаций.
 */
public class Validator {

    /**
     * Список организаций, которые необходимо проверить.
     */
    List<Organization> organization;

    /**
     * Конструктор класса.
     *
     * @param organization Список организаций, которые необходимо проверить.
     */
    public Validator(List<Organization> organization) {
        this.organization = organization;
    }

    /**
     * Метод валидации списка организаций.
     *
     * @return Список организаций, которые прошли валидацию.
     */
    public List<Organization> validate() {
        Set<Long> idSet = new HashSet<>();
        Iterator<Organization> iterator = organization.iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            if (org.getId() == null || org.getId() <= 0 || !idSet.add(org.getId())) iterator.remove();
            if (org.getName() == null || org.getName().equals("")) iterator.remove();
            if (org.getCoordinates() == null) iterator.remove();
            else {
                if (org.getCoordinates().getX() > 741) iterator.remove();
                if (org.getCoordinates().getY() == null) iterator.remove();
            }
            if (org.getCreationDate() == null) iterator.remove();
            if (org.getAnnualTurnover() == null || org.getAnnualTurnover() <= 0) iterator.remove();
            if (org.getFullName() == null || org.getFullName().equals("")) iterator.remove();
            if (org.getEmployeesCount() <= 0) iterator.remove();
            if (org.getType() == null) iterator.remove();
        }
        return organization;
    }
}