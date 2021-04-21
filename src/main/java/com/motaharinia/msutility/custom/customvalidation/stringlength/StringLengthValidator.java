package com.motaharinia.msutility.custom.customvalidation.stringlength;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی محدوده و تعداد دقیق طول رشته ها<br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class StringLengthValidator implements ConstraintValidator<StringLength, String> {

    private static final String MESSAGE_EXACT = "CUSTOM_VALIDATION.STRING_LENGTH_EXACT";
    private static final String MESSAGE_MIN = "CUSTOM_VALIDATION.STRING_LENGTH_MIN";
    private static final String MESSAGE_MAX = "CUSTOM_VALIDATION.STRING_LENGTH_MAX";

    private String message;
    private Integer min;
    private Integer max;
    private Integer exact;

    @Override
    public void initialize(StringLength stringLength) {
        min = stringLength.min();
        max = stringLength.max();
        exact = stringLength.exact();
        message = stringLength.message();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(string)) {
            return true;
        }
        boolean result = true;
        if (exact > 0) {
            if (!exact.equals(string.length())) {
                result = false;
                message = MESSAGE_EXACT + "::" + exact;
            }
        } else {
            if (min <= 0 && max <= 0) {
                result = false;
                message += "[min<=0 || max<=0]";
            } else if (min > 0 && max > 0 && min > max) {
                result = false;
                message += "[min>max]";
            } else if (min > 0 && string.length() < min) {
                result = false;
                message = MESSAGE_MIN + "::" + min;
            } else if (max > 0 && string.length() > max) {
                result = false;
                message = MESSAGE_MAX + "::" + max;
            }
        }
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return result;
    }
}
