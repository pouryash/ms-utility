package com.motaharinia.msutility.custom.customexception;

/**
 * @author eng.motahari@gmail.com<br>
 * Exception type enums
 */
public enum ExceptionTypeEnum {

    /**
     * Exceptions which are not categorized
     * ex: database exceptions
     */
    GENERAL_EXCEPTION("GENERAL_EXCEPTION"),
    /**
     * نوع خطای بیزینسی پروژه که توسط برنامه نویسان مطابق با فرآیند بیزینس پرتاب میشوند
     * Business controlled by service exceptions
     * ex: user already exists
     */
    BUSINESS_EXCEPTION("BUSINESS_EXCEPTION"),
    /**
     * Validation exceptions controlled by DTO annotations
     * ex: this field in required
     */
    VALIDATION_EXCEPTION("VALIDATION_EXCEPTION"),
    /**
     * Exceptions which come from external microservices or other external APIS
     */
    EXTERNAL_CALL_EXCEPTION("EXTERNAL_CALL_EXCEPTION"),
    /**
     * Rate limit exceptions which occurs when an API call more than expected
     */
    RATE_LIMIT_EXCEPTION("RATE_LIMIT_EXCEPTION"),
    ;

    private final String value;

    ExceptionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
