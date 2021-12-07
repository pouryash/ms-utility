package com.motaharinia.msutility.custom.customvalidation.organizationnationalcode;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی کد ملی سازمان <br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class OrganizationNationalCodeValidator implements ConstraintValidator<OrganizationNationalCode, String> {
    @Override
    public boolean isValid(String nationalCode, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(nationalCode)) {
            return true;
        }
        return nationalCode.matches(CustomValidationRegularExceptionEnum.ORGANIZATION_NATIONAL_CODE.getValue());
    }
}
