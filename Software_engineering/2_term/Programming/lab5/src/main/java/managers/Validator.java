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
                iterator.remove();
                Console.printError("Ошибка в значении ID, остановка приложения.");
                System.exit(0);}
            if(org.getName() == null || org.getName().equals("")) {
                iterator.remove();
                Console.printError("Ошибка в имени организации, остановка приложения.");
                System.exit(0);}
            if(org.getCoordinates() == null) {
                iterator.remove();
                Console.printError("Ошибка в координатах, остановка приложения.");
                System.exit(0);}
            else {if(org.getCoordinates().getX() > 741) {
                iterator.remove();
                Console.printError("Ошибка в координатах, остановка приложения.");
                System.exit(0);}
                if(org.getCoordinates().getY() == null) {
                    iterator.remove();
                    Console.printError("Ошибка в координатах, остановка приложения.");
                    System.exit(0);}
            }
            if(org.getCreationDate() == null) {
                iterator.remove();
                Console.printError("Ошибка в дате, остановка приложения.");
                System.exit(0);}
            if(org.getAnnualTurnover() == null || org.getAnnualTurnover() <= 0) {
                iterator.remove();
                Console.printError("Ошибка в значении годового оборота, остановка приложения.");
                System.exit(0);}
            if(org.getEmployeesCount() <= 0) {
                iterator.remove();
                Console.printError("Ошибка в количестве сотрудников, остановка приложения.");
                System.exit(0);}
            if(org.getType() == null) {
                iterator.remove();
                Console.printError("Ошибка в типе организации, остановка приложения.");
                System.exit(0);}
        }
        return organization;
    }
}