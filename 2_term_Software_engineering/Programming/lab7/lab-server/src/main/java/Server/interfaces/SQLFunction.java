package Server.interfaces;

import Common.exception.DatabaseException;

import java.sql.SQLException;

/**
 * Функциональный интерфейс, представляющий SQL функцию, которая принимает объект типа T в качестве аргумента
 * и возвращает объект типа R. Может генерировать SQLException и DatabaseException.
 * @param <T> тип входного параметра
 * @param <R> тип возвращаемого значения
 */
@FunctionalInterface
public interface SQLFunction<T, R> {
    R apply(T t) throws SQLException, DatabaseException;
}
