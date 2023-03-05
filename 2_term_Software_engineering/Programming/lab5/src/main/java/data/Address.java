package data;



/**
 * Адрес это улица и почтовый индекс
 */
public class Address {
    private final String street;

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