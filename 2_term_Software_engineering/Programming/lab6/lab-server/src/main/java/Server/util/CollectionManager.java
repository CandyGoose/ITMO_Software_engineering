package Server.util;

import Common.data.Organization;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    public CollectionManager() {
        organizationCollection = new LinkedList<>();
        creationDate = ZonedDateTime.now();
    }
    private LinkedList<Organization> organizationCollection;

    public List<Organization> getCollection() {
        return organizationCollection;
    }
    private final ZonedDateTime creationDate;


    public Organization getFirst() {
        if (organizationCollection.isEmpty()) return null;
        return organizationCollection.getFirst();
    }

    public void removeFirst(){ organizationCollection.poll(); } // удаляет первый элемент (head) из коллекции organizationCollection



    public Organization getById(Long id) {
        try {
            return (Organization) organizationCollection.stream()
                    .filter(s -> s.getId().equals(id))
                    .toArray()[0];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    public void removeById(Long id) {
        organizationCollection.removeIf(p -> p.getId().equals(id));
    }


    public void addToCollection(Organization organization){
        organizationCollection.add(organization);
    }


    public void clearCollection() {
        organizationCollection.clear();
    }


    public Long generateNextId() {
        if (organizationCollection.isEmpty()) return 1L;
        return organizationCollection.getLast().getId() + 1;
    }

    public void setOrganizationCollection(LinkedList<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }
    public void shuffleCollection(){
        Collections.shuffle(organizationCollection);
    }
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


    public String getInfo() {
        return "Тип коллекции: " + organizationCollection.getClass()
                + "\nДата инициализации: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss"))
                + "\nКоличество элементов: " + organizationCollection.size();
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public String showCollection() {
        if (organizationCollection.isEmpty()) return "Коллекция пуста";
        return organizationCollection.stream()
                .map(organization -> organization.toString() + "\n").collect(Collectors.joining());
    }

    public String printDescending() {
        if (organizationCollection.isEmpty()) return "Коллекция пуста";
        List<Organization> reversedCollection = new ArrayList<>(organizationCollection);
        Collections.reverse(reversedCollection);
        return reversedCollection.stream()
                .map(organization -> organization.toString() + "\n")
                .collect(Collectors.joining());
    }
}
