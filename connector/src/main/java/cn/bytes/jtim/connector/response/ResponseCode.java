package cn.bytes.jtim.connector.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @date 2020/3/17 10:39
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    //2xx
    OK("200", "操作成功"),

    //4xx
    BAD_REQUEST_PARAMS("400", "请求参数错误"),

    //5xx
    ERROR("500", "服务错误"),
    ;

    private String code;

    private String message;

}
