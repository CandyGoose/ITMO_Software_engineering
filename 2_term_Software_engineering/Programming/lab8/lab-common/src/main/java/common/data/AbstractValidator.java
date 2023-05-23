package common.data;

import common.exceptions.InvalidFieldException;

/**
 * Интерфейс, определяющий методы для проверки корректности полей объекта.
 *
 * @param <T> тип объекта, для которого выполняется проверка
 */
public interface AbstractValidator<T> {

    /**
     * Проверяет корректность полей объекта.
     *
     * @param toValidate объект для проверки
     * @throws InvalidFieldException если найдено некорректное поле
     */
    void validate(T toValidate) throws InvalidFieldException;

    /**
     * Проверяет, что поле не является пустым (null).
     *
     * @param field поле для проверки
     * @param message сообщение об ошибке
     * @param localeKey ключ локализации сообщения об ошибке
     * @throws InvalidFieldException если поле является пустым (null)
     */
    static void ensureNotNull(Object field, String message, String localeKey) throws InvalidFieldException {
        if (field == null) {
            throw new InvalidFieldException(message, localeKey);
        }
    }
}
