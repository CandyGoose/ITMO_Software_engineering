package common.data;

import common.exceptions.InvalidFieldException;

import java.io.Serializable;

/**
 * Класс, представляющий адрес.
 */
public class Address implements Serializable {
    /**
     * Валидатор для проверки корректности адреса.
     */
    public static final Validator VALIDATOR = new Validator();

    /**
     * Улица может быть null
     */
    private String street;

    /**
     * Создает новый объект адреса с указанной улицей.
     *
     * @param street улица
     * @throws InvalidFieldException если переданное значение улицы некорректно
     */
    public Address(String street) throws InvalidFieldException {
        this.street = street;
    }

    /**
     * Создает новый объект адреса с пустой улицей.
     */
    public Address() {
        street = "";
    }

    /**
     * Возвращает улицу.
     *
     * @return улица
     */
    public String getStreet() {
        return street;
    }

    /**
     * Возвращает строковое представление адреса.
     *
     * @return строковое представление адреса
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Address {");
        sb.append("\n\tstreet=");
        sb.append(street);
        sb.append("\n}");

        return sb.toString();
    }

    /**
     * Вычисляет хэш-код объекта адреса.
     *
     * @return хэш-код объекта адреса
     */
    public int hashCode() {
        final int k = 31;
        final int a = 7;
        int hash = a;
        if (street != null) {
            hash = k * hash + street.hashCode();
        }
        return hash;
    }

    /**
     * Внутренний класс, представляющий валидатор для адреса.
     */
    public static class Validator implements AbstractValidator<Address> {
        /**
         * Проверяет корректность полей объекта адреса.
         *
         * @param address объект адреса
         * @throws InvalidFieldException если найдено некорректное поле
         */
        public void validate(Address address) throws InvalidFieldException {
            validateStreet(address.street);
        }

        /**
         * Проверяет корректность улицы.
         *
         * @param street улица
         * @throws InvalidFieldException если улица некорректна
         */
        public void validateStreet(String street) throws InvalidFieldException {
        }
    }
}
