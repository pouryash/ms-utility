package com.motaharinia.msutility.tools.excel.dto;

import com.motaharinia.msutility.tools.calendar.CalendarTools;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.awt.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس پیاده سازی مدل اطلاعات و تنظیمات تولید اکسل کاربران
 */
public class UserExcelDto implements CustomExcelDto, Serializable {

    /**
     * شیی تنظیمات ظاهری
     */
    private CustomExcelStyleDto customExcelStyleDto;

    /**
     * لیست سطرهای داده
     */
    private List<Object[]> rowList;

    /**
     * متد سازنده
     *
     * @param rowList لیست سطرهای داده
     */
    public UserExcelDto(List<Object[]> rowList) {
        this.rowList = rowList;
    }

    /**
     * @return خروجی: عنوان صفحه اکسل
     */
    @Override
    public String getSheetTitle() {
        return "اطلاعات کاربران سامانه";
    }

    /**
     * @return خروجی: جهت راست به چپ صفحه اکسل
     */
    @Override
    public Boolean getSheetRightToLeft() {
        return true;
    }

    /**
     * @return خروجی:  عنوان سربرگ اکسل
     */
    @Override
    public CustomExcelCaptionDto getCaptionDto() {
        //عنوان سربرگ اکسل
        customExcelStyleDto = new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", true, Color.WHITE, new Color(198, 0, 199), BorderStyle.THIN, Color.BLACK, "General");
        return new CustomExcelCaptionDto("گزارش تیرماه اطلاعات کاربران", customExcelStyleDto);
    }

    /**
     * @return خروجی:  لیستی از عناوین ستونهای اکسل را در خود دارد
     */
    @Override
    public List<CustomExcelColumnHeaderDto> getColumnHeaderList() {
        //عناوین ستونهای اکسل
        customExcelStyleDto = new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", true, Color.BLACK, new Color(49, 204, 206), BorderStyle.THIN, Color.BLACK, "General");
        List<CustomExcelColumnHeaderDto> columnHeaderList = new ArrayList<>();
        columnHeaderList.add(new CustomExcelColumnHeaderDto("نام", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("نام خانوادگی", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("امتیاز", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("تعداد گردش", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("معدل", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("ضریب محاسبه", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("تعداد مراجعه", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("مبلغ موجودی", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("دریافت خبرنامه؟", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("تاریخ ثبت", customExcelStyleDto));
        columnHeaderList.add(new CustomExcelColumnHeaderDto("تاریخ و زمان ویرایش", customExcelStyleDto));
        return columnHeaderList;
    }

    /**
     * @return خروجی:  لیستی از تنظمیات ستونهای اکسل را در خود دارد
     */
    @Override
    public List<CustomExcelColumnDto> getColumnList() {
        //فرمت کننده برای فیلدهای boolean
        CustomFormatter booleanFormatter = value -> ((Boolean) value) ? "بلی" : "خیر";
        //فرمت کننده برای فیلدهای long epochmili با خروجی رشته تاریخ
        CustomFormatter epochDateFormatter = value -> CalendarTools.gregorianToJalaliString(Instant.ofEpochMilli((Long) value), "/", false);
        //فرمت کننده برای فیلدهای long epochmili با خروجی رشته تاریخ و زمان
        CustomFormatter epochDateTimeFormatter = value -> CalendarTools.gregorianToJalaliString(Instant.ofEpochMilli((Long) value), "/", true);


        //تنظیمات ستونهای اکسل
        customExcelStyleDto = new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", false, Color.BLACK, Color.WHITE, BorderStyle.THIN, Color.BLACK, "General");
        CustomExcelStyleDto customExcelStyleDtoNumeric = new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", false, Color.BLUE, Color.WHITE, BorderStyle.THIN, Color.BLACK, "#,##0");
        CustomExcelStyleDto customExcelStyleDtoNumericFloat = new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", false, Color.BLUE, Color.WHITE, BorderStyle.THIN, Color.BLACK, "#,##0.00");
        List<CustomExcelColumnDto> columnList = new ArrayList<>();
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDto));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDto));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDtoNumeric));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDtoNumeric));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDtoNumericFloat));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDtoNumericFloat));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDtoNumeric));
        columnList.add(new CustomExcelColumnDto(null, customExcelStyleDtoNumeric));
        columnList.add(new CustomExcelColumnDto(booleanFormatter, customExcelStyleDto));
        columnList.add(new CustomExcelColumnDto(epochDateFormatter, customExcelStyleDto));
        columnList.add(new CustomExcelColumnDto(epochDateTimeFormatter, customExcelStyleDto));
        return columnList;
    }

    /**
     * @return خروجی: لیست سطرهای داده
     */
    @Override
    public List<Object[]> getRowList() {
        return rowList;
    }
}
