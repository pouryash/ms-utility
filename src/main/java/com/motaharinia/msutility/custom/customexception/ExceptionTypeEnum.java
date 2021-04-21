package com.motaharinia.msutility.custom.customexception;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مقادیر ثابت نوع اکسپشن
 */
public enum ExceptionTypeEnum {

    /**
     * نوع خطای عمومی غیر دسته بندی شده مثل خطاهای 500 سرور
     */
    GENERAL_EXCEPTION("GENERAL_EXCEPTION"),
    /**
     * نوع خطای بیزینسی پروژه که توسط برنامه نویسان مطابق با فرآیند بیزینس پرتاب میشوند
     */
    BUSINESS_EXCEPTION("BUSINESS_EXCEPTION"),
    /**
     * نوع خطای اعتبارسنجی سفارشی
     */
    VALIDATION_EXCEPTION("VALIDATION_EXCEPTION"),
    /**
     * نوع خطای فراخوانی سرویسهای بیرونی
     */
    EXTERNAL_CAL_EXCEPTION("EXTERNAL_CAL_EXCEPTION");

    private final String value;

    ExceptionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
