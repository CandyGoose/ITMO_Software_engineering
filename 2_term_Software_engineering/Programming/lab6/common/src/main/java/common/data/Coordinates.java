package common.data;


import java.io.Serializable;

/**
 * Этот класс представляет точку в двумерном пространстве
 */
public class Coordinates implements Serializable {

    /**
     * Координата x
     * Максимальное значение поля: 741
     */
    private final float x;

    /**
     * Координата y
     * Поле не может быть null
     */
    private final Float y;

    /**
     * Конструктор, задающий параметры координат
     * @param x - координата x
     * @param y - координата y
     */
    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату точки X
     *
     * @return X
     */
    public float getX() {
        return x;
    }


    /**
     * Возвращает координату точки Y
     *
     * @return Y
     */
    public Float getY() {
        return y;
    }
}