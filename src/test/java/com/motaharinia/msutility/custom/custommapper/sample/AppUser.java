package com.motaharinia.msutility.custom.custommapper.sample;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * کلاس تست انتیتی برای تست مپر
 */
@Data
public class AppUser {
    private Instant testInstant;
    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;
    private Date testDate;
}
