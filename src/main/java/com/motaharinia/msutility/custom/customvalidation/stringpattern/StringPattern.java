package com.motaharinia.msutility.custom.customvalidation.stringpattern;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author eng.motahari@gmail.com<br>
 * انوتیشن اعتبارسنجی الگوی رشته ها<br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
@Documented
@Constraint(validatedBy = StringPatternValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringPattern {
    
    String message() default "CUSTOM_VALIDATION.STRING_PATTERN";
      
    Class<?>[] groups() default {};
      
    Class<? extends Payload>[] payload() default {};
    
    String pattern();
    
}
