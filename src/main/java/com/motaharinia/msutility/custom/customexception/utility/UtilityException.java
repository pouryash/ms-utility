package com.motaharinia.msutility.custom.customexception.utility;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * Exceptions occurs inside utility package
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class UtilityException extends RuntimeException implements Serializable {

    /**
     * The class which exception occurs
     */
    private final Class exceptionOccurredClass;
    /**
     * Exception message
     */
    private final String exceptionMessage;
    /**
     * Exception description added by developer to check in Kibana later
     */
    private final String exceptionDescription;


    public UtilityException(@NotNull Class exceptionOccurredClass, @NotNull UtilityExceptionKeyEnum exceptionKeyEnum, String exceptionDescription) {
        this.exceptionOccurredClass = exceptionOccurredClass;
        this.exceptionMessage = "UTILITY_EXCEPTION." + exceptionOccurredClass.getSimpleName().toUpperCase() + "." + exceptionKeyEnum.getValue().toUpperCase();
        this.exceptionDescription = exceptionDescription;
    }

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}
