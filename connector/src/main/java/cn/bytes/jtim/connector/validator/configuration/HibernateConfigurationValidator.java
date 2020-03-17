package cn.bytes.jtim.connector.validator.configuration;

import cn.bytes.jtim.connector.validator.Intercept;
import cn.bytes.jtim.connector.validator.ValidatorHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * configuration
 *
 * @author 江浩
 */
public class HibernateConfigurationValidator {

    @Bean
    @ConditionalOnMissingBean(Validator.class)
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    @ConditionalOnBean(Validator.class)
    @ConditionalOnMissingBean(ValidatorHelper.class)
    public ValidatorHelper validatorHelper(Validator validator) {
        return new ValidatorHelper(validator);
    }

    @Bean
    @ConditionalOnBean({Validator.class})
    @ConditionalOnMissingBean(Intercept.class)
    public Intercept intercept(ValidatorHelper validatorHelper) {
        return new Intercept(validatorHelper);
    }


}
