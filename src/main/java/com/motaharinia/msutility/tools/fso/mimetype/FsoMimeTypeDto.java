package com.motaharinia.msutility.tools.fso.mimetype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FsoMimeTypeDto implements Serializable {
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
