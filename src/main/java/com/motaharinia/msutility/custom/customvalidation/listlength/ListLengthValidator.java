package com.motaharinia.msutility.custom.customvalidation.listlength;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی محدوده اندازه لیست<br>
 * فقط برای فیلدهای از نوع List میتوان از این اعتبارسنجی استفاده کرد
 */
public class ListLengthValidator implements ConstraintValidator<ListLength, List> {

    private static final String MESSAGE_EXACT = "CUSTOM_VALIDATION.LIST_LENGTH_EXACT";
    private static final String MESSAGE_MIN = "CUSTOM_VALIDATION.LIST_LENGTH_MIN";
    private static final String MESSAGE_MAX = "CUSTOM_VALIDATION.LIST_LENGTH_MAX";

    private String message;
    private Integer min;
    private Integer max;
    private Integer exact;

    @Override
    public void initialize(ListLength listLength) {
        min = listLength.min();
        max = listLength.max();
        exact = listLength.exact();
        message = listLength.message();
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(list)) {
            return true;
        }
        boolean result = true;
        if (exact > 0) {
            if (!exact.equals(list.size())) {
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
            } else if (min > 0 && list.size() < min) {
                result = false;
                message = MESSAGE_MIN + "::" + min;
            } else if (max > 0 && list.size() > max) {
                result = false;
                message = MESSAGE_MAX + "::" + max;
            }
        }
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return result;
    }

}
