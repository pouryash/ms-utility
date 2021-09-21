package com.motaharinia.msutility.tools.exception;


import com.motaharinia.msutility.custom.customexception.business.BusinessException;
import com.motaharinia.msutility.custom.customdto.exception.ExceptionDto;
import com.motaharinia.msutility.custom.customdto.exception.ExceptionMessageDto;
import com.motaharinia.msutility.custom.customexception.ExceptionTypeEnum;
import com.motaharinia.msutility.custom.customexception.externalcall.ExternalCallException;
import com.motaharinia.msutility.custom.customdto.ClientResponseDto;
import com.motaharinia.msutility.custom.customexception.ratelimit.RateLimitException;
import com.motaharinia.msutility.tools.string.StringTools;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس ابزار خطا که خطا را به مدل تبدیل میکند
 */
public interface ExceptionTools {

    /**
     * این متد تمام خطاهای سامانه را که از سطح کنترلر پرت شده باشد را گرفته
     * و پس از بررسی نوع خطا موارد زیر را برای آن انجام میدهد
     * تبدیل انواع خطا به یک مدل واحد ExceptionDto
     * ثبت خطای دریافتی در محل ذخیره سازی لاگها ELK
     *
     * @param exception           خطای دریافتی
     * @param httpServletRequest  درخواست وب
     * @param httpServletResponse پاسخ وب
     * @param appName             نام برنامه
     * @param appPort             پورت برنامه
     * @param messageSource       شیی ترجمه
     * @param userId              شناسه کاربر
     * @param username            کلمه کاربری کاربر
     * @return خروجی: مدل خطای کلاینت
     */
    static ClientResponseDto<String> doException(Exception exception, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String appName, int appPort, MessageSource messageSource, Long userId, String username) {
        ExceptionDto exceptionDto = new ExceptionDto(appName, String.valueOf(appPort));
        if (exception != null && exception.getClass() != null) {

            //به صورت پیش فرض روی خطای عمومی ست میشود
            HttpStatus httpStatus;

            //به دست آوردن مدل از روی نوع اکسپشن
            if (exception instanceof BusinessException) {
                exceptionDto = getDtoFromBusinessException((BusinessException) exception, appName, appPort, messageSource);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else if (exception instanceof MethodArgumentNotValidException) {
                exceptionDto = getDtoFromMethodArgumentNotValidException((MethodArgumentNotValidException) exception, appName, appPort, messageSource);
                httpStatus = HttpStatus.BAD_REQUEST;
            } else if (exception instanceof ExternalCallException) {
                exceptionDto = getDtoFromExternalCallException((ExternalCallException) exception, appName, appPort, messageSource);
                httpStatus = HttpStatus.CONFLICT;
            } else if (exception instanceof RateLimitException) {
                exceptionDto = getDtoFromRateLimitException((RateLimitException) exception, appName, appPort, messageSource);
                httpStatus = HttpStatus.TOO_MANY_REQUESTS;
            } else {
                exceptionDto = getDtoFromGeneralException(exception, appName, appPort);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            if (httpServletResponse != null) {
                httpServletResponse.setStatus(httpStatus.value());
            }
        }
        exceptionDto.setUrl(getRequestUrl(httpServletRequest));
        exceptionDto.setIpAddress(getRequestIpAddress(httpServletRequest));
        exceptionDto.setUserId(userId);
        exceptionDto.setUsername(username);

        return new ClientResponseDto<>(exceptionDto, exceptionDto.getMessage());
    }


    /**
     * متد سازنده مدل خطا از خطای بیزینس
     *
     * @param businessException خطای بیزینس
     * @param appName           نام برنامه
     * @param appPort           پورت برنامه
     * @param messageSource     شیی ترجمه
     * @return خروجی: مدل خطا
     */
    static ExceptionDto getDtoFromBusinessException(BusinessException businessException, String appName, int appPort, MessageSource messageSource) {
        List<ExceptionMessageDto> messageDtoList = new ArrayList<>();
        String translatedMessage = businessException.getMessage();
        if (!ObjectUtils.isEmpty(translatedMessage) && translatedMessage.toUpperCase(Locale.getDefault()).startsWith("BUSINESS_EXCEPTION")) {
            translatedMessage = StringTools.translateCustomMessage(messageSource, businessException.getMessage());
        }
        messageDtoList.add(new ExceptionMessageDto(translatedMessage, getStackTraceString(businessException), getStackTraceLineString(businessException), ""));
        ExceptionDto exceptionDto = new ExceptionDto(appName, String.valueOf(appPort));
        exceptionDto.setType(ExceptionTypeEnum.BUSINESS_EXCEPTION);
        exceptionDto.setExceptionClassName(businessException.getExceptionClassName());
        exceptionDto.setDataId(businessException.getDataId());
        if (!messageDtoList.isEmpty()) {
            exceptionDto.setMessage(messageDtoList.get(0).getMessage());
        }
        exceptionDto.setMessageDtoList(messageDtoList);
        exceptionDto.setDescription(businessException.getDescription());
        return exceptionDto;
    }


    /**
     * متد سازنده مدل خطا از خطای اعتبارسنجی
     *
     * @param methodArgumentNotValidException خطای اعتبارسنجی
     * @param appName                         نام برنامه
     * @param appPort                         پورت برنامه
     * @param messageSource                   شیی ترجمه
     * @return خروجی: مدل خطا
     */
    static ExceptionDto getDtoFromMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, String appName, int appPort, MessageSource messageSource) {
        List<ExceptionMessageDto> messageDtoList = new ArrayList<>();
        ExceptionDto exceptionDto = new ExceptionDto(appName, String.valueOf(appPort));
        exceptionDto.setType(ExceptionTypeEnum.VALIDATION_EXCEPTION);
        BindingResult result = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String translatedMessage;
        for (FieldError fieldError : fieldErrors) {
            String dtoName = fieldError.getObjectName();
            translatedMessage = fieldError.getDefaultMessage();
            if (!ObjectUtils.isEmpty(translatedMessage) && translatedMessage.toUpperCase(Locale.getDefault()).startsWith("CUSTOM_VALIDATION")) {
                translatedMessage = StringTools.translateCustomMessage(messageSource, fieldError.getDefaultMessage());
            }
            messageDtoList.add(new ExceptionMessageDto(translatedMessage, getStackTraceString(methodArgumentNotValidException), getStackTraceLineString(methodArgumentNotValidException), dtoName + "." + fieldError.getField()));
        }
        if (!messageDtoList.isEmpty()) {
            exceptionDto.setMessage(messageDtoList.get(0).getMessage());
        }
        exceptionDto.setMessageDtoList(messageDtoList);
        exceptionDto.setDescription("");
        return exceptionDto;
    }

    /**
     * متد سازنده مدل خطا از خطای فراخوانی سرویسهای بیرونی
     *
     * @param externalCallException خطای فراخوانی سرویسهای بیرونی
     * @param appName               نام برنامه
     * @param appPort               پورت برنامه
     * @param messageSource         شیی ترجمه
     * @return خروجی: مدل خطا
     */
    static ExceptionDto getDtoFromExternalCallException(ExternalCallException externalCallException, String appName, int appPort, MessageSource messageSource) {
        List<ExceptionMessageDto> messageDtoList = new ArrayList<>();
        String translatedMessage = "ExternalCallException for " + "[" + externalCallException.getRequestMethod().toString() + "]: " + externalCallException.getRequestUrl() + " message:" + externalCallException.getResponseException().getMessage();
        messageDtoList.add(new ExceptionMessageDto(translatedMessage, getStackTraceString(externalCallException), getStackTraceLineString(externalCallException), ""));
        ExceptionDto exceptionDto = new ExceptionDto(appName, String.valueOf(appPort));
        exceptionDto.setType(ExceptionTypeEnum.EXTERNAL_CALL_EXCEPTION);
        exceptionDto.setExceptionClassName(externalCallException.getExceptionClassName());
        exceptionDto.setDataId(externalCallException.getRequestUrl());
        if (!messageDtoList.isEmpty()) {
            if (externalCallException.getResponseCustomError().equalsIgnoreCase("I/O error. Connection refused: connect")) {
                exceptionDto.setMessage(StringTools.translateCustomMessage(messageSource, "EXTERNAL_CALL_EXCEPTION.IO::" + externalCallException.getRequestCode()));
            } else if (externalCallException.getResponseCode().isEmpty()) {
                exceptionDto.setMessage(StringTools.translateCustomMessage(messageSource, "EXTERNAL_CALL_EXCEPTION.UNKNOWN::" + externalCallException.getRequestCode()));
            } else {
                exceptionDto.setMessage(StringTools.translateCustomMessage(messageSource, "EXTERNAL_CALL_EXCEPTION.CODE::" + externalCallException.getResponseCode() + "," + externalCallException.getRequestCode()));
            }
        }
        exceptionDto.setMessageDtoList(messageDtoList);
        exceptionDto.setDescription(externalCallException.getRequestMethod().toString());
        return exceptionDto;
    }

    /**
     * متد سازنده مدل خطا از خطای محدودیت بازدید
     *
     * @param rateLimitException خطای محدودیت بازدید
     * @param appName            نام برنامه
     * @param appPort            پورت برنامه
     * @param messageSource      شیی ترجمه
     * @return خروجی: مدل خطا
     */
    static ExceptionDto getDtoFromRateLimitException(RateLimitException rateLimitException, String appName, int appPort, MessageSource messageSource) {
        List<ExceptionMessageDto> messageDtoList = new ArrayList<>();
        messageDtoList.add(new ExceptionMessageDto(StringTools.translateCustomMessage(messageSource, rateLimitException.getMessage()), getStackTraceString(rateLimitException), getStackTraceLineString(rateLimitException), ""));
        ExceptionDto exceptionDto = new ExceptionDto(appName, String.valueOf(appPort));
        exceptionDto.setType(ExceptionTypeEnum.RATE_LIMIT_EXCEPTION);
        exceptionDto.setExceptionClassName("");
        exceptionDto.setDataId("");
        if (!messageDtoList.isEmpty()) {
            exceptionDto.setMessage(messageDtoList.get(0).getMessage());
        }
        exceptionDto.setMessageDtoList(messageDtoList);
        exceptionDto.setDescription("");
        return exceptionDto;
    }

    /**
     * متد سازنده مدل خطا از خطای عمومی
     *
     * @param generalException خطای عمومی
     * @param appName          نام برنامه
     * @param appPort          پورت برنامه
     * @return خروجی: مدل خطا
     */
    static ExceptionDto getDtoFromGeneralException(Exception generalException, String appName, int appPort) {
        List<ExceptionMessageDto> messageDtoList = new ArrayList<>();
        messageDtoList.add(new ExceptionMessageDto(generalException.getMessage(), getStackTraceString(generalException), getStackTraceLineString(generalException), ""));
        ExceptionDto exceptionDto = new ExceptionDto(appName, String.valueOf(appPort));
        exceptionDto.setType(ExceptionTypeEnum.GENERAL_EXCEPTION);
        exceptionDto.setExceptionClassName("");
        exceptionDto.setDataId("");
        if (!messageDtoList.isEmpty()) {
            exceptionDto.setMessage(messageDtoList.get(0).getMessage());
        }
        exceptionDto.setMessageDtoList(messageDtoList);
        exceptionDto.setDescription("");
        return exceptionDto;
    }


    /**
     * این متد رشته stacktrace خطا را خروجی میدهد
     *
     * @param exception خطا
     * @return خروجی: رشته stacktrace خطا
     */
    static String getStackTraceString(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    /**
     * این متد خط اول فنی خطا را به صورت رشته خروجی میدهد
     *
     * @param exception خطا
     * @return خروجی: خط اول رشته stacktrace خطا
     */
    static String getStackTraceLineString(Exception exception) {
        if (exception.getStackTrace() != null) {
            StackTraceElement[] stackTraceElements = exception.getStackTrace();
            String relatedToClassName = stackTraceElements[0].getClassName();
            String relatedToMethodName = stackTraceElements[0].getMethodName();
            String relatedToLineNumber = Integer.toString(stackTraceElements[0].getLineNumber());
            return ("ClassName:" + relatedToClassName + " MethodName:" + relatedToMethodName + " LineNumber:" + relatedToLineNumber);
        } else {
            return "";
        }
    }


    /**
     * این متد نشانی وب را از درخواست وب خروجی میدهد
     *
     * @param httpServletRequest درخواست وب
     * @return خروجی: نشانی وب
     */
    static String getRequestUrl(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            return httpServletRequest.getServletPath();
        } else {
            return "";
        }
    }

    /**
     * این متد آی پی کاربر را از درخواست وب خروجی میدهد
     *
     * @param httpServletRequest درخواست وب
     * @return خروجی: آی پی کاربر
     */
    static String getRequestIpAddress(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = httpServletRequest.getRemoteAddr();
            }
            return ipAddress;
        } else {
            return "";
        }
    }

}
