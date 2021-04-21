package com.motaharinia.msutility.custom.customvalidation.integerrange;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author https://github.com/motaharinia<br>
 * انوتیشن اعتبارسنجی محدوده اعداد صحیح<br>
 * فقط برای فیلدهای از نوع Integer میتوان از این اعتبارسنجی استفاده کرد
 */
@Documented
@Constraint(validatedBy = IntegerRangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerRange {

    String message() default "CUSTOM_VALIDATION.INTEGER_RANGE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * ابتدای محدوده عددی
     *
     * @return خروجی:
     */
    int min() default -2147483648;

    /**
     * انتهای محدوده عددی
     *
     * @return خروجی:
     */
    int max() default 2147483647;

}
