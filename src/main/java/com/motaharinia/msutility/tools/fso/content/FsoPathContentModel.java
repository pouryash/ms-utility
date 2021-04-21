package com.motaharinia.msutility.tools.fso.content;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل حاوی اطلاعات فایلها و دایرکتوری های داخل یک مسیر
 */
@Data
public class FsoPathContentModel implements Serializable {
    /**
     * لیست اطلاعات دایرکتوری های داخل مسیر داده شده
     */
    private List<FsoPathContentDirectoryModel> directoryModelList = new ArrayList<>();
    /**
     * لیست اطلاعات فایلهای داخل مسیر داده شده
     */
    private List<FsoPathContentFileModel> fileModelList = new ArrayList<>();
}
