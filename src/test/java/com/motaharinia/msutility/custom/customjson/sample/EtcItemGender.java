package com.motaharinia.msutility.custom.customjson.sample;


/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مقادیر ثابت جنسیت
 */
public enum EtcItemGender {

    /**
     *جنسیت : مرد
     */
    GENDER_MALE("COMBO_ITEM.GENDER_MALE"),
    /**
     *جنسیت : زن
     */
    GENDER_FEMALE("COMBO_ITEM.GENDER_FEMALE"),
    ;
    private final String value;

    EtcItemGender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
