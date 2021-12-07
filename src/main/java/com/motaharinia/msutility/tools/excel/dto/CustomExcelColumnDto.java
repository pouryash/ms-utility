/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motaharinia.msutility.tools.excel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مدل تنظمیات ستونهای اکسل
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomExcelColumnDto implements Serializable {

    /**
     * فرمت کننده ستون
     * مثلا میخواهیم برای مقادیر صفر در ستون کلمه خیر بیاریم و برای مقادیر یک در ستون کلمه بلی بیاریم
     */
    private CustomFormatter formatter;
    /**
     * تنظیمات ظاهری ستون
     */
    private CustomExcelStyleDto style;

}
