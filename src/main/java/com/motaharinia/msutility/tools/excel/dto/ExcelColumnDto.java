/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motaharinia.msutility.tools.excel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;
import java.util.HashMap;

/**
 *این کلاس اطلاعاتی از هر یک از ستونهای گرید ارائه میکند
 * @author Administrator
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelColumnDto implements Serializable {

    /**
     * عنوان ستون
     */
    private String title;
    /**
     * جهت مقدار در ستون
     */
    private HorizontalAlignment alignment = HorizontalAlignment.CENTER;
    /**
     * نوع ستون که عددی یا متنی است
     */
    private Boolean isNumeric;
    /**
     * فرمت کننده ستون
     * مثلا میخواهیم برای مقادیر صفر در ستون کلمه خیر بیاریم و برای مقادیر یک در ستون کلمه بلی بیاریم
     */
    private HashMap<Object,Object> formatterMap;

}
