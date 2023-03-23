package common.interaction;

import java.io.Serializable;

/**
 * Класс, содержащий результат выполнения операции в сетевом взаимодействии.
 * Реализует интерфейс Serializable для возможности передачи объекта по сети.
 */
public class Response implements Serializable {
    /**
     * Результат выполнения операции.
     */
    private final ResponseResult responseResult;
    /**
     * Тело ответа.
     */
    private final String responseBody;

    /**
     * Конструктор класса Response.
     * @param responseResult результат выполнения операции.
     * @param responseBody тело ответа.
     */
    public Response(ResponseResult responseResult, String responseBody) {
        this.responseResult = responseResult;
        this.responseBody = responseBody;
    }

    /**
     * Возвращает результат выполнения операции.
     * @return результат выполнения операции.
     */
    public ResponseResult getResponseResult() {
        return responseResult;
    }

    /**
     * Возвращает тело ответа.
     * @return тело ответа.
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Возвращает строковое представление объекта Response.
     * @return строковое представление объекта Response.
     */
    @Override
    public String toString() {
        return "Response[" + responseResult + ", " + responseBody + "]";
    }
}