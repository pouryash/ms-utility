package com.motaharinia.msutility.tools.string;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.junit.jupiter.api.*;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس تست StringTools
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StringToolsUnitTest {
    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void beforeEach() {
        Locale.setDefault(Locale.US);
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
    void generateMD5HashTest() {
        try {
            assertThat(StringTools.generateMD5Hash("Mostafa Motaharinia", 20)).isNotBlank();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(2)
    @Test
    void removeHtmlTest() {
        try {
            assertThat(StringTools.removeHtml("Mostafa<br>Motaharinia").contains("<br>")).isNotEqualTo(true);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(3)
    @Test
    void summarizeStringTest() {
        try {
            assertThat(StringTools.summarizeString("Hello Mostafa Motaharinia", 20)).isEqualTo("Hello Mostafa Mot...");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(4)
    @Test
    void highlightTest() {
        try {
            assertThat(StringTools.highlight("Hello Mostafa Motaharinia", "Hello")).isEqualTo("<span class='highlight'>Hello</span> Mostafa Motaharinia");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(5)
    @Test
    void generateRandomStringCharacterAllTest() {
        try {
            assertThat(StringTools.generateRandomString(RandomGenerationTypeEnum.LATIN_CHARACTERS, 5, false)).matches(CustomValidationRegularExceptionEnum.LATIN_CHARACTERS.getValue());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(6)
    @Test
    void generateRandomStringNumberTest() {
        try {
            assertThat(StringTools.generateRandomString(RandomGenerationTypeEnum.NUMBER, 5, false)).matches(CustomValidationRegularExceptionEnum.NUMERIC_VALUE.getValue());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(7)
    @Test
    void generateRandomStringCharacterNumberTest() {
        try {
            assertThat(StringTools.generateRandomString(RandomGenerationTypeEnum.LATIN_CHARACTERS_NUMBERS, 5, false)).matches(CustomValidationRegularExceptionEnum.LATIN_CHARACTERS_NUMBERS.getValue());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(8)
    @Test
    void generateRandomStringCharacterPersianSpaceTest() {
        try {
            assertThat(StringTools.generateRandomString(RandomGenerationTypeEnum.PERSIAN_CHARACTERS_SPACE, 10, false)).matches(CustomValidationRegularExceptionEnum.PERSIAN_CHARACTERS_SPACE.getValue());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(9)
    @Test
    void generateRandomStringCharacterPersianSpaceNumberTest() {
        try {
            System.out.println(StringTools.generateRandomString(RandomGenerationTypeEnum.PERSIAN_CHARACTERS_SPACE_NUMBERS, 5, false));
            assertThat(StringTools.generateRandomString(RandomGenerationTypeEnum.PERSIAN_CHARACTERS_SPACE_NUMBERS, 10, false)).matches(CustomValidationRegularExceptionEnum.PERSIAN_CHARACTERS_SPACE_NUMBERS.getValue());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }
}
