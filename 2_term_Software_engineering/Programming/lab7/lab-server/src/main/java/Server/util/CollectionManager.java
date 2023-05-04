package Server.util;

import Common.data.Organization;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    private ConcurrentLinkedDeque<Organization> organizationCollection = new ConcurrentLinkedDeque<>();

    /**
     * Дата инициализации коллекции
     */
    private LocalDateTime lastInitTime;

    /**
     * Сортирует коллекцию организаций по умолчанию.
     * @param collection коллекция организаций
     * @return отсортированная коллекция организаций
     */
    public ConcurrentLinkedDeque<Organization> sort(ConcurrentLinkedDeque<Organization> collection) {
        return collection.stream().sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque<Organization>::new));
    }

    /**
     * Возвращает коллекцию организаций
     * @return коллекция организаций
     */
    public ConcurrentLinkedDeque<Organization> getCollection() {
        return organizationCollection;
    }

    /**
     * Возвращает дату инициализации коллекции
     * @return дата инициализации коллекции
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Метод удаляет организацию из коллекции по заданному идентификатору.
     * @param id идентификатор организации для удаления
     */
    public void removeById(Long id) {
            organizationCollection.removeIf(p -> p.getId().equals(id));
    }

    /**
     * Возвращает ID первой организации в коллекции.
     * @return ID первой организации или 0, если коллекция пуста
     */
    public long getFirstId(){
        Optional<Long> minId = organizationCollection.stream()
                .map(Organization::getId)
                .min(Comparator.naturalOrder());
        return minId.orElse(0L);
    }

    /**
     * Добавляет организацию в коллекцию, сортирует ее и устанавливает время последней инициализации.
     * @param organization добавляемая организация
     */
    public void addToCollection(Organization organization) {
            organizationCollection.add(organization);
            organizationCollection = new ConcurrentLinkedDeque<>(sort(organizationCollection));
            setLastInitTime(LocalDateTime.now());
    }

    /**
     * Устанавливает время последней инициализации.
     * @param lastInitTime время последней инициализации
     */
    public void setLastInitTime(LocalDateTime lastInitTime) {
            this.lastInitTime = lastInitTime;
    }

    /**
     * Очищает коллекцию организаций.
     */
    public void clearCollection() {
            organizationCollection.clear();
    }

    /**
     * Устанавливает коллекцию организаций.
     * @param organizationCollection коллекция организаций
     */
    public void setOrganizationCollection(ConcurrentLinkedDeque<Organization> organizationCollection) {
            this.organizationCollection = organizationCollection;
    }

    /**
     * Возвращает коллекцию организаций, перемешанную в случайном порядке.
     * @return перемешанная коллекция организаций
     */
    public ConcurrentLinkedDeque<Organization> shuffleCollection() {
        List<Organization> list = new ArrayList<>(organizationCollection);
        Collections.shuffle(list);
        ConcurrentLinkedDeque<Organization> shuffledCollection = new ConcurrentLinkedDeque<>();
        shuffledCollection.addAll(list);
        return shuffledCollection;
    }

    /**
     * Возвращает коллекцию организаций, отсортированную по умолчанию.
     * @return отсортированная коллекция организаций
     */
    public ConcurrentLinkedDeque<Organization> sortCollection(){
        List<Organization> list = new ArrayList<>(organizationCollection);
        Collections.sort(list);
        ConcurrentLinkedDeque<Organization> sortedCollection = new ConcurrentLinkedDeque<>();
        sortedCollection.addAll(list);
        return sortedCollection;
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
     * Возвращает коллекцию организаций, отсортированную по убыванию по умолчанию.
     * @return отсортированная по убыванию коллекция организаций
     */
    public ConcurrentLinkedDeque<Organization> getDescending() {
            return organizationCollection.stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toCollection(ConcurrentLinkedDeque<Organization>::new));
    }

    /**
     * Возвращает коллекцию организаций с указанными идентификаторами.
     * @param ids список идентификаторов организаций
     * @return коллекция организаций с указанными идентификаторами
     */
    public ConcurrentLinkedDeque<Organization> getUsersElements(List<Long> ids) {
            return organizationCollection.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }

    /**
     * Возвращает коллекцию организаций, которые не содержатся в указанном списке идентификаторов.
     * @param ids список идентификаторов организаций
     * @return коллекция организаций, которые не содержатся в указанном списке идентификаторов
     */
    public ConcurrentLinkedDeque<Organization> getAlienElements(List<Long> ids) {
            return organizationCollection.stream().filter(p -> !ids.contains(p.getId())).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }

    /**
     * Возвращает информацию о коллекции организаций.
     * @return строка с информацией о коллекции
     */
    public String getInfo() {
        return "Тип коллекции: " + ConcurrentLinkedDeque.class + ", тип элементов: "
                + Organization.class
                + (getLastInitTime() == null ? "" : (", дата инициализации: "
                + getLastInitTime().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss"))))
                + ", количество элементов: " + organizationCollection.size();

    }

    /**
     * Возвращает строковое представление коллекции организаций.
     * @return строковое представление коллекции
     */
    @Override
    public String toString() {
        if (organizationCollection.isEmpty()) return "Коллекция пуста.";
        StringBuilder info = new StringBuilder();
        for (Organization organization : organizationCollection) {
            info.append(organization);
            if (organization != organizationCollection.getLast()) info.append("\n\n");
        }
        return info.toString();
    }
}
