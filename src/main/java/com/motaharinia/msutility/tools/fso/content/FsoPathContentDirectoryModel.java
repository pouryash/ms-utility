package com.motaharinia.msutility.tools.fso.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل اطلاعات دایرکتوری
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FsoPathContentDirectoryModel implements Serializable {
    /**
     * نام دایرکتوری
     */
    private String name;
    /**
     * مسیر  دایرکتوری والد
     */
    private String directoryPath;
    /**
     * مسیر کامل دایرکتوری به همراه نام
     */
    private String fullPath;
    /**
     * تاریخ آخرین تغییر
     */
    private Date lastModified;
    /**
     * رشته تاریخ آخرین تغییر
     */
    private String lastModifiedString;
    /**
     * حجم دایرکتوری
     */
    private Long size;
}
