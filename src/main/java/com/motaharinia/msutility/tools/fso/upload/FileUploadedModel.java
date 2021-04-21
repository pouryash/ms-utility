package com.motaharinia.msutility.tools.fso.upload;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل فایل جهت دانلود
 */
@Data
public class FileUploadedModel implements Serializable {
    /**
     * نام فایل
     * example:"2019-06-12_10-39-29_dsa - Copy (2)"
     */
    protected String name;
    /**
     * پسوند فایل
     * example:"png"
     */
    protected String extension;
    /**
     * نام کامل فایل با پسوند
     * example:"2019-06-12_10-39-29_dsa - Copy (2).png"
     */
    protected String fullName;
    /**
     * حجم فایل
     * example:12109
     */
    protected Long size;
    /**
     * نوع mimeType فایل
     * example:"image/png"
     */
    protected String mimeType;
    /**
     * درصورتی که فایل جدید آپلود شده باشد این فیلد پر میشود
     */
    protected String key;
    /**
     * تاریخ و زمان آپلود
     */
    private Date uploadDateTime;
    /**
     * مسیر آپلود شده
     */
    private String uploadedPath;
    /**
     * نام انتیتی فایل آپلود شده
     */
    private String entity;
    /**
     * نام زیرسیستم فایل آپلود شده
     */
    private String subSystem;
    /**
     * آرایه بایت داده فایل
     */
    private byte[] dataByteArray;
    /**
     * مسیر پوشه
     */
    private String directoryRealPath;
    /**
     * مسیر هش شده پوشه
     */
    private String directoryHashedPath;

}
