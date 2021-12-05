package com.motaharinia.msutility.custom.customjson.sample;


import com.motaharinia.msutility.custom.customjson.serializer.CustomEnum;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مقادیر ثابت جنسیت
 */
public enum EtcItemGender implements CustomEnum {

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

    @Override
    public String getValue() {
        return value;
    }
}
