package com.motaharinia.msutility.tools.network;


import com.motaharinia.msutility.tools.network.requestinfo.RequestInfoDto;
import eu.bitwalker.useragentutils.UserAgent;
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
     * @param withAgentOsDeviceData آیا اطلاعات مرورگر و سیستم عامل و دستگاه هم نیاز است؟
     * @return خروجی: مدل اطلاعات درخواست وب
     */
    static RequestInfoDto readCurrentRequestInfo(boolean withAgentOsDeviceData) {
        RequestInfoDto requestInfoDto = new RequestInfoDto();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!ObjectUtils.isEmpty(servletRequestAttributes)) {
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            if (!ObjectUtils.isEmpty(httpServletRequest)) {
                //به دست آوردن نشانی کامل درخواست وب
                StringBuilder requestURL = new StringBuilder(httpServletRequest.getRequestURL().toString());
                String queryString = httpServletRequest.getQueryString();
                if (ObjectUtils.isEmpty(httpServletRequest)) {
                    requestInfoDto.setFullUrl(requestURL.toString());
                } else {
                    requestInfoDto.setFullUrl(requestURL.append('?').append(queryString).toString());
                }
                //به دست آوردن نشانی آی پی درخواست دهنده وب
                requestInfoDto.setIpAddress(httpServletRequest.getHeader("X-FORWARDED-FOR"));
                if (ObjectUtils.isEmpty(requestInfoDto.getIpAddress())) {
                    requestInfoDto.setIpAddress(httpServletRequest.getRemoteAddr());
                }

                //دریافت اطلاعات مرورگر
                if (withAgentOsDeviceData && !ObjectUtils.isEmpty(httpServletRequest.getHeader("User-Agent"))) {
                    String userAgentHeader = httpServletRequest.getHeader("User-Agent");
                    if (userAgentHeader.toUpperCase().startsWith("POSTMAN") && userAgentHeader.contains("/")) {
                        //اگر درخواست وب از طریق postman فراخوانی شده باشد
                        String[] userAgentHeaderArray = userAgentHeader.split("/");
                        requestInfoDto.setBrowser(userAgentHeaderArray[0]);
                        requestInfoDto.setBrowserVersion(userAgentHeaderArray[1]);
                        requestInfoDto.setOperatingSystem("Unknown");
                        requestInfoDto.setDeviceType("Unknown");
                    } else {
                        //اگر درخواست وب از طریق مرورگر فراخوانی شده باشد
                        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
                        requestInfoDto.setBrowser(userAgent.getBrowser().getName());
                        if (!ObjectUtils.isEmpty(userAgent.getBrowserVersion()))
                            requestInfoDto.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
                        requestInfoDto.setOperatingSystem(userAgent.getOperatingSystem().getName());
                        requestInfoDto.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
                    }
                }

            }
        }
        return requestInfoDto;
    }


}
