package Server.interfaces;

import Common.exception.DatabaseException;

import java.sql.Connection;

/**
 * Интерфейс для работы с базой данных.
 */
public interface DBConnectable {
    /**
     * Обрабатывает запрос, передавая тело запроса в качестве аргумента функции, которая принимает соединение с базой данных.
     * @param queryBody тело запроса, принимающее соединение с базой данных.
     * @throws DatabaseException если произошла ошибка при обращении к базе данных.
     */
    void handleQuery(SQLConsumer<Connection> queryBody) throws DatabaseException;

    /**
     * Обрабатывает запрос, передавая тело запроса в качестве аргумента функции, которая принимает соединение с базой данных
     * и возвращает результат запроса.
     * @param queryBody тело запроса, принимающее соединение с базой данных и возвращающее результат запроса.
     * @param <T> тип результата запроса.
     * @return результат запроса.
     * @throws DatabaseException если произошла ошибка при обращении к базе данных.
     */
    <T> T handleQuery(SQLFunction<Connection, T> queryBody) throws DatabaseException;
}
