package common.util;

import org.springframework.stereotype.Component;

/**
 * Интерфейс для управления пользователями.
 */
@Component
public interface UserManager {
    /**
     * Аутентификация пользователя на основе переданных учетных данных.
     *
     * @param auth учетные данные пользователя.
     * @return идентификатор пользователя, если аутентификация успешна, иначе null.
     */
    Long authenticate(AuthCredentials auth);

    /**
     * Регистрация нового пользователя с переданными учетными данными.
     *
     * @param auth учетные данные пользователя.
     * @return идентификатор созданного пользователя.
     */
    Long register(AuthCredentials auth);
}
