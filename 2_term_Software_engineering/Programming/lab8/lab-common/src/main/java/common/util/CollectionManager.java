package common.util;
import common.data.Organization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Менеджер коллекции организаций.
 */
@Component
public interface CollectionManager {

    /**
     * Возвращает текущую коллекцию организаций.
     *
     * @return текущая коллекция организаций.
     */
    List<Organization> getCollection();

    /**
     * Возвращает организацию по ее идентификатору.
     *
     * @param id идентификатор организации.
     * @return организация с указанным идентификатором или null, если организация не найдена.
     */
    Organization getItemById(long id);

    /**
     * Добавляет новую организацию в коллекцию.
     *
     * @param organization организация для добавления.
     * @return идентификатор добавленной организации.
     */
    long add(Organization organization);

    /**
     * Обновляет информацию об организации в коллекции.
     *
     * @param organization организация для обновления.
     * @return true, если обновление прошло успешно, иначе false.
     */
    boolean update(Organization organization);

    /**
     * Возвращает список уникальных значений количества сотрудников в коллекции.
     *
     * @return список уникальных значений количества сотрудников.
     */
    List<Long> getUniqueEmployeesCount();

    /**
     * Возвращает список годовых оборотов организаций в порядке убывания.
     *
     * @return список годовых оборотов организаций.
     */
    List<Float> getDescendingAnnualTurnover();

    /**
     * Удаляет организацию из коллекции по ее идентификатору.
     *
     * @param id идентификатор организации для удаления.
     */
    void remove(long id);

    /**
     * Удаляет первую организацию, удовлетворяющую заданному условию.
     *
     * @param predicate условие для удаления организации.
     * @return количество удаленных организаций (0 или 1).
     */
    int removeFirst(Predicate<? super Organization> predicate);

    /**
     * Удаляет все организации, удовлетворяющие заданному условию.
     *
     * @param predicate условие для удаления организации.
     * @return количество удаленных организаций.
     */
    int removeIf(Predicate<? super Organization> predicate);

    /**
     * Возвращает коллекцию организаций в порядке убывания.
     *
     * @return коллекция организаций в порядке убывания.
     */
    List<Organization> getDescendingCollection();

    /**
     * Возвращает отсортированную коллекцию организаций.
     *
     * @return отсортированная коллекцию организаций.
     */
    List<Organization> getSortedCollection();
}
