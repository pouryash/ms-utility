package com.motaharinia.msutility.tools.fso.download;

import com.motaharinia.msutility.tools.fso.view.FileViewModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل فایل جهت دانلود
 */
@Data
public class FileDownloadModel extends FileViewModel implements Serializable {
    /**
     * آرایه بایت داده فایل
     */
    private byte[] dataByteArray;
}
