package com.motaharinia.msutility.tools.excel;

import com.motaharinia.msutility.tools.excel.dto.*;
import com.motaharinia.msutility.tools.zip.ZipTools;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس ابزارهای مربوط به اکسل
 */
public interface ExcelTools {
    /**
     * متد تولید شیی کتاب اکسل
     *
     * @param excelDto مدل اطلاعات و تنظیمات تولید اکسل
     * @return خروجی: شیی کتاب اکسل
     */
    @NotNull
    static XSSFWorkbook generate(@NotNull CustomExcelDto excelDto) {

        //ساخت شیی اکسل و صفحه اکسل داخل آن
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet worksheet = workbook.createSheet(excelDto.getSheetTitle());
        worksheet.setRightToLeft(excelDto.getSheetRightToLeft());

        //تعریف متغیرها
        XSSFRow row;
        XSSFCell cell;
        XSSFCellStyle style;
        int rowIndex = 0;


        //اگر نیاز به وجود عنوان سربرگ بود
        if (!ObjectUtils.isEmpty(excelDto.getCaptionDto())) {
            //متغیرهای مربوط به سطر سربرگ
            style = makeStyle(workbook, excelDto.getCaptionDto().getStyle());
            //تنظیم سطر اکسل به عنوان سطر سربرگ
            row = worksheet.createRow(rowIndex++);
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(excelDto.getCaptionDto().getTitle());
            worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelDto.getColumnList().size() - 1));
        }

        //اگر نیاز به وجود عناوین ستونها بود
        if (!ObjectUtils.isEmpty(excelDto.getColumnHeaderList())) {
            //متغیرهای مربوط به سطر عناوین ستونها
            int columnHeaderIndex = 0;
            //تنظیم سطر اکسل به عنوان سطر عناوین ستونها
            row = worksheet.createRow(rowIndex++);
            for (CustomExcelColumnHeaderDto dto : excelDto.getColumnHeaderList()) {
                style = makeStyle(workbook, dto.getStyle());
                cell = row.createCell(columnHeaderIndex++);
                cell.setCellStyle(style);
                cell.setCellValue(dto.getTitle());
            }
        }


        //متغیرهای مربوط به سطرهای داده
        CustomExcelColumnDto customExcelColumnDto;
        CustomFormatter formatter;
        style = makeStyle(workbook, new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", false, Color.BLACK, Color.WHITE, BorderStyle.THIN, Color.BLACK, "General"));
        for (Object[] dataColumnArray : excelDto.getRowList()) {
            row = worksheet.createRow(rowIndex++);
            for (int columnIndex = 0; columnIndex < dataColumnArray.length; columnIndex++) {
                formatter=null;
                if (!ObjectUtils.isEmpty(excelDto.getColumnList()) && excelDto.getColumnList().size() > columnIndex) {
                    customExcelColumnDto = excelDto.getColumnList().get(columnIndex);
                    style = makeStyle(workbook, customExcelColumnDto.getStyle());
                    formatter = customExcelColumnDto.getFormatter();
                }
                cell = row.createCell(columnIndex);
                cell.setCellStyle(style);
                setCellValue(cell,dataColumnArray[columnIndex],formatter);
            }
        }

        //تنظیم عرض خودکار روی صفحه اکسل که داده داخل آن پر شده است
        for (int columnIndex = 0; columnIndex < excelDto.getColumnList().size(); columnIndex++) {
            worksheet.autoSizeColumn(columnIndex);
        }


        return workbook;
    }


    /**
     * متد ایجاد و فشرده سازی فایل های اکسل
     *
     * @param excelDto مدل اطلاعات و تنظیمات تولید اکسل
     * @param rowCount تعداد سطر هر فایل
     * @param password رمز فایل زیپ
     * @param zipName  اسم فایل زیپ
     * @return خروجی: آرایه بایت
     */
    static byte[] generateBatch(@NotNull CustomExcelDto excelDto, @NotNull Integer rowCount, @NotNull String password, @NotNull String zipName) throws IOException {
        //نام پوشه برای ذخیره موقت فایل ها
        String tempFolder = System.getProperty("java.io.tmpdir");
        //تعداد کل فایل هایی که باید تولید شود
        int batchSize = excelDto.getRowList().size() / rowCount;
        //مسیر هر فایل تولید شده
        List<String> paths = new ArrayList<>();
        //شماره آخرین سطر آخرین اکسل تولید شده
        int lastPosition = 0;
        for (int i = 0; i <= batchSize; i++) {


            //ساخت شیی اکسل و صفحه اکسل داخل آن
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet worksheet = workbook.createSheet(excelDto.getSheetTitle());
            worksheet.setRightToLeft(excelDto.getSheetRightToLeft());

            //تعریف متغیرها
            XSSFRow row;
            XSSFCell cell;
            XSSFCellStyle style;
            int rowIndex = 0;


            //اگر نیاز به وجود عنوان سربرگ بود
            if (!ObjectUtils.isEmpty(excelDto.getCaptionDto())) {
                //متغیرهای مربوط به سطر سربرگ
                style = makeStyle(workbook, excelDto.getCaptionDto().getStyle());
                //تنظیم سطر اکسل به عنوان سطر سربرگ
                row = worksheet.createRow(rowIndex++);
                cell = row.createCell(0);
                cell.setCellStyle(style);
                cell.setCellValue(excelDto.getCaptionDto().getTitle());
                worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelDto.getColumnList().size() - 1));
            }

            //اگر نیاز به وجود عناوین ستونها بود
            if (!ObjectUtils.isEmpty(excelDto.getColumnHeaderList())) {
                //متغیرهای مربوط به سطر عناوین ستونها
                int columnHeaderIndex = 0;
                //تنظیم سطر اکسل به عنوان سطر عناوین ستونها
                row = worksheet.createRow(rowIndex++);
                for (CustomExcelColumnHeaderDto dto : excelDto.getColumnHeaderList()) {
                    style = makeStyle(workbook, dto.getStyle());
                    cell = row.createCell(columnHeaderIndex++);
                    cell.setCellStyle(style);
                    cell.setCellValue(dto.getTitle());
                }
            }


            //متغیرهای مربوط به سطرهای داده
            CustomExcelColumnDto customExcelColumnDto;
            CustomFormatter formatter;
            style = makeStyle(workbook, new CustomExcelStyleDto(HorizontalAlignment.CENTER, "Tahoma", false, Color.BLACK, Color.WHITE, BorderStyle.THIN, Color.BLACK, "General"));
            //ایجاد فایل اکسل از پوزیشن آخرین سطر اکسل آخر اگر تعداد سطر درخواستی به علاوه آخرین پوزیشن بیشتر از سایز دیتا نباشد
            int rowSize = (Math.min((lastPosition + rowCount), excelDto.getRowList().size()));
            for (int j = lastPosition; j < rowSize; j++) {
                Object[] dataColumnArray = excelDto.getRowList().get(j);
                row = worksheet.createRow(rowIndex++);
                for (int columnIndex = 0; columnIndex < dataColumnArray.length; columnIndex++) {
                    formatter=null;
                    if (!ObjectUtils.isEmpty(excelDto.getColumnList()) && excelDto.getColumnList().size() > columnIndex) {
                        customExcelColumnDto = excelDto.getColumnList().get(columnIndex);
                        style = makeStyle(workbook, customExcelColumnDto.getStyle());
                        formatter = customExcelColumnDto.getFormatter();
                    }
                    cell = row.createCell(columnIndex);
                    cell.setCellStyle(style);
                    setCellValue(cell,dataColumnArray[columnIndex],formatter);
                }
            }

            //تنظیم عرض خودکار روی صفحه اکسل که داده داخل آن پر شده است
            for (int columnIndex = 0; columnIndex < excelDto.getColumnList().size(); columnIndex++) {
                worksheet.autoSizeColumn(columnIndex);
            }
            //ذخیره فایل و اضافه کردن مسیر آن به لیست مسیرها
            String path = (tempFolder + lastPosition + "_" + rowSize + ".xlsx");
            saveTempExcel(path, workbook);
            paths.add(path);
            lastPosition += rowCount;
        }

        ZipTools.zip(paths, tempFolder + zipName.concat(".zip"), CompressionMethod.DEFLATE, CompressionLevel.MAXIMUM, password, EncryptionMethod.AES, AesKeyStrength.KEY_STRENGTH_256);

        return FileUtils.readFileToByteArray(new File(tempFolder + zipName.concat(".zip")));
    }

    /**
     * این متد با دریافت مدل تنظیمات ظاهری شیی استایل اکسل را ایجاد میکند
     *
     * @param workbook            شیی کتاب اکسل
     * @param customExcelStyleDto مدل تنظیمات ظاهری
     * @return خروجی: شیی استایل اکسل
     */
    private static XSSFCellStyle makeStyle(XSSFWorkbook workbook, CustomExcelStyleDto customExcelStyleDto) {
        XSSFFont styleFont = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        //قلم
        styleFont.setBold(customExcelStyleDto.getFontIsBold());
        styleFont.setFontName(customExcelStyleDto.getFontName());
        styleFont.setColor(new XSSFColor(customExcelStyleDto.getFontColor()));
        //ظاهر
        style.setFont(styleFont);
        style.setAlignment(customExcelStyleDto.getAlignment());
        style.setFillForegroundColor(new XSSFColor(customExcelStyleDto.getBackgroundColor()));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(customExcelStyleDto.getBorderStyle());
        style.setBorderRight(customExcelStyleDto.getBorderStyle());
        style.setBorderLeft(customExcelStyleDto.getBorderStyle());
        style.setBorderTop(customExcelStyleDto.getBorderStyle());
        style.setDataFormat(dataFormat.getFormat(customExcelStyleDto.getDataFormat()));

        return style;
    }

    /**
     * متد ذخیره فایل اکسل به صورت موقت
     *
     * @param filePath مسیر فایل
     * @param workbook شی اکسل
     */
    private static void saveTempExcel(@NotNull String filePath, @NotNull XSSFWorkbook workbook) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }


    private static void setCellValue(XSSFCell cell,Object value,CustomFormatter formatter){
        if (formatter!=null) {
            cell.setCellValue(formatter.format(value));
        } else {
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Float) {
                cell.setCellValue((Float) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof BigInteger) {
                cell.setCellValue(((BigInteger) value).doubleValue() );
            } else if (value instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) value).doubleValue() );
            } else {
                cell.setCellValue(value + "");
            }
        }
    }

}
