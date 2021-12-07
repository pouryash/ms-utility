package com.motaharinia.msutility.tools.captcha;

import com.motaharinia.msutility.custom.customexception.utility.UtilityException;
import com.motaharinia.msutility.custom.customexception.utility.UtilityExceptionKeyEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس کپچا ابزارهای لازم برای ساخت تصویر کپچای تصادفی را ارائه مینماید
 */
public interface CaptchaTools {


    Random RANDOM = new Random();

    /**
     * ایت متد کد اتفاقی و مسیر پیش فرض و نوع تصویر مورد نظر را از ورودی دریافت میکند و یک تصویر کپچا مطابق با آنها خروجی میدهد
     *
     * @param captchaConfigDto مدل تنظیمات تولید کپچا
     * @param code               کد اتفاقی
     * @param imageType          BufferedImage.TYPE_INT_** (Default:BufferedImage.TYPE_INT_RGB)
     * @return خروجی: تصویر کپچا
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     * @throws FontFormatException این متد ممکن است اکسپشن داشته باشد
     */
    @NotNull
    static BufferedImage generateCaptcha(@NotNull CaptchaConfigDto captchaConfigDto, @NotNull String code, @NotNull Integer imageType) throws IOException, FontFormatException {
        if (ObjectUtils.isEmpty(code)) {
            throw new UtilityException(CaptchaTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "code");
        }
        if (ObjectUtils.isEmpty(imageType)) {
            throw new UtilityException(CaptchaTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "imageType");
        }
        InputStream inputStream;

        int fontIndex;
        if (captchaConfigDto.isFontVarious()) {
            fontIndex = RANDOM.nextInt(captchaConfigDto.getFontCount()) + 1;
        } else {
            fontIndex = captchaConfigDto.getFontFileDefaultIndex();
        }

        String fontFilePath="static/captcha/font/" + fontIndex + ".ttf";
        inputStream = CaptchaTools.class.getClassLoader().getResourceAsStream(fontFilePath);
        if(ObjectUtils.isEmpty(inputStream) || inputStream.available()<=0){
            throw new UtilityException(CaptchaTools.class, UtilityExceptionKeyEnum.CAPTCHA_FONT_FILE_IS_NOT_EXIST, fontFilePath);
        }
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(getFontSize(fontIndex));

        BufferedImage img = new BufferedImage(captchaConfigDto.getWidth(), captchaConfigDto.getHeight(), imageType);
        Graphics2D g = img.createGraphics();

        g.setColor(captchaConfigDto.getBackgroundColor());
        g.fillRect(0, 0, captchaConfigDto.getWidth(), captchaConfigDto.getHeight());

        //انتخاب تصویر اتفاقی پس زمینه
        int backgroundIndex;
        if (captchaConfigDto.isBackgroundImageVarious()) {
            backgroundIndex = RANDOM.nextInt(captchaConfigDto.getBackgroundImageCount()) + 1;
        } else {
            backgroundIndex = captchaConfigDto.getBackgroundImageFileDefaultIndex();
        }
        String backgroundFilePath="static/captcha/background/" + backgroundIndex + ".png";
        inputStream = CaptchaTools.class.getClassLoader().getResourceAsStream(backgroundFilePath);
        if(ObjectUtils.isEmpty(inputStream) || inputStream.available()<=0){
            throw new UtilityException(CaptchaTools.class, UtilityExceptionKeyEnum.CAPTCHA_BACKGROUND_FILE_IS_NOT_EXIST, backgroundFilePath);
        }
        BufferedImage back = ImageIO.read(inputStream);
        g.drawImage(back, 0, 0, captchaConfigDto.getWidth(), captchaConfigDto.getHeight(), null);

        //تولید متن کپچا
        g.setColor(captchaConfigDto.getFontDefaultColor());
        g.setFont(customFont);
        int angle;
        int textPositionX = 30;
        int textPositionY = (int) (captchaConfigDto.getHeight() / 1.5);
        for (int i = 0; i < code.length(); i++) {
            angle = RANDOM.nextInt(30 + 30) - 30;
            AffineTransform original = g.getTransform();
            Font theFont = g.getFont();
            original.rotate(angle * Math.PI / 180);
            Font theDerivedFont = theFont.deriveFont(original);
            g.setFont(theDerivedFont);
            if (captchaConfigDto.isFontColorful()) {
                g.setColor(captchaConfigDto.getFontColorfulArray()[RANDOM.nextInt(8)]);
            }
            g.drawString(code.charAt(i) + "", textPositionX, textPositionY);
            g.setFont(theFont);
            textPositionX += captchaConfigDto.getFontSize() + 5;
        }

        //تولید خطهای تصادفی
        if (captchaConfigDto.isLinesIncluded()) {
            int rand;
            int dest;
            int[] sourceCordinates;
            int[] destCordinates;
            g.setColor(captchaConfigDto.getLinesColor());
            for (int i = 0; i < captchaConfigDto.getLinesNumber(); i++) {
                rand = RANDOM.nextInt(4) + 1;
                sourceCordinates = getCoordinates(rand, captchaConfigDto.getWidth(), captchaConfigDto.getHeight());
                dest = rand + RANDOM.nextInt(3) + 1;
                destCordinates = getCoordinates(dest, captchaConfigDto.getWidth(), captchaConfigDto.getHeight());
                g.drawLine(sourceCordinates[0], sourceCordinates[1], destCordinates[0], destCordinates[1]);
            }
        }

        //تولید نقطه های تصادفی
        if (captchaConfigDto.isDotsIncluded()) {
            int randomX;
            int randomY;
            g.setColor(captchaConfigDto.getDotsColor());
            for (int i = 0; i < captchaConfigDto.getDotsNumber(); i++) {
                randomX = RANDOM.nextInt(captchaConfigDto.getWidth());
                randomY = RANDOM.nextInt(captchaConfigDto.getHeight());
                g.drawLine(randomX, randomY, randomX + 1, randomY + 1);
            }
        }
        return img;
    }


    /**
     * متدی که مقدار عددی لبه بالا(1) یا راست(2) یا پایین(3) یا چپ(4) را دریافت میکند و بر اساس طول و عرض تصویر کپچا مختصات نقطه ای اتفاقی را در لبه مورد نظر خروجی میدهد
     *
     * @param direction مقدار عددی لبه بالا(1) یا راست(2) یا پایین(3) یا چپ(4)
     * @param width     عرض تصویر مورد نظر جهت تولید
     * @param height    طول تصویر مورد نظر جهت تولید
     * @return خروجی: مختصات نقطه ای اتفاقی در لبه مورد نظر
     */
    static int[] getCoordinates(@NotNull Integer direction, @NotNull Integer width, @NotNull Integer height) {
        if (ObjectUtils.isEmpty(direction)) {
            throw new UtilityException(CaptchaTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "direction");
        }
        int[] coordinates = new int[2];
        switch (direction) {
            case 1: //لبه بالای مستطیل
                coordinates[0] = RANDOM.nextInt(width);
                break;

            case 2: //لبه راست مستطیل
                coordinates[0] = width;
                coordinates[1] = RANDOM.nextInt(height);
                break;

            case 3: //لبه پایین مستطیل
                coordinates[0] = RANDOM.nextInt(width);
                coordinates[1] = height;
                break;

            case 4: //لبه چپ مستطیل
                coordinates[1] = RANDOM.nextInt(height);
                break;
            default:
                break;
        }
        return coordinates;
    }


    /**
     * متدی که طبق اندیس فایل فونت موجود در ریسورس اندازه قلم متناسب با آن فابل فونت را خروجی میدهد
     *
     * @param fontIndex اندیس فایل فونت موجود در ریسورس
     * @return خروجی: اندازه قلم متناسب با فابل فونت
     */
    @NotNull
    static Float getFontSize(@NotNull Integer fontIndex) {
        float result = 0f;
        switch (fontIndex) {
            case 1:
                result = 50f;
                break;
            case 2:
                result = 60f;
                break;
            case 3:
                result = 100f;
                break;
            case 4:
                result = 60f;
                break;
            case 5:
                result = 60f;
                break;
            case 6:
                result = 50f;
                break;
            case 7:
                result = 60f;
                break;
            case 8:
                result = 50f;
                break;
            case 9:
                result = 50f;
                break;
            case 10:
                result = 60f;
                break;
            case 11:
                result = 50f;
                break;
            case 12:
                result = 70f;
                break;
            case 13:
                result = 50f;
                break;
            default:
                break;
        }
        return result;
    }


}
