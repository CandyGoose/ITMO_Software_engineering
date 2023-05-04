package Server.interfaces;

import java.sql.SQLException;


/**
 * Функциональный интерфейс, представляющий операцию над объектом типа T, выполняющую операции с базой данных.
 * В отличие от стандартного интерфейса Consumer, может бросать SQLException.
 * @param <T> тип объекта, который будет обрабатываться операцией
 */
@FunctionalInterface
public interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}
