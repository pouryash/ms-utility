package com.motaharinia.msutility.tools.captcha;

import lombok.Data;

import java.awt.*;
import java.io.Serializable;

/**
 * @author https://github.com/motaharinia<br>
 */
@Data
public class CaptchaConfigDto implements Serializable {

    /**
     * عرض تصویر مورد نظر جهت تولید
     */
    private Integer width = 450;
    /**
     * طول تصویر مورد نظر جهت تولید
     */
    private Integer height = 100;


    /**
     * آیا تصویر کپچا تولیدی خط تصادفی هم داشته باشد؟
     */
    private boolean linesIncluded = false;
    /**
     * تعداد خطهای تصادفی جهت تولید
     */
    private Integer linesNumber = 20;
    /**
     * رنگ خطهایی که تصادفی تولید میشوند
     */
    private Color linesColor = new Color(86, 86, 86);


    /**
     * آیا تصویر کپچا تولیدی نقطه تصادفی هم داشته باشد؟
     */
    private boolean dotsIncluded = false;
    /**
     * تعداد نقطه های تصادفی جهت تولید
     */
    private Integer dotsNumber = 1000;
    /**
     * رنگ نقطه هایی که تصادفی تولید میشوند
     */
    private Color dotsColor = new Color(0, 0, 0);


    /**
     * آیا حروف کپچا از چند فونت ایجاد شوند؟
     */
    private boolean fontVarious = false;
    /**
     * ایندکس فایل فونت پیش فرض در ریسورس
     */
    private Integer fontFileDefaultIndex = 12;
    /**
     * تعداد فایلهای فونت موجود در ریسورس
     */
    private Integer fontCount = 13;
    /**
     * اندازه پیکسلی عرض فونت برای جا شدن تعداد حروف در تصویر
     */
    private Integer fontSize = (width / 6) - 10;
    /**
     * رنگ متن پیش فرض حروفی که تصادفی تولید میشوند
     */
    private Color fontDefaultColor = new Color(0, 0, 0);
    /**
     * آیا حروف کپچا رنگی باشند؟
     */
    private boolean fontColorful = false;
    /**
     * اگرحروف کپچا قرار هست رنگی باشند از چه رنگهایی ایجاد شوند
     */
    private Color[] fontColorfulArray = new Color[]{
            new Color(0, 0, 0), //black
            new Color(64, 64, 64), //gray
            new Color(0, 0, 255), //blue
            new Color(255, 0, 0), //red
            new Color(0, 255, 0), //green
            new Color(51, 0, 0), //brown
            new Color(255, 128, 0), //orange
            new Color(102, 0, 102), //purple
    };


    /**
     * رنگ تصویر پس زمینه
     */
    private Color backgroundColor = new Color(250, 250, 250);
    /**
     * آیا تصویر پیش زمینه متغیر باشد؟
     */
    private boolean backgroundImageVarious = true;
    /**
     * ایندکس فایل تصویر پس زمینه پیش فرض در ریسورس
     */
    private Integer backgroundImageFileDefaultIndex = 9;
    /**
     * تعداد فایلهای تصویر پس زمینه موجود در ریسورس
     */
    private Integer backgroundImageCount = 26;


}
