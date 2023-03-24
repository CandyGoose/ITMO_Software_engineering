package server.utility;

import common.data.Organization;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс-менеджер коллекции организаций.
 * Содержит методы для работы с коллекцией.
 * Возвращает информацию о коллекции.
 */
public class CollectionManager {

    /**
     * Коллекция организаций.
     */
    private LinkedList<Organization> organizationCollection;

    /**
     * Дата инициализации коллекции.
     */
    private ZonedDateTime lastInitTime;

    /**
     * Менеджер файлов.
     */
    private final CollectionFileManager collectionFileManager;

    /**
     * Конструктор класса CollectionManager.
     * @param collectionFileManager объект класса CollectionFileManager, управляющий файлами коллекции.
     */
    public CollectionManager(CollectionFileManager collectionFileManager) {
        this.lastInitTime = null;
        this.collectionFileManager = collectionFileManager;
        loadCollection();
    }

    /**
     * Возвращает дату инициализации коллекции.
     * @return дата инициализации коллекции.
     */
    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Возвращает коллекцию организаций.
     * @return коллекция организаций.
     */
    public LinkedList<Organization> getCollection() {
        return organizationCollection;
    }

    /**
     * Показывает содержимое коллекции.
     * @return Содержимое коллекции или соответствующая строка, если коллекция пуста.
     */
    public String showCollection() {
        if (organizationCollection.isEmpty()) return "Коллекция пуста";
        return organizationCollection.stream()
                .map(organization -> organization.toString() + "\n").collect(Collectors.joining());
    }

    /**
     * Возвращает тип коллекции.
     * @return тип коллекции.
     */
    public String collectionType() {
        return organizationCollection.getClass().getName();
    }

    /**
     * Возвращает количество элементов в коллекции.
     * @return количество элементов в коллекции.
     */
    public int collectionSize() {
        return organizationCollection.size();
    }

    /**
     * Возвращает организацию по заданному id.
     * @param id id организации.
     * @return организация с заданным id или null, если она не найдена.
     */
    public Organization getById(Long id) {
        return organizationCollection.stream().filter(marine -> marine.getId().equals(id)).findFirst().orElse(null);
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
     * Удаляет организацию из коллекции по заданному идентификатору.
     * @param id идентификатор организации для удаления
     */
    public void removeByIdFromCollection(Long id){
        organizationCollection.stream() // получение последовательного потока элементов из коллекции organizationCollection
                .filter(organization -> organization.getId().equals(id)) // выбираем только те объекты Organization, у которых id совпадает с переданным значением id
                .findFirst() // возвращает первый элемент из потока, удовлетворяющий условию, представленному в фильтре, если такого элемента нет, метод возвращает пустое значение.
                .ifPresent(this::removeFromCollection); // проверяет, присутствует ли значение в Optional (возвращаемое значение метода findFirst()) и, если это так, то указывает на ссылку на метод removeFromCollection() текущего объекта для удаления
    }

    /**
     * Очищает коллекцию организаций.
     */
    public void clearCollection(){
        organizationCollection.clear();
    }

    /**
     * Перемешивает элементы коллекции организаций в случайном порядке.
     */
    public void shuffleCollection(){
        Collections.shuffle(organizationCollection);
    }

    /**
     * Сортирует коллекцию по умолчанию (по возрастанию id)
     */
    public void sortCollection(){ Collections.sort(organizationCollection); }

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
    public long generateNextId(){
        if(organizationCollection.isEmpty()) return 1;
        else return organizationCollection.stream() // создает поток объектов класса Organization,
                .mapToLong(Organization::getId) // преобразует каждый объект Organization в его id и создает поток из этих значений
                .filter(organization -> organization >= 0) // фильтрует id, чтобы получить только положительные значения (ID организации не может быть отрицательным)
                .max().orElse(0) + 1; // находит максимальное значение id из потока и возвращает его, или 0, если поток был пустым (в случае, если коллекция была пустой)
    }

    /**
     * Метод удаляет первый элемент из коллекции организаций.
     */
    public void removeFirstInCollection(){ organizationCollection.poll(); } // удаляет первый элемент (head) из коллекции organizationCollection

    /**
     * Сохраняет в файл.
     */
    public void saveCollection() {
        collectionFileManager.writeCollection(organizationCollection);
    }

    /**
     * Загружает коллекцию из файла.
     */
    private void loadCollection() {
        organizationCollection = collectionFileManager.readCollection();
        lastInitTime = ZonedDateTime.now();
    }
}
