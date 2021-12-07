package com.motaharinia.msutility.custom.customvalidation.organizationregistrationno;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی شماره ثبت سازمان <br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class OrganizationRegistrationNoValidator implements ConstraintValidator<OrganizationRegistrationNo, String> {
    @Override
    public boolean isValid(String registrationNo, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(registrationNo)) {
            return true;
        }
        return registrationNo.matches(CustomValidationRegularExceptionEnum.NUMERIC_VALUE.getValue());
    }
}
