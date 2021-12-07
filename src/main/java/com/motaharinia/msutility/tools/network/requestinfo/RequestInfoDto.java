package com.motaharinia.msutility.tools.network.requestinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مدل اطلاعات درخواست وب
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfoDto {
    /**
     * نشانی کامل درخواست وب
     */
    private String fullUrl = "";
    /**
     * نشانی آی پی درخواست دهنده وب
     */
    private String ipAddress = "";
    /**
     * نام مرورگر درخواست دهنده وب
     * Chrome 9 / PostmanRuntime
     */
    private String browser = "";
    /**
     * نسخه مرورگر درخواست دهنده وب
     * 92.0.4515.131 / 7.26.8
     */
    private String browserVersion = "";
    /**
     * سیستم عامل درخواست دهنده وب
     * Mac OS X / Unknown
     */
    private String operatingSystem = "";
    /**
     * نوع دستگاه درخواست دهنده وب
     * Computer / Unknown
     */
    private String deviceType = "";
}
