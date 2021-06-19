package com.motaharinia.msutility.tools.image;

import com.motaharinia.msutility.tools.fso.FsoConfigDto;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس تست ImageTools
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImageToolsUnitTest {

    String parentDirPath = "/MsUtilityTests";
    String imageFileFullName = "imageTools.jpg";

    FsoConfigDto fsoConfigDto = new FsoConfigDto(new Integer[]{60, 120}, "thumb", 100);

    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void beforeEach() throws IOException {
        File parentDir = new File(parentDirPath);
        if (parentDir.exists()) {
            FileUtils.deleteDirectory(parentDir);
        }
        FileUtils.forceMkdir(parentDir);

        final BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, 200, 200);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.drawOval(0, 0, 200, 200);
        graphics2D.dispose();
        ImageIO.write(image, "JPEG", new File(parentDirPath + "/" + imageFileFullName));

        Locale.setDefault(new Locale("fa", "IR"));
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void afterEach() throws IOException {
        Locale.setDefault(Locale.US);
        File parentDir = new File(parentDirPath);
        if (parentDir.exists()) {
            FileUtils.deleteDirectory(parentDir);
        }
    }

    @Order(1)
    @Test
    void createThumbTest() {
        try {
            //تست ایجاد تصویر بندانگشتی از فایل تصویر موجود
            ImageTools.createThumb(parentDirPath, imageFileFullName, fsoConfigDto.getThumbSizeArray()[0],  fsoConfigDto.getThumbSizeArray()[0]);
            File file = new File(parentDirPath + "/" + imageFileFullName + "-" +  fsoConfigDto.getThumbSizeArray()[0] + ".thumb");
            assertThat(file).exists();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(2)
    @Test
    void imageResizeTest() {
        try {
            File sourceFile = new File(parentDirPath + "/" + imageFileFullName);
           byte[] resizedByteArray=  ImageTools.imageResize(FileUtils.readFileToByteArray(sourceFile),"jpg",100,100);
            FileUtils.writeByteArrayToFile(new File(parentDirPath + "/imgaeToolsResized.jpg"), resizedByteArray);
            File file = new File(parentDirPath + "/imgaeToolsResized.jpg");
            assertThat(file).exists();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

}
