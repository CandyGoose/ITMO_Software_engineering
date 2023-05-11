package Server.commands;

import Common.util.Request;
import Common.util.Response;

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
     * Получает описание команды
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Получает количество аргументов команды
     * @return количество аргументов команды
     */
    public int getAmountOfArgs() { return amountOfArgs; }

    /**
     * Переопределенный метод toString() для класса AbstractCommand
     * @return строковое представление объекта AbstractCommand
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
