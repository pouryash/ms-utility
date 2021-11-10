package com.motaharinia.msutility.tools.excel;

import com.motaharinia.msutility.tools.excel.dto.UserExcelDto;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    void generateTest() {
        try {

            //سطرهای تستی جهت تبدیل به اکسل
            List<Object[]> rowList = new ArrayList<>();
            rowList.add(new Object[]{"مصطفی", "مطهری نیا", true, 10000, 1000000L, 1.5f, 1.25d, BigInteger.valueOf(1000000000L), BigDecimal.valueOf(150000)});
            rowList.add(new Object[]{"احمد", "کریمی راد", false, 20000, 2000000L, 2.5f, 2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});

            //تولید اکسل
            XSSFWorkbook workbook = ExcelTools.generate(new UserExcelDto(rowList));
            assertThat(workbook).isNotNull();
            String fileName="ExcelToolsUnitTest_generateTest.xlsx";
            Files.createDirectories(Paths.get("/test_converted"));
            File file = new File("/test_converted/"+fileName);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file,false);
            workbook.write(fileOutputStream);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(2)
    @Test
    void generateBatchTest() {
        try {

            //سطرهای تستی جهت تبدیل به اکسل
            List<Object[]> rowList = new ArrayList<>();
            rowList.add(new Object[]{"مصطفی", "مطهری نیا", true, 10000, 1000000L, 1.5f, 1.25d, BigInteger.valueOf(1000000000L), BigDecimal.valueOf(150000)});
            rowList.add(new Object[]{"احمد", "کریمی راد", false, 20000, 2000000L, 2.5f, 2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});
            rowList.add(new Object[]{"احمد", "کریمی راد", false, 20000, 2000000L, 2.5f, 2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});
            rowList.add(new Object[]{"احمد", "کریمی راد", false, 20000, 2000000L, 2.5f, 2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});
            rowList.add(new Object[]{"احمد", "کریمی راد", false, 20000, 2000000L, 2.5f, 2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});
            rowList.add(new Object[]{"احمد", "کریمی راد", false, 20000, 2000000L, 2.5f, 2.25d, BigInteger.valueOf(2000000000L), BigDecimal.valueOf(250000)});

            //تولید اکسل
            String fileName="result.zip";
            byte[] bytes = ExcelTools.generateBatch(new UserExcelDto(rowList), 2, "", fileName);
            assertThat(bytes).isNotEmpty();
            File file = new File("/test_converted/"  + fileName);
            Files.createDirectories(Paths.get("/test_converted"));
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file,false);
            fileOutputStream.write(bytes);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }
}
