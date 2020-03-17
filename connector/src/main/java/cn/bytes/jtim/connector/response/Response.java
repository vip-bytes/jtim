package cn.bytes.jtim.connector.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @date 2020/3/17 11:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    private Object body;

    private String code;

    private String message;

    private String path;

    private String language;

    public static Response response(ResponseCode responseCode, Object body, String path) {
        return Response.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .body(body)
                .path(path)
                .build();
    }

    public static Response ok() {
        return ok(null);
    }

    public static Response ok(String path) {
        return response(ResponseCode.OK, null, path);
    }

    public static Response fail(ResponseCode responseCode, String path) {
        return fail(responseCode, null, path);
    }

    public static Response fail(ResponseCode responseCode, Object body, String path) {
        return response(responseCode, body, path);
    }

}
