package com.motaharinia.msutility.tools.fso.check;


/**
 * @author https://github.com/motaharinia<br>
 * مقادیر ثابت نوع مسیر فایل یا دایرکتوری
 */
public enum FsoPathCheckTypeEnum {

    /**
     * مسیر داده شده یک فایل است
     */
    FILE("FILE"),
    /**
     * مسیر داده شده یک دایرکتوری است
     */
    DIRECTORY("DIRECTORY");

    private final String value;

    FsoPathCheckTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
