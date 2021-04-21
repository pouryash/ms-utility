package com.motaharinia.msutility.tools.fso.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import lombok.Data;

import java.io.Serializable;


/**
 * @author https://github.com/motaharinia<br>

 * کلاس مدل مشاهده فایل جهت ارسال به کلاینت
 */
@Data
public class FileViewModel implements Serializable {
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
     * مسیر کامل دایرکتوری به همراه نام فایل
     * example:"/70755/personality/2019-06-12_10-39-29_dsa - Copy (2).png"
     */
    @JsonIgnore
//    @GraphQLIgnore
    private String fullPath;
    /**
     * قسمت هش شده ای از مسیر کامل دایرکتوری به همراه نام فایل
     * example:"LzcwNzU1L3BlcnNvbmFsaXR5LzIwMTktMDYtMTJfMTAtMzktMjlfZHNhIC0gQ29weSAoMikucG5n"
     */
    private String hashedPath;
    /**
     * تاریخ آخرین تغییر فایل
     * example:{year:1398,month:10,day:25}
     */
    private CustomDate lastModifiedDate;
    /**
     * وضعیت فایل
     * example: ADDED,DELETED,EXISTED
     */
    private FileViewModelStatusEnum statusEnum = FileViewModelStatusEnum.EXISTED;
}
