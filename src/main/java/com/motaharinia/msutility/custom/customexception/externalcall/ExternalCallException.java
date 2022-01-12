package com.motaharinia.msutility.custom.customexception.externalcall;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * External call exception is used when an external call has exception
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class ExternalCallException extends RuntimeException implements Serializable {

    /**
     * The class which exception occurs
     */
    private final String exceptionClassName;
    /**
     * request URL
     * ex: http://ms-notification/send
     */
    private final String requestUrl;
    /**
     * HTTP Method which is called
     * ex: GET,POST,PUT,DELETE
     */
    private final HttpMethod requestMethod;
    /**
     * Request code which developers assign to each external API call
     * ex: REQ-1001
     */
    private final String requestCode;
    /**
     * Response status code
     * ex: 400
     */
    private final String responseCode;
    /**
     * Response error message
     * ex: national code not found
     */
    private final String responseCustomError;
    /**
     * Response exception
     */
    private final Exception responseException;


    public ExternalCallException(@NotNull Class exceptionClass, @NotNull String requestUrl, @NotNull HttpMethod requestMethod, @NotNull String requestCode, @NotNull String responseCode, @NotNull String responseCustomError, @NotNull Exception responseException) {
        super("ExternalCallException");
        this.exceptionClassName = exceptionClass.getSimpleName();
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.requestCode = requestCode;
        this.responseCode = responseCode;
        this.responseCustomError = responseCustomError;
        this.responseException = responseException;
    }
}
