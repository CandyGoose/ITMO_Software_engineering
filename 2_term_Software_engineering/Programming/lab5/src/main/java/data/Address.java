package data;

/**
 * Адрес содержит улицу
 */
public class Address {

    /**
     * Улица
     * Поле может быть null
     */
    private final String street;

    /**
     * Конструктор, задающий параметры адреса
     * @param street - улица
     */
    public Address(String street){
        this.street = street;
    }

    /**
     * Возвращает название улицы
     *
     * @return street
     */
    public String getStreet() {
        return street;
    }
}