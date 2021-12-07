package com.motaharinia.msutility.tools.fso.download;

import com.motaharinia.msutility.tools.fso.view.FileViewDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مدل فایل جهت دانلود
 */
@Data
public class FileDownloadDto extends FileViewDto implements Serializable {
    /**
     * آرایه بایت داده فایل
     */
    private byte[] dataByteArray;
}
