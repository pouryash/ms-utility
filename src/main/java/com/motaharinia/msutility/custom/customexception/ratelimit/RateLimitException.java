package com.motaharinia.msutility.custom.customexception.ratelimit;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author eng.motahari@gmail.com<br>
 * Rate limit exception
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class RateLimitException extends RuntimeException {
    /**
     * Exception message
     */
    private final String MESSAGE = "RATE_LIMIT_EXCEPTION.BAN";
}
