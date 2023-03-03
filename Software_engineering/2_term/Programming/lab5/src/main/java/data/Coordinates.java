package data;


/**
 * Этот класс представляет точку в двумерном пространстве
 */
public class Coordinates {
    private final float x;
    private final Float y;

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