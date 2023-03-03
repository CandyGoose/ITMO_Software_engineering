package managers;

import data.Organization;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Класс Collection Manager управляет всей коллекцией в приложении
 */
public class CollectionManager {
    @XStreamImplicit
    private LinkedList<Organization> organizationCollection;

    private final ZonedDateTime creationDate;

    public CollectionManager() {
        organizationCollection = new LinkedList<>();
        creationDate = ZonedDateTime.now();
    }

    /**
     * Получение даты создания объекта
     *
     * @return creationDate
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    /**
     * Эта функция возвращает коллекцию организаций
     *
     * @return organizationCollection
     */
    public LinkedList<Organization> getCollection() {
        return organizationCollection;
    }


    /**
     * Функция set Collection устанавливает в поле organization Collection значение параметра organizationCollection
     *
     * @param organizationCollection Коллекция организаций
     */
    public void setCollection(LinkedList<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }


    /**
     * Присвоение id
     *
     * @param id Идентификатор организации, который необходимо получить
     * @return ответная реакция
     */
    public Organization getById(Long id){
        for (Organization organization: organizationCollection) {
            if(organization.getId() == id) return organization;
        }
        return null;
    }

    /**
     * Замена организацию с заданным идентификатором новым значением
     *
     * @param id Идентификатор организации, нуждающийся замене
     * @param newValue Новое значение, которое необходимо установить
     */
    public void replaceById(Long id,Organization newValue){
        newValue.setId(id);
        organizationCollection
                .stream()
                .filter(organization -> organization.getId() == id)
                .findFirst()
                .ifPresent(organization -> organizationCollection.set(organizationCollection.indexOf(organization), newValue));
    }


    /**
     * Добавить организацию в коллекцию организаций
     *
     * @param organization Организация, которую нужно добавить в коллекцию
     */
    public void addToCollection(Organization organization){
        organizationCollection.add(organization);
    }


    /**
     * Удалить организацию из коллекций организаций
     *
     * @param organization Организация, которую нужно удалить из коллекции
     */
    public void removeFromCollection(Organization organization){
        organizationCollection.remove(organization);
    }


    /**
     * Сортирует коллекцию
     */
    public void sortCollection(){
        Collections.sort(organizationCollection);
    }

    /**
     * Удалить первый элемент из коллекции
     */
    public void removeFirstInCollection(){
        organizationCollection.poll();
    }

    /**
     * Удаление организации из коллекции, если она существует
     *
     * @param id Идентификатор организации, которую нужно удалить
     */
    public void removeByIdFromCollection(Long id){
        organizationCollection.stream()
                .filter(organization -> organization.getId() == id)
                .findFirst()
                .ifPresent(this::removeFromCollection);
    }


    /**
     * Очистить коллекцию всех организаций
     */
    public void clearCollection(){
        organizationCollection.clear();
    }


    /**
     * Эта функция перемешивает коллекцию организаций
     */
    public void shuffleCollection(){
        Collections.shuffle(organizationCollection);
    }



    /**
     * Возврат максимального значения идентификатора коллекции, иначе 0
     *
     * @return Идентификатор организации, которая была только что добавлена
     */
    public Long generateNewIdForCollection(){
        Long id = organizationCollection.stream()
                .mapToLong(Organization::getId)
                .filter(organization -> organization >= 0)
                .max().orElse(0);
        return id + 1;
    }

    /**
     * Эта функция возвращает строку, содержащую информацию о коллекции
     *
     * @return информация о коллекции
     */
    public String infoAboutCollection(){
        return "Тип: " + organizationCollection.getClass() + "\n" +
                "Дата инициализации: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss")) + "\n" +
                "Количество элементов в коллекции: " + organizationCollection.size();
    }


    /**
     * Получение сета из всех значений количества сотрудников в системе
     *
     * @return Сет из всех значений количества сотрудников в системе
     */
    public Set<Long> getSetEmployeesCount(){
        return organizationCollection.stream()
                .map(Organization::getEmployeesCount)
                .collect(Collectors.toSet());
    }

    /**
     * Получение списка из всех значений годового оборота в системе
     *
     * @return Список из всех значений годового оборота в системе
     */
    public List<Float> getListAnnualTurnover(){
        return organizationCollection.stream()
                .map(Organization::getAnnualTurnover)
                .collect(Collectors.toList());
    }
}