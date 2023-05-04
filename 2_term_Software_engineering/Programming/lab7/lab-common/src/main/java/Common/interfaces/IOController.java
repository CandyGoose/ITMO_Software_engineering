package Common.interfaces;

import java.io.IOException;

/**
 * Интерфейс для контроля ввода/вывода данных
 */
public interface IOController {
    /**
     * Отправка данных
     * @param data данные для отправки
     * @throws IOException если произошла ошибка ввода/вывода
     */
    void send(Data data) throws IOException;

    /**
     * Получение данных
     * @return полученные данные
     * @throws IOException если произошла ошибка ввода/вывода
     * @throws ClassNotFoundException если класс не найден
     */
    Data receive() throws IOException, ClassNotFoundException;
}
