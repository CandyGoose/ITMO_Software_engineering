package common.network;

import java.io.Serializable;

import common.util.AuthCredentials;

/**
 * Класс, представляющий запрос.
 */
public class Request implements Serializable {
    /**
     * Имя команды
     */
    private String commandName;

    /**
     * Учетные данные
     */
    private AuthCredentials auth;

    /**
     * Тело запроса
     */
    private RequestBody body;

    /**
     * Создает новый объект запроса.
     *
     * @param commandName имя команды.
     * @param body тело запроса.
     * @param auth учетные данные аутентификации.
     */
    public Request(String commandName, RequestBody body, AuthCredentials auth) {
        this.commandName = commandName;
        this.body = body;
        this.auth = auth;
    }

    /**
     * Возвращает имя команды.
     *
     * @return имя команды.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Возвращает тело запроса.
     *
     * @return тело запроса.
     */
    public RequestBody getBody() {
        return body;
    }

    /**
     * Возвращает учетные данные аутентификации.
     *
     * @return учетные данные аутентификации.
     */
    public AuthCredentials getAuth() {
        return auth;
    }
}
