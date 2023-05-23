package client.util;

/**
 * Класс NumberStringConverter представляет собой реализацию интерфейса StringConverter
 * для преобразования строковых значений в числовые объекты.
 * Он обертывает другой объект StringConverter и обрабатывает исключение NumberFormatException,
 * возвращая значение null в случае ошибки преобразования.
 *
 * @param <T> Тип числового объекта.
 */
public class NumberStringConverter<T extends Number> implements StringConverter<T> {
    private StringConverter<T> baseConverter;

    /**
     * Конструктор класса NumberStringConverter.
     *
     * @param baseConverter Объект StringConverter, который будет использоваться для преобразования строк в числовые значения.
     */
    public NumberStringConverter(StringConverter<T> baseConverter) {
        this.baseConverter = baseConverter;
    }

    /**
     * Метод convert преобразует строковое значение в числовой объект.
     * В случае ошибки преобразования, метод возвращает значение null.
     *
     * @param s Строковое значение для преобразования.
     * @return Числовой объект, полученный из строки, или null в случае ошибки преобразования.
     */
    @Override
    public T convert(String s) {
        try {
            return baseConverter.convert(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
