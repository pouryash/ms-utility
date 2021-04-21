package com.motaharinia.msutility.tools.fso.mimetype;


/**
 * @author https://github.com/motaharinia<br>
 * مقادیر ثابت نوع فایل
 */
public enum FsoMimeTypeEnum {

    /**
     * فایلهای عمومی
     */
    GENERAL("GENERAL"),
    /**
     * فایلهای آفیس
     */
    APPLICATION("APPLICATION"),
    /**
     * فایلهای تصویری
     */
    IMAGE("IMAGE");

    private final String value;

    FsoMimeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
