package com.motaharinia.msutility.tools.image;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.motaharinia.msutility.custom.customexception.utility.UtilityException;
import com.motaharinia.msutility.custom.customexception.utility.UtilityExceptionKeyEnum;
import com.motaharinia.msutility.tools.fso.FsoTools;
import org.imgscalr.Scalr;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;


/**
 * @author eng.motahari@gmail.com<br>
 * اینترفیس متدهای ابزاری کار با تصاویر
 */
public interface ImageTools {


    /**
     * این متد یک مسیر دایرکتوری و نام کامل فایل تصویر و ارتفاع و عرض تصویر را از ورودی دریافت میکند و تصویر بندانگشتی فایل تصویر را با طول و عرض داده شده ثبت مینماید
     *
     * @param directoryPath مسیر دایرکتوری
     * @param fileFullName  نام کامل فایل
     * @param height        ارتفاع تصویر
     * @param width         عرض تصویر
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     */
    static void createThumb(@NotNull String directoryPath, @NotNull String fileFullName, @NotNull Integer height, @NotNull Integer width) throws IOException {
        if (ObjectUtils.isEmpty(directoryPath)) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "directoryPath");
        }
        if (ObjectUtils.isEmpty(fileFullName)) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "fileFullName");
        }
        if (ObjectUtils.isEmpty(height)) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "height");
        }
        if (ObjectUtils.isEmpty(width)) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "width");
        }
        if (height < 1) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_ZERO_OR_NEGATIVE, "height:" + height);
        }
        if (width < 1) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_ZERO_OR_NEGATIVE, "width:" + width);
        }
        float z;
        int newWidth ;
        int newHeight ;

        BufferedImage originalImage = ImageIO.read(new File( String.format("%s/%s",directoryPath, fileFullName)));
        if (ObjectUtils.isEmpty(originalImage)) {
            throw new UtilityException(ImageTools.class, UtilityExceptionKeyEnum.IMAGE_ORIGINAL_READ_PROBLEM, "originalImage is null");
        }
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        if ((originalWidth <= width) && (originalHeight <= height)) {
            newHeight = originalHeight;
            newWidth = originalWidth;
        } else {
            if ((originalWidth > width) && (originalHeight > height)) {
                if (originalWidth >= originalHeight) {
                    z = (float) width / originalWidth;
                } else {
                    z = (float) height / originalHeight;
                }
            } else if (originalWidth > width) {
                z = (float) width / originalWidth;
            } else {
                z = (float) height / originalHeight;
            }
            newHeight = (int) (originalHeight * z);
            newWidth = (int) (originalWidth * z);
        }
        int imageType = originalImage.getType();
        if (imageType == 0) {
            imageType = BufferedImage.TYPE_INT_RGB;
        }
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, imageType);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        ImageIO.write(resizedImage, FsoTools.getFileExtension(fileFullName), new File(String.format("%s/%s-%s.thumb",directoryPath, fileFullName,width.toString())));
    }



    /**
     * این متد داده آرایه بایت تصویر مبدا و پسوند فایل تصویر مبدا و عرض و طول و تناسب را از ورودی دریافت میکند و داده آرایه بایت تصویر تغییر اندازه شده را خروجی میدهد
     *
     * @param originalImageBytes داده آرایه بایت تصویر مبدا
     * @param originalExt        پسوند فایل تصویر مبدا
     * @param destWidth          عرض تصویر مورد نظر
     * @param destHeight         طول تصویر مورد نظر
     * @return خروجی:  داده آرایه بایت تصویر تغییر اندازه شده
     * @throws IOException خطا
     * @throws ImageProcessingException خطا
     * @throws MetadataException خطا
     */
    static byte[] imageResize(byte[] originalImageBytes, @NotNull String originalExt, @NotNull Integer destWidth, @NotNull Integer destHeight) throws IOException, ImageProcessingException, MetadataException {
        //زمانی که تصاویر با دوربین موبایل گرفته میشوند داخل ExifIF آنها چرخش تنظیم میشود و ImageIO.read قادر به فهمیدن آن نیست
        //با استفاده از metadata-extractor تنظیمات داخل ExifIf را میخوانیم و در هنگام ریسایز اگر لازم باشد چرخش میدهیم
        Scalr.Rotation imageRotation = getImageRotation(originalImageBytes);

        //تبدیل آرایه بایت فایل به شیی تصویر بافر شده
        BufferedImage mainBufferedImage = ImageIO.read(new ByteArrayInputStream(originalImageBytes));
        //ایجاد شیی تصویر بافر شده با اندازه تصویر ریسایز شده
        BufferedImage resizedBufferedImage = Scalr.resize(mainBufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, destHeight, destWidth, Scalr.OP_ANTIALIAS);
        //اگر بخاطر مشکل خواندن ExifIF توسط ImageIO.read تصویر بعد از ریسایز نیاز به چرخش داشت اینکار انجام شود
        if (imageRotation != null) {
            resizedBufferedImage = Scalr.rotate(resizedBufferedImage, imageRotation);
        }
        BufferedImage resultResizedBufferedImage = new BufferedImage(resizedBufferedImage.getWidth(), resizedBufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        resultResizedBufferedImage.createGraphics().drawImage(resizedBufferedImage, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resultResizedBufferedImage, originalExt, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /**
     * این متد داده آرایه بایت تصویر مبدا و پسوند فایل تصویر مبدا و سایز نهایی عرض یا طول (هر کدام بیشتر باشد لحاظ میشود) را از ورودی دریافت میکند و داده آرایه بایت تصویر تغییر اندازه شده را خروجی میدهد
     *
     * @param originalImageBytes  داده آرایه بایت تصویر مبدا
     * @param originalExt         پسوند فایل تصویر مبدا
     * @param targetSizeWithScale سایز طول/عرض تصویر مورد نظر که به صورت خودکار Scale میشود
     * @return خروجی:  داده آرایه بایت تصویر تغییر اندازه شده
     * @throws IOException خطا
     * @throws ImageProcessingException خطا
     * @throws MetadataException خطا
     */
    static byte[] imageResize(byte[] originalImageBytes, @NotNull String originalExt, @NotNull Integer targetSizeWithScale) throws IOException, ImageProcessingException, MetadataException {
        //زمانی که تصاویر با دوربین موبایل گرفته میشوند داخل ExifIF آنها چرخش تنظیم میشود و ImageIO.read قادر به فهمیدن آن نیست
        //با استفاده از metadata-extractor تنظیمات داخل ExifIf را میخوانیم و در هنگام ریسایز اگر لازم باشد چرخش میدهیم
        Scalr.Rotation imageRotation = getImageRotation(originalImageBytes);

        //تبدیل آرایه بایت فایل به شیی تصویر بافر شده
        BufferedImage mainBufferedImage = ImageIO.read(new ByteArrayInputStream(originalImageBytes));
        //ایجاد شیی تصویر بافر شده با اندازه تصویر ریسایز شده
        BufferedImage resizedBufferedImage = Scalr.resize(mainBufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetSizeWithScale, Scalr.OP_ANTIALIAS);
        //اگر بخاطر مشکل خواندن ExifIF توسط ImageIO.read تصویر بعد از ریسایز نیاز به چرخش داشت اینکار انجام شود
        if (imageRotation != null) {
            resizedBufferedImage = Scalr.rotate(resizedBufferedImage, imageRotation);
        }
        BufferedImage resultResizedBufferedImage = new BufferedImage(resizedBufferedImage.getWidth(), resizedBufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        resultResizedBufferedImage.createGraphics().drawImage(resizedBufferedImage, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resultResizedBufferedImage, originalExt, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /**
     * متد بررسی کننده rotation تصویر بر اساس ExifIf برای تصاویر گرفته شده با دوربین دیجیتال
     *
     * @param originalImageBytes آرایه بایت تصویر
     * @return خروجی: میزان چرخش مورد نیاز
     * @throws IOException              خطا
     * @throws ImageProcessingException خطا
     * @throws MetadataException        خطا
     */
    static Scalr.Rotation getImageRotation(byte[] originalImageBytes) throws IOException, ImageProcessingException, MetadataException {
        Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(originalImageBytes));
        Collection<ExifIFD0Directory> exifIFD0Collection = metadata.getDirectoriesOfType(ExifIFD0Directory.class);
        if (!ObjectUtils.isEmpty(exifIFD0Collection)) {
            //تصویر با دوربین دیجیتال گرفته شده است
            ExifIFD0Directory exifIFD0 = exifIFD0Collection.iterator().next();
            if (exifIFD0 != null) {
                try {
                    int orientation = exifIFD0.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                    switch (orientation) {
                        case 1: // [Exif IFD0] Orientation - Top, left side (Horizontal / normal)
                            return null;
                        case 6: // [Exif IFD0] Orientation - Right side, top (Rotate 90 CW)
                            return Scalr.Rotation.CW_90;
                        case 3: // [Exif IFD0] Orientation - Bottom, right side (Rotate 180)
                            return Scalr.Rotation.CW_180;
                        case 8: // [Exif IFD0] Orientation - Left side, bottom (Rotate 270 CW)
                            return Scalr.Rotation.CW_270;
                        default:
                            return null;
                    }
                } catch (Exception exception) {
                    //تصویر با دوربین دیجیتال گرفته شده ولی تگ ORIENTATION ندارد
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


}
