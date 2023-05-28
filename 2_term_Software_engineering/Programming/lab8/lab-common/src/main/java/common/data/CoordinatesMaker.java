package common.data;

import common.exceptions.InvalidFieldException;
import common.network.BasicUserIO;

/**
 * Вспомогательный класс для создания объектов Coordinates.
 */
public final class CoordinatesMaker {
    /**
     * Создает объект координат
     */
    private CoordinatesMaker() {
    }

    /**
     * Создает объект Coordinates на основе ввода пользователя.
     *
     * @param io объект BasicUserIO для ввода пользователя
     * @return созданный объект Coordinates
     * @throws InvalidFieldException если введенные значения некорректны
     */
    public static Coordinates parseCoordinates(BasicUserIO io) throws InvalidFieldException {
        return new Coordinates(
            BasicParsers.Repeater.doUntilGet(CoordinatesMaker::parseX, io),
            BasicParsers.Repeater.doUntilGet(CoordinatesMaker::parseY, io)
        );
    }

    /**
     * Считывает значение координаты X из ввода пользователя.
     *
     * @param io объект BasicUserIO для ввода пользователя
     * @return значение координаты X
     * @throws InvalidFieldException если введенное значение некорректно
     */
    public static Float parseX(BasicUserIO io) throws InvalidFieldException {
        Float x = BasicParsers.parseFloat(
            io,
            "X: ",
            "Error."
        );
        Coordinates.VALIDATOR.validateX(x);
        return x;
    }


    /**
     * Считывает значение координаты Y из ввода пользователя.
     *
     * @param io объект BasicUserIO для ввода пользователя
     * @return значение координаты Y
     * @throws InvalidFieldException если введенное значение некорректно
     */
    public static Float parseY(BasicUserIO io) throws InvalidFieldException {
        Float y = BasicParsers.parseFloat(
            io,
            "Y: ",
            "Error."
        );
        Coordinates.VALIDATOR.validateY(y);
        return y;
    }
}
