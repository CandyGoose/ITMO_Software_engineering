package managers;

import data.Organization;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Класс-менеджер коллекции организаций.
 * Содержит методы для работы с коллекцией, такие как добавление, удаление,
 * поиск по id, замена по id, сортировка, перемешивание, очистка и т.д.
 * Также возвращает информацию о коллекции.
 */
public class CollectionManager {
    /**
     * Коллекция организаций
     */
    @XStreamImplicit
    private LinkedList<Organization> organizationCollection;

    /**
     * Дата инициализации коллекции
     */
    private final ZonedDateTime creationDate;

    /**
     * Создает пустую коллекцию организаций и задает ее дату инициализации
     */
    public CollectionManager() {
        organizationCollection = new LinkedList<>();
        creationDate = ZonedDateTime.now();
    }

    /**
     * Возвращает дату инициализации коллекции
     * @return дата инициализации коллекции
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    /**
     * Возвращает коллекцию организаций
     * @return коллекция организаций
     */
    public LinkedList<Organization> getCollection() {
        return organizationCollection;
    }


    /**
     * Устанавливает коллекцию организаций
     * @param organizationCollection коллекция организаций
     */
    public void setCollection(LinkedList<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }


    /**
     * Возвращает организацию по заданному id
     * @param id id организации
     * @return организация с заданным id или null, если она не найдена
     */
    public Organization getById(Long id){
        for (Organization organization: organizationCollection) {
            if(Objects.equals(organization.getId(), id)) return organization;
        }
        return null;
    }

    /**
     * Заменяет организацию с заданным id на новую организацию
     * @param id id организации, которую нужно заменить
     * @param newValue новая организация
     */
    public void replaceById(Long id,Organization newValue){
        newValue.setId(id);
        organizationCollection
                .stream()
                .filter(organization -> Objects.equals(organization.getId(), id))
                .findFirst()
                .ifPresent(organization -> organizationCollection.set(organizationCollection.indexOf(organization), newValue));
    }


    /**
     * Добавляет организацию в коллекцию
     * @param organization организация для добавления
     */
    public void addToCollection(Organization organization){
        organizationCollection.add(organization);
    }


    /**
     * Удаляет организацию из коллекции
     * @param organization организация для удаления
     */
    public void removeFromCollection(Organization organization){
        organizationCollection.remove(organization);
    }


    /**
     * Сортирует коллекцию по умолчанию (по возрастанию id)
     */
    public void sortCollection(){
        Collections.sort(organizationCollection);
    }

    /**
     * Метод удаляет первый элемент из коллекции организаций.
     */
    public void removeFirstInCollection(){
        organizationCollection.poll();
    }

    /**
     * Метод удаляет организацию из коллекции по заданному идентификатору.
     * @param id идентификатор организации для удаления
     */
    public void removeByIdFromCollection(Long id){
        organizationCollection.stream()
                .filter(organization -> Objects.equals(organization.getId(), id))
                .findFirst()
                .ifPresent(this::removeFromCollection);
    }


    /**
     * Метод очищает коллекцию организаций.
     */
    public void clearCollection(){
        organizationCollection.clear();
    }


    /**
     * Метод перемешивает элементы коллекции организаций в случайном порядке.
     */
    public void shuffleCollection(){
        Collections.shuffle(organizationCollection);
    }


    /**
     * Метод возвращает множество значений количества сотрудников в коллекции организаций.
     * @return множество значений количества сотрудников
     */
    public Set<Long> getSetEmployeesCount(){
        return organizationCollection.stream()
                .map(Organization::getEmployeesCount)
                .collect(Collectors.toSet());
    }

    /**
     * Метод возвращает список значений годового оборота в коллекции организаций.
     * @return список значений годового оборота
     */
    public List<Float> getListAnnualTurnover(){
        return organizationCollection.stream()
                .map(Organization::getAnnualTurnover)
                .collect(Collectors.toList());
    }

    /**
     * Метод генерирует новый идентификатор для добавления организации в коллекцию.
     * @return новый идентификатор организации
     */
    public Long generateNewIdForCollection(){
        Long id = organizationCollection.stream()
                .mapToLong(Organization::getId)
                .filter(organization -> organization >= 0)
                .max().orElse(0);
        return id + 1;
    }

    /**
     * Метод возвращает информацию о коллекции организаций.
     * @return строка с типом коллекции, датой инициализации и количеством элементов
     */
    public String infoAboutCollection(){
        return "Тип: " + organizationCollection.getClass() + "\n" +
                "Дата инициализации: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss")) + "\n" +
                "Количество элементов в коллекции: " + organizationCollection.size();
    }

}