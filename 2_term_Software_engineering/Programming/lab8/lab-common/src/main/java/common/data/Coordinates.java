package common.data;

import java.io.Serializable;
import java.util.Objects;

import common.exceptions.InvalidFieldException;

/**
 * Класс, представляющий координаты.
 */
public class Coordinates implements Serializable {
    /**
     * Валидатор
     */
    public static final Validator VALIDATOR = new Validator();

    /**
     * X не может быть null
     */
    private Float x;

    /**
     * Y не может быть null
     */
    private Float y;

    /**
     * Создает новый объект координат.
     *
     * @param x координата X
     * @param y координата Y
     * @throws InvalidFieldException если значения координат некорректны
     */
    public Coordinates(Float x, Float y) throws InvalidFieldException {
        this.x = x;
        this.y = y;
        VALIDATOR.validate(this);
    }

    /**
     * Создает новый объект координат со значениями по умолчанию.
     */
    public Coordinates() {
        x = null;
        y = null;
    }

    /**
     * Возвращает значение координаты X.
     *
     * @return значение координаты X
     */
    public Float getX() {
        return x;
    }

    /**
     * Возвращает значение координаты Y.
     *
     * @return значение координаты Y
     */
    public Float getY() {
        return y;
    }

    /**
     * Возвращает строковое представление объекта координат.
     *
     * @return строковое представление координат
     */
    public String toString() {
        return "Coordinates {\n\tx=" + x + ",\n\ty=" + y + ",\n}";
    }

    /**
     * Проверяет, является ли указанный объект равным данному объекту координат.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, false в противном случае
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Coordinates)) {
            return false;
        }

        Coordinates t = (Coordinates) obj;

        return (Objects.equals(x, t.x))
                && y.equals(t.y);
    }

    /**
     * Возвращает хэш-код объекта координат.
     *
     * @return хэш-код объекта
     */
    public int hashCode() {
        final int k = 31;
        final int a = 7;
        int hash = a;
        hash = k * hash + (x == null ? 0 : x.hashCode());
        hash = k * hash + (y == null ? 0 : y.hashCode());
        return hash;
    }

    /**
     * Валидатор для объекта координат.
     */
    public static class Validator implements AbstractValidator<Coordinates> {
        /**
         * Проверяет корректность значений координат.
         *
         * @param coordinates объект координат для проверки
         * @throws InvalidFieldException если значения координат некорректны
         */
        public void validate(Coordinates coordinates) throws InvalidFieldException {
            validateX(coordinates.x);
            validateY(coordinates.y);
        }

        /**
         * Проверяет корректность значения координаты X.
         *
         * @param x значение координаты X
         * @throws InvalidFieldException если значение координаты X некорректно
         */
        public void validateX(Float x) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(x, "Поле x не может быть пустым.", "coordinatesXNotNull");
        }

        /**
         * Проверяет корректность значения координаты Y.
         *
         * @param y значение координаты Y
         * @throws InvalidFieldException если значение координаты Y некорректно
         */
        public void validateY(Float y) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(y, "Поле y не может быть пустым.", "coordinatesYNotNull");
        }
    };
}


