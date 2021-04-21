package com.motaharinia.msutility.custom.customexception.externalcall;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس خطای بیزینس پایه که تمامی کلاسهای خطای بیزینس سامانه از آن توسعه می یابند
 */


@Getter
@EqualsAndHashCode(callSuper = true)
public class ExternalCallException extends RuntimeException implements Serializable {

    /**
     * کلاسی که در آن خطا اتفاق می افتد
     */
    private final String exceptionClassName;
    /**
     * نشانی درخواست
     */
    private final String requestUrl;
    /**
     * نوع درخواست وب مانند GET,POST,PUT,DELETE
     */
    private final HttpMethod httpMethod;
    /**
     * اکسپشن اتفاق افتاده
     */
    private final Exception exception;


    /**
     * متد سازنده اکسپشن
     *
     * @param exceptionClass کلاسی که در آن خطا اتفاق می افتد
     * @param requestUrl     نشانی درخواست
     * @param httpMethod     نوع درخواست وب
     * @param exception      اکسپشن اتفاق افتاده
     */
    public ExternalCallException(@NotNull Class exceptionClass, @NotNull String requestUrl, @NotNull HttpMethod httpMethod, @NotNull Exception exception) {
        super("ExternalCallException");
        this.exceptionClassName = exceptionClass.getSimpleName();
        this.requestUrl = requestUrl;
        this.httpMethod = httpMethod;
        this.exception = exception;
    }


}
