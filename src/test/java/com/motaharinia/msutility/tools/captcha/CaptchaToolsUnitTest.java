package com.motaharinia.msutility.tools.captcha;

import org.junit.jupiter.api.*;

import java.awt.image.BufferedImage;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس تست CaptchaTools
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CaptchaToolsUnitTest {


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
    void generateCaptchaTest() {
        try {
            //تست ایجاد تصویر کپچا از روی تنظیمات پیش فرض
            CaptchaConfigDto captchaConfigDto = new CaptchaConfigDto();
            BufferedImage bufferedImage = CaptchaTools.generateCaptcha(captchaConfigDto, "123456", BufferedImage.TYPE_INT_RGB);
            assertThat(bufferedImage).isNotNull();
            assertThat(bufferedImage.getWidth()).isEqualTo(captchaConfigDto.getWidth());
            assertThat(bufferedImage.getHeight()).isEqualTo(captchaConfigDto.getHeight());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


}
