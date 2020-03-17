package cn.bytes.jtim.connector.validator.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @date 2020/3/17 10:44
 */
@Data
@NoArgsConstructor
public class BasicConnectorException extends RuntimeException {

    private String code;

    private String message;

    public BasicConnectorException(String code) {
        this.code = code;
    }

    public BasicConnectorException(String code, String message) {
        super(message);
        this.code = code;
    }
}
