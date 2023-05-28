package common.data;

import common.exceptions.InvalidFieldException;
import common.network.BasicUserIO;

/**
 * Утилитарный класс для создания объектов адреса.
 */
public final class AddressMaker {
    /**
     * Новый объект адреса
     */
    private AddressMaker() {
    }


    /**
     * Создает объект адреса на основе пользовательского ввода.
     *
     * @param io объект для ввода-вывода пользовательских данных
     * @return объект адреса
     * @throws InvalidFieldException если введенные данные некорректны
     */
    public static Address parseAddress(BasicUserIO io) throws InvalidFieldException {
        return new Address(
            BasicParsers.Repeater.doGet(AddressMaker::parseStreet, io)
        );
    }

    /**
     * Парсит улицу на основе пользовательского ввода.
     *
     * @param io объект для ввода-вывода пользовательских данных
     * @return улица
     */
    public static String parseStreet(BasicUserIO io) {
        String street = BasicParsers.parseString(io, "Street: ");
        return street;
    }
}
