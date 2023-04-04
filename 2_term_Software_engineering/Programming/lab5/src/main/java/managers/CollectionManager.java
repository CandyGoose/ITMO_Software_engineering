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
    public void replaceById(Long id, Organization newValue){
        newValue.setId(id);
        organizationCollection
                .stream() // создает поток из коллекции organizationCollection
                .filter(organization -> Objects.equals(organization.getId(), id)) // фильтрует поток, оставляя только те объекты Organization, у которых значение поля ID равно переданному методу значению id
                .findFirst() // возвращает первый объект из отфильтрованного потока, результатом выполнения этого метода будет либо один объект типа Optional<Organization>, либо пустой Optional
                .ifPresent(organization -> organizationCollection.set(organizationCollection.indexOf(organization), newValue)); // если найден объект с заданным ID (Optional не пустой), то происходит замена этого объекта на переданный объект newValue
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
    public void removeFirstInCollection(){ organizationCollection.poll(); } // удаляет первый элемент (head) из коллекции organizationCollection


    /**
     * Метод удаляет организацию из коллекции по заданному идентификатору.
     * @param id идентификатор организации для удаления
     */
    public void removeByIdFromCollection(Long id){
        organizationCollection.stream() // получение последовательного потока элементов из коллекции organizationCollection
                .filter(organization -> Objects.equals(organization.getId(), id)) // выбираем только те объекты Organization, у которых id совпадает с переданным значением id
                .findFirst() // возвращает первый элемент из потока, удовлетворяющий условию, представленному в фильтре, если такого элемента нет, метод возвращает пустое значение.
                .ifPresent(this::removeFromCollection); // проверяет, присутствует ли значение в Optional (возвращаемое значение метода findFirst()) и, если это так, то указывает на ссылку на метод removeFromCollection() текущего объекта для удаления
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
        return organizationCollection.stream() // создание потока данных
                .map(Organization::getEmployeesCount) // применяется к каждому элементу потока данных, получая значение поля employeesCount для каждого объекта типа Organization в коллекции, результатом выполнения метода map() является новый поток данных, содержащий значения поля employeesCount
                .collect(Collectors.toSet()); // собирает все уникальные значения в Set, который затем возвращается из метода getSetEmployeesCount()
    }

    /**
     * Метод возвращает список значений годового оборота в коллекции организаций.
     * @return список значений годового оборота
     */
    public List<Float> getListAnnualTurnover(){
        return organizationCollection.stream() // создание потока данных
                .map(Organization::getAnnualTurnover) // применяется к каждому элементу потока данных, получая значение поля annualTurnover для каждого объекта типа Organization в коллекции, результатом выполнения метода map() является новый поток данных, содержащий значения поля AnnualTurnover
                .collect(Collectors.toList()); // собирает все значения в List
    }

    /**
     * Метод генерирует новый идентификатор для добавления организации в коллекцию.
     * @return новый идентификатор организации
     */
    public Long generateNewIdForCollection(){
        Long id = organizationCollection.stream() // создает поток объектов класса Organization,
                .mapToLong(Organization::getId) // преобразует каждый объект Organization в его id и создает поток из этих значений
                .filter(organization -> organization >= 0) // фильтрует id, чтобы получить только положительные значения (ID организации не может быть отрицательным)
                .max().orElse(0); // находит максимальное значение id из потока и возвращает его, или 0, если поток был пустым (в случае, если коллекция была пустой)
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