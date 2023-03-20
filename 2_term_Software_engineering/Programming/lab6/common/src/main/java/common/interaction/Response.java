package common.interaction;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseResult responseResult;
    private final String responseBody;

    public Response(ResponseResult responseResult, String responseBody) {
        this.responseResult = responseResult;
        this.responseBody = responseBody;
    }
    public ResponseResult getResponseResult() {
        return responseResult;
    }


    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseResult + ", " + responseBody + "]";
    }
}