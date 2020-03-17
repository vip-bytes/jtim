package cn.bytes.jtim.connector.validator.exception;

import cn.bytes.jtim.connector.response.ResponseCode;
import lombok.Data;

/**
 * @author 江浩
 */
@Data
public class InvokerException extends BasicConnectorException {

    public InvokerException(String key, String message) {
        super(ResponseCode.BAD_REQUEST_PARAMS.getCode(), String.format("%s:%s", key, message));
    }
}
