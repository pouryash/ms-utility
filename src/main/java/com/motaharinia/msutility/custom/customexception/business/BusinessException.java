package com.motaharinia.msutility.custom.customexception.business;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * Business class which all business classes extend from it
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException implements Serializable {

    /**
     * The class which exception occurs
     */
    private final String exceptionClassName;
    /**
     * Data unique id which exception is related to it (it is used to search in Kibana later)
     * ex: ID or national code of a user
     */
    private final String dataId;
    /**
     * Exception message
     */
    private final String message;
    /**
     * Exception description added by developer to check in Kibana later
     */
    private final String description;


    protected BusinessException(@NotNull Class exceptionClass, @NotNull String dataId, @NotNull String message, String description) {
        this.exceptionClassName = exceptionClass.getSimpleName();
        this.dataId = dataId;
        this.message = message;
        this.description = description;
    }
}
