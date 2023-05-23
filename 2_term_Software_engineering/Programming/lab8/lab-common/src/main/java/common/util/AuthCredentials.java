package common.util;

import java.io.Serializable;

/**
 * Учетные данные аутентификации.
 */
public class AuthCredentials implements Serializable {
    /**
     * Логин
     */
    private final String login;

    /**
     * Пароль
     */
    private final String password;

    /**
     * Конструктор для создания учетных данных аутентификации.
     *
     * @param login логин пользователя.
     * @param password пароль пользователя.
     */
    public AuthCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Возвращает логин пользователя.
     *
     * @return логин пользователя.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя.
     */
    public String getPassword() {
        return password;
    }
}
