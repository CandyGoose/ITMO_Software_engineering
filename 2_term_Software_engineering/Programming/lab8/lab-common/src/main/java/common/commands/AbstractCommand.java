package common.commands;

import common.exceptions.CommandArgumentException;
import common.exceptions.InvalidRequestException;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.network.BasicUserIO;

/**
 * Абстрактный класс AbstractCommand, представляющий общую структуру для команд.
 */
public abstract class AbstractCommand {
    /**
     * Имя команды
     */
    private final String name;

    /**
     * Требование аутентификации
     */
    private final boolean requireAuth;

    /**
     * Конструктор класса.
     *
     * @param name имя команды
     */
    public AbstractCommand(String name) {
        this.name = name;
        requireAuth = true;
    }

    /**
     * Конструктор класса.
     *
     * @param name имя команды
     * @param requireAuth требуется ли аутентификация для выполнения команды
     */
    public AbstractCommand(String name, boolean requireAuth) {
        this.name = name;
        this.requireAuth = requireAuth;
    }

    /**
     * Получить имя команды.
     *
     * @return имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Проверить, требуется ли аутентификация для выполнения команды.
     *
     * @return true, если требуется аутентификация, иначе false
     */
    public boolean requiresAuth() {
        return requireAuth;
    }

    /**
     * Упаковка аргументов команды в тело запроса.
     *
     * @param args аргументы команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return тело запроса
     * @throws CommandArgumentException при некорректных аргументах команды
     */
    public RequestBody packageBody(String[] args, BasicUserIO io) throws CommandArgumentException {
        if (args.length != 0) {
            throw new CommandArgumentException(this.getName(), args.length);
        }
        return new RequestBody(args);
    }

    /**
     * Выполнение команды на основе полученного запроса.
     *
     * @param request запрос
     * @return ответ на запрос
     * @throws InvalidRequestException при некорректном запросе
     */
    public abstract Response execute(Request request) throws InvalidRequestException;

    /**
     * Получение информации о использовании команды.
     *
     * @return информация о использовании команды
     */
    public abstract String getUsage();
}
