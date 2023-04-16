package Server.util;

import Common.data.Organization;

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
     * Создает пустую коллекцию организаций и задает ее дату инициализации
     */
    public CollectionManager() {
        organizationCollection = new LinkedList<>();
        creationDate = ZonedDateTime.now();
    }

    /**
     * Коллекция организаций
     */
    private LinkedList<Organization> organizationCollection;

    /**
     * Возвращает коллекцию организаций
     * @return коллекция организаций
     */
    public List<Organization> getCollection() {
        return organizationCollection;
    }

    /**
     * Дата инициализации коллекции
     */
    private final ZonedDateTime creationDate;

    /**
     * Возвращает дату инициализации коллекции
     * @return дата инициализации коллекции
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает коллекцию организаций
     * @param organizationCollection коллекция организаций
     */
    public void setOrganizationCollection(LinkedList<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    /**
     * Метод генерирует новый идентификатор для добавления организации в коллекцию.
     * @return новый идентификатор организации
     */
    public Long generateNextId() {
        if (organizationCollection.isEmpty()) return 1L;
        List<Organization> sortedList = new ArrayList<>(organizationCollection);
        sortedList.sort(Comparator.comparing(Organization::getId));
        return sortedList.get(sortedList.size() - 1).getId() + 1;
    }

    /**
     * Возвращает первый объект из коллекции.
     * Если коллекция пуста, возвращается null.
     * @return первый объект из коллекции или null, если коллекция пуста.
     */
    public Organization getFirst() {
        if (organizationCollection.isEmpty()) return null;
        return organizationCollection.getFirst();
    }

    /**
     * Метод удаляет первый элемент из коллекции организаций.
     */
    public void removeFirst(){ organizationCollection.poll(); } // удаляет первый элемент (head) из коллекции organizationCollection

    /**
     * Возвращает организацию по заданному id
     * @param id id организации
     * @return организация с заданным id или null, если она не найдена
     */
    public Organization getById(Long id) {
        try {
            return (Organization) organizationCollection.stream()
                    .filter(s -> s.getId().equals(id)) // Фильтруются объекты, у которых id равен переданному в метод
                    .toArray()[0]; // Результат фильтрации преобразуется в массив, из которого выбирается первый элемент и возвращается в качестве результата метода
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Метод удаляет организацию из коллекции по заданному идентификатору.
     * @param id идентификатор организации для удаления
     */
    public void removeById(Long id) {
        organizationCollection.removeIf(p -> p.getId().equals(id));
    }

    /**
     * Добавляет организацию в коллекцию
     * @param organization организация для добавления
     */
    public void addToCollection(Organization organization){
        organizationCollection.add(organization);
        organizationCollection.sort(Comparator.comparing(Organization::getName, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Метод очищает коллекцию организаций.
     */
    public void clearCollection() {
        organizationCollection.clear();
    }

    /**
     * Метод перемешивает элементы коллекции организаций в случайном порядке.
     */
    public void shuffleCollection(){
        Collections.shuffle(organizationCollection);
    }

    /**
     * Сортирует коллекцию по умолчанию (по возрастанию id)
     */
    public void sortCollection(){
        Collections.sort(organizationCollection);
    }

    /**
     * Метод возвращает множество значений количества сотрудников в коллекции организаций.
     * @return множество значений количества сотрудников
     */
    public Set<Long> getEmployeesCount() {
        return organizationCollection.stream() // создание потока данных
                .map(Organization::getEmployeesCount) // получение значения поля EmployeesCount для каждого объекта типа Organization в коллекции
                .collect(Collectors.toSet()); // собирает все уникальные значения в Set и возвращает его
    }

    /**
     * Метод возвращает список значений годового оборота в коллекции организаций.
     * @return список значений годового оборота
     */
    public List<Float> getListAnnualTurnover(){
        return organizationCollection.stream() // создание потока данных
                .map(Organization::getAnnualTurnover) // получение значения поля AnnualTurnover для каждого объекта типа Organization в коллекции
                .sorted(Collections.reverseOrder()) // сортировка в обратном порядке
                .collect(Collectors.toList()); // собирает все значения в List
    }

    /**
     * Метод возвращает информацию о коллекции организаций.
     * @return строка с типом коллекции, датой инициализации и количеством элементов
     */
    public String getInfo() {
        return "Тип коллекции: " + organizationCollection.getClass()
                + "\nДата инициализации: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss"))
                + "\nКоличество элементов: " + organizationCollection.size();
    }

    /**
     * Возвращает все объекты коллекции.
     * Если коллекция пуста, возвращается строка "Коллекция пуста".
     * @return все объекты коллекции.
     */
    public String showCollection() {
        if (organizationCollection.isEmpty()) return "Коллекция пуста";
        return organizationCollection.stream()
                .map(organization -> organization.toString() + "\n")
                .collect(Collectors.joining());
    }

    /**
     * Возвращает все объекты коллекции в обратном порядке.
     * Если коллекция пуста, возвращается строка "Коллекция пуста".
     * @return все объекты коллекции в обратном порядке.
     */
    public String printDescending() {
        if (organizationCollection.isEmpty()) return "Коллекция пуста";
        List<Organization> reversedCollection = new ArrayList<>(organizationCollection);
        Collections.reverse(reversedCollection);
        return reversedCollection.stream()
                .map(organization -> organization.toString() + "\n")
                .collect(Collectors.joining());
    }
}
