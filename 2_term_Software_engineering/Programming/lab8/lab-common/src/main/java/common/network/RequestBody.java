package common.network;

import java.io.Serializable;

/**
 * Класс, представляющий тело запроса.
 */
public class RequestBody implements Serializable {
    /**
     * Аргументы
     */
    private String[] args;

    /**
     * Создает новый объект тела запроса.
     *
     * @param args аргументы запроса.
     */
    public RequestBody(String[] args) {
        this.args = args;
    }

    /**
     * Возвращает аргумент запроса по индексу.
     *
     * @param i индекс аргумента.
     * @return аргумент запроса.
     */
    public String getArg(int i) {
        return args[i];
    }

    /**
     * Возвращает длину аргументов запроса.
     *
     * @return длина аргументов запроса.
     */
    public int getArgsLength() {
        return args.length;
    }
}
