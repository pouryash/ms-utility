package com.motaharinia.msutility.tools.fso.mimetype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author https://github.com/motaharinia<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FsoMimeTypeModel implements Serializable {
    /**
     * رشته mimeType فایل
     */
    String mimeType;

    /**
     * نوع فایل
     */
    FsoMimeTypeEnum type;

    /**
     * پسوند فایل
     */
    String extension;
}
