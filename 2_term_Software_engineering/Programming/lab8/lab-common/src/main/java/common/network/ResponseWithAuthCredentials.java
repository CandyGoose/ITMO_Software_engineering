package common.network;

import common.util.AuthCredentials;

/**
 * Класс, представляющий ответ на запрос с информацией об аутентификационных учетных данных.
 */
public class ResponseWithAuthCredentials extends Response {
    /**
     * Учетные данные
     */
    private final AuthCredentials auth;

    /**
     * Создает новый объект ответа с аутентификационными учетными данными и сообщением.
     *
     * @param auth аутентификационные учетные данные.
     * @param message сообщение ответа.
     */
    public ResponseWithAuthCredentials(AuthCredentials auth, String message) {
        super(message);
        this.auth = auth;
    }

    /**
     * Возвращает аутентификационные учетные данные.
     *
     * @return аутентификационные учетные данные.
     */
    public AuthCredentials getAuthCredentials() {
        return auth;
    }
}
