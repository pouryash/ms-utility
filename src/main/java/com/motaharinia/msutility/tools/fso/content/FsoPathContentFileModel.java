package com.motaharinia.msutility.tools.fso.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل اطلاعات فایل
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FsoPathContentFileModel implements Serializable {
    /**
     * نام فایل
     * example:"2019-06-12_10-39-29_dsa - Copy (2)"
     */
    private String name;
    /**
     * پسوند فایل
     * example:"png"
     */
    private String extension;
    /**
     * نام کامل فایل با پسوند
     * example:"2019-06-12_10-39-29_dsa - Copy (2).png"
     */
    private String fullName;
    /**
     * مسیر دایرکتوری والد
     */
    private String directoryPath;
    /**
     * مسیر کامل دایرکتوری به همراه نام فایل
     */
    private String fullPath;
    /**
     * تاریخ آخرین تغییر
     */
    private Date lastModifiedDate;
    /**
     * رشته تاریخ آخرین تغییر
     */
    private String lastModifiedDateString;
    /**
     * حجم فایل
     */
    private Long size;
    /**
     * نوع mimeType فایل
     * example:"image/png"
     */
    private String mimeType;
}
