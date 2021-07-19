package com.motaharinia.msutility.tools.network;


import com.motaharinia.msutility.tools.network.requestinfo.RequestInfoDto;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author https://github.com/motaharinia<br>
 * اینترفیس متدهای ابزاری اطلاعات شبکه و درخواستها
 */
public interface NetworkTools {

    /**
     * متد استخراج کننده اطلاعات درخواست وب جاری
     *
     * @return خروجی: مدل اطلاعات درخواست وب
     */
    static RequestInfoDto readCurrentRequestInfo() {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String fullUrl = "";
        String ipAddress = "";
        if (!ObjectUtils.isEmpty(httpServletRequest)) {
            //به دست آوردن نشانی کامل درخواست وب
            StringBuilder requestURL = new StringBuilder(httpServletRequest.getRequestURL().toString());
            String queryString = httpServletRequest.getQueryString();
            if (ObjectUtils.isEmpty(httpServletRequest)) {
                fullUrl = requestURL.toString();
            } else {
                fullUrl = requestURL.append('?').append(queryString).toString();
            }
            //به دست آوردن نشانی آی پی درخواست دهنده وب
            ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
            if (ObjectUtils.isEmpty(ipAddress)) {
                ipAddress = httpServletRequest.getRemoteAddr();
            }
        }
        return new RequestInfoDto(fullUrl, ipAddress);
    }


}
