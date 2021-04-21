package com.motaharinia.msutility.custom.customvalidation.password;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی رمز عبور با تعیین حداقل و یا پیچیده بودن رمز <br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String MESSAGE_MIN = "CUSTOM_VALIDATION.PASSWORD_MIN";
    private static final String MESSAGE_MAX = "CUSTOM_VALIDATION.PASSWORD_MAX";
    private static final String MESSAGE_COMPLICATED = "CUSTOM_VALIDATION.PASSWORD_COMPLICATED";

    private String message;
    private Integer minLength;
    private Integer maxLength;
    private Boolean complicated;
    private String complicatedSpecialChars;

    @Override
    public void initialize(Password password) {
        minLength = password.minLength();
        maxLength = password.maxLength();
        complicated = password.complicated();
        complicatedSpecialChars = password.complicatedSpecialChars();
        message = password.message();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(password)) {
            return true;
        }
        if (minLength <= 0) {
            return false;
        }
        if (maxLength <= 0) {
            return false;
        }
        boolean result = true;
        if (password.length() < minLength) {
            message = MESSAGE_MIN + "::" + minLength;
            cvc.disableDefaultConstraintViolation();
            cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        if (password.length() > maxLength) {
            message = MESSAGE_MAX + "::" + maxLength;
            cvc.disableDefaultConstraintViolation();
            cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        complicatedSpecialChars=CustomValidationRegularExceptionEnum.PASSWORD_COMPLEXITY.getValue().replace("%COMPLECATED_SPECIAL_CHARS%",complicatedSpecialChars);
        if (complicated.equals(true) && (!password.matches(complicatedSpecialChars))){
                message = MESSAGE_COMPLICATED;
                cvc.disableDefaultConstraintViolation();
                cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                result = false;
        }
        return result;
    }
}
