package com.motaharinia.msutility.tools.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelDto {
    /**
     * داده های سطرها
     */
    private List<Object[]> rowList;
    /**
     * لیستی از اطلاعات ستونهای گرید را در خود دارد
     */
    private List<ExcelColumnDto> columnList;
}
