package com.motaharinia.msutility.custom.customvalidation.integerrange;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی محدوده اعداد صحیح<br>
 * فقط برای فیلدهای از نوع Integer میتوان از این اعتبارسنجی استفاده کرد
 */
public class IntegerRangeValidator implements ConstraintValidator<IntegerRange, Integer> {

    private static final String MESSAGE_MIN = "CUSTOM_VALIDATION.INTEGER_RANGE_MIN";
    private static final String MESSAGE_MAX = "CUSTOM_VALIDATION.INTEGER_RANGE_MAX";


    private String message;
    private Integer min;
    private Integer max;

    @Override
    public void initialize(IntegerRange integerRange) {
        min = integerRange.min();
        max = integerRange.max();
        message = integerRange.message();
    }

    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(number)) {
            return true;
        }

        boolean result = true;

        if (number < min) {
            result = false;
            message = MESSAGE_MIN + "::" + min;
        } else if (number > max) {
            result = false;
            message = MESSAGE_MAX + "::" + max;
        }

        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return result;
    }


}
