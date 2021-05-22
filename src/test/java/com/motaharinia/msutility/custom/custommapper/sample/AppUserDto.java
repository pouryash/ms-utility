package com.motaharinia.msutility.custom.custommapper.sample;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppUserDto implements Serializable {
    private Long testInstant;
    private Long testLocalDate;
    private Long testLocalDateTime;
    private Long testDate;
}
