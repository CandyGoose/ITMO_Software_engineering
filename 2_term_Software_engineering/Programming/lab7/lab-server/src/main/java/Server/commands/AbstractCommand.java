package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Server.db.DBManager;
import Server.util.CollectionManager;

/**
 * Абстрактный класс для команд
 */
public abstract class AbstractCommand {

    /**
     * Название команды
     */
    private final String name;

    /**
     * Описание команды
     */
    private final String description;

    /**
     * Количество аргументов у команды
     */
    private final int amountOfArgs;

    /**
     * Менеджер коллекции
     */
    protected CollectionManager collectionManager;

    /**
     * Менеджер БД
     */
    protected DBManager dbManager;

    /**
     * Конструктор абстрактного класса команды
     * @param name название команды
     * @param description описание команды
     * @param amountOfArgs количество аргументов у команды
     */
    public AbstractCommand(String name, String description, int amountOfArgs) {
        this.name = name;
        this.description = description;
        this.amountOfArgs = amountOfArgs;
    }

    /**
     * Конструктор абстрактного класса команды
     * @param name название команды
     * @param description описание команды
     * @param amountOfArgs количество аргументов у команды
     * @param collectionManager менеджер коллекции
     * @param dbManager менеджер БД
     */
    public AbstractCommand(String name, String description, int amountOfArgs, CollectionManager collectionManager, DBManager dbManager) {
        this.name = name;
        this.description = description;
        this.amountOfArgs = amountOfArgs;
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    /**
     * Метод для выполнения команды
     * @param request запрос пользователя
     * @return ответ сервера
     */
    public abstract Response execute(Request request);

    /**
     * Получает название команды
     * @return название команды
     */
    public String getName() {
        return name;
    }

    /**
     * Получает менеджер коллекции
     * @return менеджер коллекции
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Получает менеджер БД
     * @return менеджер БД
     */
    public DBManager getDbManager() {
        return dbManager;
    }

    /**
     * Переопределенный метод toString() для класса AbstractCommand
     * @return строковое представление объекта AbstractCommand
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
