package com.motaharinia.msutility.tools.fso.check;

import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مدل که نوع مسیر داده شده فایل یا دایرکتوری و رفرنس فایل آن مسیر را در خود دارد
 */
@Data
public class FsoPathCheckDto implements Serializable {
    /**
     * رفرنس فایل مسیر
     */
    File file;
    /**
     * نوع مسیر
     */
    FsoPathCheckTypeEnum typeEnum;
}
