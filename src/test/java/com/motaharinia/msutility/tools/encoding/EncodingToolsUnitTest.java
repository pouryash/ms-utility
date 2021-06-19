package com.motaharinia.msutility.tools.encoding;

import org.junit.jupiter.api.*;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس تست EncodingTools
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EncodingToolsUnitTest {

    String content = "this is first test";

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
    void afterEach()  {
        Locale.setDefault(Locale.US);
    }

    @Order(1)
    @Test
    void base64EncodeDecodeTest() {
        try {
            assertThat(EncodingTools.base64decode(EncodingTools.base64Encode(content))  ).isEqualTo(content);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

}
