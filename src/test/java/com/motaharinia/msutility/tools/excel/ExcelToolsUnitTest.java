package com.motaharinia.msutility.tools.excel;

import com.motaharinia.msutility.tools.excel.dto.ExcelColumnDto;
import com.motaharinia.msutility.tools.excel.dto.ExcelDto;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس تست ExcelTools
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExcelToolsUnitTest {


    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void beforeEach() {
        Locale.setDefault(new Locale("fa", "IR"));
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void afterEach() {
        Locale.setDefault(Locale.US);
    }

    @Order(1)
    @Test
    void excelExportTest() {
        try {

            //فرنت کننده برای فیلدهای boolean
            HashMap<Object,Object> formatterMap = new HashMap<>();
            formatterMap.put(true,"بلی");
            formatterMap.put(false,"خیر");

            //عناوین و مشخصات ستونهای اکسل
            List<ExcelColumnDto> columnList = new ArrayList<>();
            columnList.add(new ExcelColumnDto("نام", HorizontalAlignment.RIGHT,false,null));
            columnList.add(new ExcelColumnDto("نام خانوادگی", HorizontalAlignment.RIGHT,false, null));
            columnList.add(new ExcelColumnDto("دریافت خبرنامه؟", HorizontalAlignment.CENTER,false,formatterMap));
            columnList.add(new ExcelColumnDto("امتیاز", HorizontalAlignment.LEFT,true, null));
            columnList.add(new ExcelColumnDto("تعداد گردش", HorizontalAlignment.LEFT,true, null));
            columnList.add(new ExcelColumnDto("معدل", HorizontalAlignment.LEFT,true, null));
            columnList.add(new ExcelColumnDto("ضریب محاسبه", HorizontalAlignment.LEFT,true, null));
            columnList.add(new ExcelColumnDto("تعداد مراجعه", HorizontalAlignment.LEFT,true, null));
            columnList.add(new ExcelColumnDto("مبلغ موجودی", HorizontalAlignment.LEFT,true, null));

            //سطرهای اکسل
            List<Object[]> rowList = new ArrayList<>();
            rowList.add(new Object[]{"مصطفی","مطهری نیا",true,10000,1000000L,1.5f,1.25d, BigInteger.valueOf(1000000000L), BigDecimal.valueOf(150000)});
            rowList.add(new Object[]{"احمد","کریمی راد",false,20000,2000000L,2.5f,2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});

            //تبدیل
            ExcelDto excelDto = new ExcelDto(rowList,columnList);
            XSSFWorkbook workbook = ExcelTools.generate(excelDto, "اطلاعات کاربران سامانه","Tahoma");
            FileOutputStream fileOutputStream = new FileOutputStream("excelExportTest.xlsx");
            workbook.write(fileOutputStream);
            assertThat(workbook).isNotNull();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


}
