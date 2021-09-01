package com.motaharinia.msutility.tools.excel;

import com.motaharinia.msutility.tools.excel.dto.ExcelDto;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس ابزارهای مربوط به اکسل
 */
public interface ExcelTools {

    @NotNull
    static XSSFWorkbook generate(@NotNull ExcelDto excelDto, @NotNull String sheetTitle , @NotNull String fontName) {
        List<Object[]> listData = excelDto.getRowList();

        //ساخت شیی اکسل و صفحه اکسل داخل آن
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet worksheet = workbook.createSheet(sheetTitle);
        //تنظیمات لوکال که راست به چپ باشد یا خیر
        Locale locale = LocaleContextHolder.getLocale();
        if ((locale.getLanguage().equals("fa")) || (locale.getLanguage().equals("ar"))) {
            worksheet.setRightToLeft(true);
        } else {
            worksheet.setRightToLeft(false);
        }
        //متغیرهای مربوط به هدر
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName(fontName);
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(headerFont);
        //متغیرهای مربطو به سطرهای داده
        XSSFFont dataFont = workbook.createFont();
        dataFont.setBold(false);
        dataFont.setFontName(fontName);
        XSSFCellStyle dataStyle;



        //تعریف متغیرها
        XSSFRow row;
        XSSFCell cell;
        int rowIndex = 0;
        int colIndex = 0;
        BigInteger bigIntegerTest = null;
        BigDecimal bigDecimalTest = null;

        //تنظیم سطر اول اکسل به عنوان هدر
        row = worksheet.createRow(rowIndex++);
        for (int i = 0; i < excelDto.getColumnList().size(); i++) {
                cell = row.createCell(colIndex++);

                cell.setCellStyle(headerStyle);
                cell.setCellValue(excelDto.getColumnList().get(i).getTitle());
        }

        //تبدیل اطلاعات سطرها در صفحه اکسل
        for (int i = 0; i < listData.size(); i++) {
            colIndex = 0;
            row = worksheet.createRow(rowIndex++);
            for (int j = 0; j < excelDto.getColumnList().size(); j++) {
                    cell = row.createCell(colIndex++);

                dataStyle = workbook.createCellStyle();
                    dataStyle.setAlignment(excelDto.getColumnList().get(j).getAlignment());
                    dataStyle.setFont(dataFont);
                    cell.setCellStyle(dataStyle);

                if(excelDto.getColumnList().get(j).getFormatterMap()!=null && excelDto.getColumnList().get(j).getFormatterMap().get(listData.get(i)[j])!=null){
                    cell.setCellValue((String) excelDto.getColumnList().get(j).getFormatterMap().get(listData.get(i)[j]));
                }else{
                    if (listData.get(i)[j] instanceof String) {
                        cell.setCellValue((String) listData.get(i)[j]);
                    } else if (listData.get(i)[j] instanceof Boolean) {
                        cell.setCellValue((Boolean) listData.get(i)[j]);
                    } else if (listData.get(i)[j] instanceof Integer) {
                        cell.setCellValue((Integer) listData.get(i)[j]);
                    } else if (listData.get(i)[j] instanceof Long) {
                        cell.setCellValue((Long) listData.get(i)[j]);
                    } else if (listData.get(i)[j] instanceof Float) {
                        cell.setCellValue((Float) listData.get(i)[j]);
                    } else if (listData.get(i)[j] instanceof Double) {
                        cell.setCellValue((Double) listData.get(i)[j]);
                    } else if (listData.get(i)[j] instanceof BigInteger) {
                        bigIntegerTest = (BigInteger) listData.get(i)[j];
                        cell.setCellValue(bigIntegerTest.doubleValue());
                    } else if (listData.get(i)[j] instanceof BigDecimal) {
                        bigDecimalTest = (BigDecimal) listData.get(i)[j];
                        cell.setCellValue(bigDecimalTest.doubleValue());
                    } else {
                        cell.setCellValue(listData.get(i)[j] + "");
                    }
                }
            }
        }

        //تنظیم عرض خودکار روی صفحه اکسل که داده داخل آن پر شده است
        for (int i = 0; i < colIndex; i++) {
            worksheet.autoSizeColumn(i);
        }

        return workbook;
    }
}
