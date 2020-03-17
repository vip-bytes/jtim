package cn.bytes.jtim.connector.validator.exception;

import cn.bytes.jtim.connector.response.ResponseCode;

/**
 * 验证异常
 *
 * @author 江浩
 */
public class ValidatorException extends BasicConnectorException {

    public ValidatorException(String message) {
        super(ResponseCode.BAD_REQUEST_PARAMS.getCode(), message);
    }
}
