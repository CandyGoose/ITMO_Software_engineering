package Server.util;

import Common.data.Organization;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import java.util.stream.Collectors;

/**
 * This class realizes all operations with the collection
 */
public class CollectionManager {
    private ConcurrentLinkedDeque<Organization> organizationCollection = new ConcurrentLinkedDeque<>();
    private LocalDateTime lastInitTime;

    public ConcurrentLinkedDeque<Organization> sort(ConcurrentLinkedDeque<Organization> collection) {
        return collection.stream().sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque<Organization>::new));
    }

    public ConcurrentLinkedDeque<Organization> getCollection() {
        return organizationCollection;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public void removeById(Long id) {
            organizationCollection.removeIf(p -> p.getId().equals(id));
    }

    public long getFirstId(){
        Optional<Long> minId = organizationCollection.stream()
                .map(Organization::getId)
                .min(Comparator.naturalOrder());
        return minId.orElse(0L);
    }



    public void addToCollection(Organization organization) {
            organizationCollection.add(organization);
            organizationCollection = new ConcurrentLinkedDeque<>(sort(organizationCollection));
            setLastInitTime(LocalDateTime.now());
    }


    public void setLastInitTime(LocalDateTime lastInitTime) {
            this.lastInitTime = lastInitTime;
    }

    public void clearCollection() {
            organizationCollection.clear();
    }

    public void setOrganizationCollection(ConcurrentLinkedDeque<Organization> organizationCollection) {
            this.organizationCollection = organizationCollection;
    }

    public Organization removeFirst() {
            return organizationCollection.poll();
    }

    public ConcurrentLinkedDeque<Organization> shuffleCollection() {
        List<Organization> list = new ArrayList<>(organizationCollection);
        Collections.shuffle(list);
        ConcurrentLinkedDeque<Organization> shuffledCollection = new ConcurrentLinkedDeque<>();
        shuffledCollection.addAll(list);
        return shuffledCollection;
    }

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

    public ConcurrentLinkedDeque<Organization> getDescending() {
            return organizationCollection.stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toCollection(ConcurrentLinkedDeque<Organization>::new));
    }

    public ConcurrentLinkedDeque<Organization> getUsersElements(List<Long> ids) {
            return organizationCollection.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }



    public ConcurrentLinkedDeque<Organization> getAlienElements(List<Long> ids) {
            return organizationCollection.stream().filter(p -> !ids.contains(p.getId())).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }

    public String getInfo() {
            return "Тип коллекции: " + organizationCollection.getClass()
                    + "\nДата инициализации: " + getLastInitTime().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss"))
                    + "\nКоличество элементов: " + organizationCollection.size();
    }

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
