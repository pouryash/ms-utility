package com.motaharinia.msutility.custom.customdto.search.data.columnconfig;

/**
* @author eng.motahari@gmail.com<br>
 *     اینام مقادیر ثابت جهت نمایش افقی مطالب
 */
public enum SearchDataColumnConfigAlignEnum {
    /**
     *چپ چین
     */
    LEFT("LEFT"),
    /**
     *راست چین
     */
    RIGHT("RIGHT"),
    /**
     *وسط چین
     */
    CENTER("CENTER")
    ;

    //جهت نمایش افقی مطالب
    private String value;

    /**
     * مقدار جهت نمایش افقی مطالب را خروجی میدهد
     *
     * @return خروجی: مقدارجهت نمایش افقی مطالب
     */
    public String getValue() {
        return this.value;
    }

    /**
     * تابع سازنده ثابت جهت نمایش افقی مطالب از روی مقدار جهت نمایش افقی مطالب آن
     *
     * @param value مقدار ثابت جهت نمایش افقی مطالب
     */
    SearchDataColumnConfigAlignEnum(String value) {
        this.value = value;
    }

}
