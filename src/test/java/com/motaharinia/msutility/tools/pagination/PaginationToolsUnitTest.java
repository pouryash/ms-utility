package com.motaharinia.msutility.tools.pagination;

import com.motaharinia.msutility.tools.encoding.EncodingTools;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس تست PaginationTools
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaginationToolsUnitTest {

    List<String> allRowList = new ArrayList<>();


    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void beforeEach() {
        Locale.setDefault(new Locale("fa", "IR"));
        allRowList.clear();
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void afterEach()  {
        Locale.setDefault(Locale.US);
    }

    @Order(1)
    @Test
    void paginateListLessThanSizeTest() {
        try {
            allRowList.add("1");
            allRowList.add("2");
            allRowList.add("3");
            allRowList.add("4");
            allRowList.add("5");
            allRowList.add("6");
            allRowList.add("7");
            allRowList.add("8");
            allRowList.add("9");
            allRowList.add("10");
            allRowList.add("11");
            List<String> result = PaginationTools.paginateList(allRowList, 1, 10);
            assertThat(result.size()).isEqualTo(10);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(2)
    @Test
    void paginateListExactSizeTest() {
        try {
            allRowList.add("1");
            allRowList.add("2");
            allRowList.add("3");
            allRowList.add("4");
            allRowList.add("5");
            allRowList.add("6");
            allRowList.add("7");
            allRowList.add("8");
            allRowList.add("9");
            allRowList.add("10");
            List<String> result = PaginationTools.paginateList(allRowList, 1, 10);
            assertThat(result.size()).isEqualTo(10);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(3)
    @Test
    void paginateListMoreThanSizeTest() {
        try {
            allRowList.add("1");
            allRowList.add("2");
            allRowList.add("3");
            allRowList.add("4");
            allRowList.add("5");
            allRowList.add("6");
            allRowList.add("7");
            allRowList.add("8");
            allRowList.add("9");
            List<String> result = PaginationTools.paginateList(allRowList, 1, 10);
            assertThat(result.size()).isEqualTo(9);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }
}
