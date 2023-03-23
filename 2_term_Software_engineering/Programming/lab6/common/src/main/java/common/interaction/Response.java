package common.interaction;

import java.io.Serializable;

/**
 * �����, ���������� ��������� ���������� �������� � ������� ��������������.
 * ��������� ��������� Serializable ��� ����������� �������� ������� �� ����.
 */
public class Response implements Serializable {
    /**
     * ��������� ���������� ��������.
     */
    private final ResponseResult responseResult;
    /**
     * ���� ������.
     */
    private final String responseBody;

    /**
     * ����������� ������ Response.
     * @param responseResult ��������� ���������� ��������.
     * @param responseBody ���� ������.
     */
    public Response(ResponseResult responseResult, String responseBody) {
        this.responseResult = responseResult;
        this.responseBody = responseBody;
    }

    /**
     * ���������� ��������� ���������� ��������.
     * @return ��������� ���������� ��������.
     */
    public ResponseResult getResponseResult() {
        return responseResult;
    }

    /**
     * ���������� ���� ������.
     * @return ���� ������.
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * ���������� ��������� ������������� ������� Response.
     * @return ��������� ������������� ������� Response.
     */
    @Override
    public String toString() {
        return "Response[" + responseResult + ", " + responseBody + "]";
    }
}