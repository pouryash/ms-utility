package com.motaharinia.msutility.tools.fso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author https://github.com/motaharinia<br>
 * Description:کلاس مدل تنظیمات ابزار فایل
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FsoConfigModel implements Serializable {
    /**
     * ابعاد تصویر بندانگشتی  فایلهای تصویری
     */
    private Integer[] thumbSizeArray = new Integer[]{60, 120};
    /**
     * پسوند فایلهای بندانگشتی تصاویر
     */
    private String thumbExtension = "thumb";
    /**
     * حداکثر تعداد مجاز فایل در یک دایرکتوری
     */
    private Integer directoryFileLimit = 100;
}
