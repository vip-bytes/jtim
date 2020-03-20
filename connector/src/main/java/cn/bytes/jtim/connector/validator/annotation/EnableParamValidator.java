package cn.bytes.jtim.connector.validator.annotation;

import cn.bytes.jtim.connector.validator.configuration.AutoEnableParamValidatorImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * All rights Reserved
 * <p>标识是否开启参数校验</p>
 *
 * @version V1.0
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoEnableParamValidatorImportSelector.class)
public @interface EnableParamValidator {
}
