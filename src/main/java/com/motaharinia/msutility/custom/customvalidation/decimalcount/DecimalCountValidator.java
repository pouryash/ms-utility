package com.motaharinia.msutility.custom.customvalidation.decimalcount;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی محدوده و تعداد دقیق تعداد رقم اعشار<br>
 * فقط برای فیلدهای از نوع Double میتوان از این اعتبارسنجی استفاده کرد
 */
public class DecimalCountValidator implements ConstraintValidator<DecimalCount, Double> {

    private static final String MESSAGE_EXACT = "CUSTOM_VALIDATION.DECIMAL_COUNT_EXACT";
    private static final String MESSAGE_MIN = "CUSTOM_VALIDATION.DECIMAL_COUNT_MIN";
    private static final String MESSAGE_MAX = "CUSTOM_VALIDATION.DECIMAL_COUNT_MAX";

    private String message;
    private Integer min;
    private Integer max;
    private Integer exact;

    @Override
    public void initialize(DecimalCount decimalCount) {
        min = decimalCount.min();
        max = decimalCount.max();
        exact = decimalCount.exact();
        message = decimalCount.message();
    }

    @Override
    public boolean isValid(Double number, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(number)) {
            return true;
        }
        boolean result = true;
        String doubleStr = number + "";
        String[] parts = doubleStr.split("\\.");
        String decimalPart = parts[1];

        if (exact > 0) {
            if (!exact.equals(decimalPart.length())) {
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
            } else if (min > 0 && decimalPart.length() < min) {
                result = false;
                message = MESSAGE_MIN + "::" + min;
            } else if (max > 0 && decimalPart.length() > max) {
                result = false;
                message = MESSAGE_MAX + "::" + max;
            }
        }
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return result;
    }


}
