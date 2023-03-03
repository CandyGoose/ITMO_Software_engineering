package managers;

import data.Organization;

import java.util.Iterator;
import java.util.List;


/**
 * Класс, производящий проверку данных
 */
public class Validator {
    List<Organization> organization;

    /**
     * Эта функция задает объект organization
     */
    public Validator(List<Organization> organization) {
        this.organization = organization;
    }

    /**
     * Эта функция возвращает проверенный объект organization
     *
     * @return organization
     */
    public List<Organization> validate(){
        for(Iterator<Organization> iterator = organization.iterator(); iterator.hasNext(); ){
            Organization org = iterator.next();
            if(org.getId() == null || org.getId() <= 0) {
                iterator.remove();}
            if(org.getName() == null || org.getName().equals("")) {
                iterator.remove();}
            if(org.getCoordinates() == null) {
                iterator.remove();}
            else {
                if(org.getCoordinates().getX() > 741) {
                iterator.remove();}
                if(org.getCoordinates().getY() == null) {
                    iterator.remove();}
            }
            if(org.getCreationDate() == null) {
                iterator.remove();}
            if(org.getAnnualTurnover() == null || org.getAnnualTurnover() <= 0) {
                iterator.remove();}
            if(org.getEmployeesCount() <= 0) {
                iterator.remove();}
            if(org.getType() == null) {
                iterator.remove();}
        }
        return organization;
    }
}