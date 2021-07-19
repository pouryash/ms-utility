package com.motaharinia.msutility.tools.network.requestinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل اطلاعات درخواست وب
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfoDto {
    /**
     * نشانی کامل درخواست وب
     */
    private String fullUrl;
    /**
     * نشانی آی پی درخواست دهنده وب
     */
    private String ipAddress;
}
