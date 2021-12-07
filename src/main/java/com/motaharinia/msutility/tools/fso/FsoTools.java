package com.motaharinia.msutility.tools.fso;


import com.motaharinia.msutility.custom.customexception.utility.UtilityException;
import com.motaharinia.msutility.custom.customexception.utility.UtilityExceptionKeyEnum;
import com.motaharinia.msutility.tools.encoding.EncodingTools;
import com.motaharinia.msutility.tools.fso.check.FsoPathCheckDto;
import com.motaharinia.msutility.tools.fso.check.FsoPathCheckTypeEnum;
import com.motaharinia.msutility.tools.fso.mimetype.FsoMimeTypeEnum;
import com.motaharinia.msutility.tools.fso.mimetype.FsoMimeTypeDto;
import com.motaharinia.msutility.tools.fso.content.*;
import com.motaharinia.msutility.tools.image.ImageTools;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author eng.motahari@gmail.com<br>
 * اینترفیس متد های ابزاری فایل سیستم
 */
public interface FsoTools {

    String EXCEPTION_EMPTY_DIRECTORY_PATH = "directoryPath is empty";
    String EXCEPTION_EMPTY_WITH_THUMBNAIL = "withThumbnail is empty";
    String EXCEPTION_EMPTY_PATH_FROM = "pathFrom is empty";
    String EXCEPTION_EMPTY_PATH_TO = "pathTo is empty";
    String EXCEPTION_EMPTY_WITH_DIRECTORY_CREATION = "withDirectoryCreation is empty";
    String EXCEPTION_EMPTY_FILE_NAME = "fileName is empty";
    String RECURSIVE_COPY_NAME = " - Copy";

    /**
     * این متد یک مسیر کامل فایل یا دایرکتوری ورودی دریافت میکند و چک میکند آن مسیر وجود داشته باشد و مدل حاوی نوع مسیر (فایل یا دایرکتوری) و مرجع فایل را خروجی میدهد
     *
     * @param path مسیر کامل فایل یا دایرکتوری ورودی
     * @return خروجی: مدل حاوی نوع مسیر (فایل یا دایرکتوری) و مرجع فایل
     */
    @NotNull
    static FsoPathCheckDto pathExistCheck(@NotNull String path) {
        if (ObjectUtils.isEmpty(path)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "path");
        }
        FsoPathCheckDto fsoPathCheckDto = new FsoPathCheckDto();
        File file = new File(path);
        fsoPathCheckDto.setFile(file);
        if (file.exists()) {
            if (!file.isDirectory()) {
                fsoPathCheckDto.setTypeEnum(FsoPathCheckTypeEnum.FILE);
            } else {
                fsoPathCheckDto.setTypeEnum(FsoPathCheckTypeEnum.DIRECTORY);
            }
        } else {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_PATH_IS_NOT_EXISTED, "path:" + path);
        }
        return fsoPathCheckDto;
    }

    /**
     * این متد یک مسیر و نوع آن و یک سوال که در صورت وجود مسیر در مقصد یک نام جدید با -copy بسازد را از ورودی دریافت میکند و در صورت تغییر مسیر آن را خروجی میدهد
     *
     * @param path                 مسیر
     * @param fsoPathCheckTypeEnum نوع مسیر دایرکتوری یا فایل
     * @param withRenameOnExist    در صورت وجود مسیر در مقصد یک نام جدید با -copy بسازد
     * @return خروجی: مسیر نهایی
     */
    @NotNull
    static String pathCreateCheck(@NotNull String path, @NotNull FsoPathCheckTypeEnum fsoPathCheckTypeEnum, boolean withRenameOnExist) {
        if (ObjectUtils.isEmpty(path)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "path");
        }
        if (ObjectUtils.isEmpty(fsoPathCheckTypeEnum)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "fsoPathCheckTypeEnum");
        }
        if (ObjectUtils.isEmpty(withRenameOnExist)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "withRenameOnExist");
        }
        File fileTo = new File(path);
        String[] pathDirectoryArray = path.split("/");
        if (fileTo.exists()) {
            if (withRenameOnExist) {
                if (fsoPathCheckTypeEnum.equals(FsoPathCheckTypeEnum.DIRECTORY)) {
                    path = FsoTools.recursiveCreateName(path, 0);
                } else {
                    String pathParentDirectory = String.join("/", Arrays.copyOf(pathDirectoryArray, pathDirectoryArray.length - 1));
                    String filefullName = pathDirectoryArray[pathDirectoryArray.length - 1];
                    path = FsoTools.recursiveCreateName(pathParentDirectory, getFileNameWithoutExtension(filefullName), getFileExtension(filefullName), 0);
                }
            } else {
                throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_DESTINATION_PATH_EXISTED, "path:" + path);
            }
        }
        return path;
    }

    /**
     * این متد یک مسیر و فیلترهای فایل و دایرکتوری محتویات آن را از ورودی دریافت میکند و طبق فیلترهای ورودی اطلاعات محتویات فایل و دایرکتوری های زیر مجموعه مسیر را در قالب مدل خروجی میدهد
     *
     * @param directoryPath        مسیر ورودی
     * @param acceptNameArray      آرایه نام فایلهای مجاز برای فیلتر
     * @param acceptExtensionArray آرایه پسوند فایلهای مجاز برای فیلتر
     * @param denyNameArray        آرایه نام فایلهای غیر مجاز برای فیلتر
     * @param denyExtensionArray   آرایه پسوند فایلهای غیر مجاز برای فیلتر
     * @param showHidden           مجاز بودن فایل و دایرکتوری های مخفی در فیلتر
     * @return خروجی: مدل حاوی اطلاعات فایلها و دایرکتوری های داخل یک مسیر
     */
    @NotNull
    static FsoPathContentDto pathContent(@NotNull String directoryPath, @NotNull String[] acceptNameArray, @NotNull String[] acceptExtensionArray, @NotNull String[] denyNameArray, @NotNull String[] denyExtensionArray, @NotNull Boolean showHidden) {
        if (ObjectUtils.isEmpty(directoryPath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_DIRECTORY_PATH);
        }
        if (ObjectUtils.isEmpty(showHidden)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "showHidden");
        }
        FsoPathCheckDto fsoPathCheckDto = pathExistCheck(directoryPath);
        File directory = fsoPathCheckDto.getFile();
        if (!fsoPathCheckDto.getTypeEnum().equals(FsoPathCheckTypeEnum.DIRECTORY)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_PATH_IS_NOT_DIRECTORY, "directoryPath:" + directoryPath);
        }

        long directorySize;

        //فرمت دهنده برای تبدیل تاریخ به رشته تاریخ
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //ایجاد مدل خروجی
        FsoPathContentDto fsoPathContentDto = new FsoPathContentDto();
        //ایجاد یک فایل لیست برای محدود سازی اطلاعات مورد نظر
        FsoFileListFilter fsoFileListFilter = new FsoFileListFilter(acceptNameArray, acceptExtensionArray, denyNameArray, denyExtensionArray, showHidden);
        //دریافت لیست اطلاعات فایلها و دایرکتوری های داخل مسیر
        File[] fileArray = directory.listFiles(fsoFileListFilter);
        if (!ObjectUtils.isEmpty(fileArray)) {
            //مرتب سازی فایلهای مسیر
            Arrays.sort(fileArray);
            //حلقه روی محتویات مسیر
            for (File subFile : fileArray) {
                if (subFile.isDirectory()) {
                    //اگر دایرکتوری است
                    directorySize = pathGetFileListRecursive(directoryPath + "/" + subFile.getName(), fsoFileListFilter, new ArrayList<>()).stream()
                            .mapToLong(File::length)
                            .sum();
                    fsoPathContentDto.getDirectoryList().add(new FsoPathContentDirectoryDto(subFile.getName(), directoryPath, directoryPath + "/" + subFile.getName(), new Date(subFile.lastModified()), sdf.format(subFile.lastModified()), directorySize));
                } else {
                    //اگر فایل است
                    fsoPathContentDto.getFileList().add(new FsoPathContentFileDto(getFileNameWithoutExtension(subFile.getName()), getFileExtension(subFile.getName()), subFile.getName(), directoryPath, directoryPath + "/" + subFile.getName(), new Date(subFile.lastModified()), sdf.format(subFile.lastModified()), subFile.length(), getMimeTypeDto(directoryPath + "/" + subFile.getName()).getMimeType()));
                }
            }
        }
        return fsoPathContentDto;
    }

    /**
     * این متد یک مسیر ورودی و فیلتر جستجو و یک لیست خالی مرجع فایل دریافت میکند و به صورت بازگشتی داخل دایرکتوری های مسیر ورودی جستجو میکند و لیست مرجع فایلهای داخل آن را خروجی میدهد
     *
     * @param directoryPath     مسیر ورودی
     * @param fileFullPathList  لیست مرجع فایل
     * @param fsoFileListFilter فیلتر جستجو
     * @return خروجی: لیست مراجع فایلهای داخل مسیر ورودی
     */
    @NotNull
    static List<File> pathGetFileListRecursive(@NotNull String directoryPath, @NotNull FsoFileListFilter fsoFileListFilter, @NotNull List<File> fileFullPathList) {
        if (ObjectUtils.isEmpty(directoryPath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_DIRECTORY_PATH);
        }
        if (ObjectUtils.isEmpty(fsoFileListFilter)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "fsoFileListFilter");
        }
        FsoPathCheckDto fsoPathCheckDto = pathExistCheck(directoryPath);
        File file = fsoPathCheckDto.getFile();
        File[] fileArray = file.listFiles(fsoFileListFilter);
        //حلقه روی محتویات مسیر
        for (File subFile : fileArray) {
            if (subFile.isDirectory()) {
                //اگر دایرکتوری است

                fileFullPathList = pathGetFileListRecursive(String.format("%s/%s", directoryPath, subFile.getName()), fsoFileListFilter, fileFullPathList);
            } else {
                //اگر فایل است
                fileFullPathList.add(subFile);
            }
        }
        return fileFullPathList;
    }

    /**
     * این متد یک مسیر را از ورودی دریافت میکند و اگر وجود نداشته باشد آن را ایجاد میکند
     *
     * @param directoryPath مسیر ورودی
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     */
    static void createDirectoryIfNotExist(@NotNull String directoryPath) throws IOException {
        if (ObjectUtils.isEmpty(directoryPath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_DIRECTORY_PATH);
        }
        File file = new File(directoryPath);
        if (!file.exists()) {
            FileUtils.forceMkdir(file);
        }
    }

    /**
     * این متد یک رشته مسیر دایرکتوری ورودی دریافت میکند و سعی میکند اگر آن مسیر دایرکتوری وجود ندارد آن را کامل ایجاد کند
     *
     * @param directoryPath رشته مسیر دایرکتوری ورودی
     */
    static void pathDirectoryPrepare(@NotNull String directoryPath) {
        if (ObjectUtils.isEmpty(directoryPath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_DIRECTORY_PATH);
        }
        String[] directoryPathArray = directoryPath.split("/");
        String directoryPathCheck = "";
        File directory;
        StringBuilder stringBuilder = new StringBuilder(directoryPathCheck);
        for (String subPath : directoryPathArray) {
            if (!ObjectUtils.isEmpty(subPath)) {
                stringBuilder.append("/").append(subPath);
                directory = new File(stringBuilder.toString());
                if (!directory.exists() && !directory.mkdir()) {
                    throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_DIRECTORY_CREATION_FAILED, stringBuilder.toString());
                }
            }
        }
    }

    /**
     * این متد یک مسیر ورودی و یک سوال که آیا مسیر ورودی تصویر بندانگشتی دارد یا خیر را از ورودی دریافت میکند و در صورت وجود مسیر ورودی، آن را حذف مینماید
     *
     * @param path          مسیر ورودی
     * @param withThumbnail مسیر مبدا حاوی تصویر بندانگشتی
     * @param fsoConfigDto  مدل تنظیمات ابزار فایل
     */
    static void delete(@NotNull String path, boolean withThumbnail, FsoConfigDto fsoConfigDto) {
        if (ObjectUtils.isEmpty(path)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "path");
        }
        if (ObjectUtils.isEmpty(withThumbnail)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_WITH_THUMBNAIL);
        }
        String thumbPath;
        File file = new File(path);
        if ((file.exists())) {
            FileUtils.deleteQuietly(file);
            if (withThumbnail) {
                for (Integer size : fsoConfigDto.getThumbSizeArray()) {
                    thumbPath = path + "-" + size + "." + fsoConfigDto.getThumbExtension();
                    delete(thumbPath, false, fsoConfigDto);
                }
            }
        }
    }


    /**
     * این متد یک مسیر مبدا و یک مسیر مقصد و یک سوال که آیا مسیر مبدا تصویر بندانگشتی دارد یا خیر و و یک سوال که در صورت وجود نداشتن مسیر آن را بسازد یا خیر از ورودی دریافت میکند و مسیر مبدا را به مسیر مقصد انتقال/تغییرنام میدهد
     *
     * @param pathFrom              مسیر مبدا
     * @param pathTo                مسیر مقصد
     * @param withThumbnail         مسیر مبدا حاوی تصویر بندانگشتی
     * @param fsoConfigDto          مدل تنظیمات ابزار فایل
     * @param withDirectoryCreation در صورت عدم وجود مسیر مقصد آن را ایجاد کند؟
     */
    static void move(@NotNull String pathFrom, @NotNull String pathTo, boolean withThumbnail, FsoConfigDto fsoConfigDto, boolean withDirectoryCreation) {
        if (ObjectUtils.isEmpty(pathFrom)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_PATH_FROM);
        }
        if (ObjectUtils.isEmpty(pathTo)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_PATH_TO);
        }
        if (ObjectUtils.isEmpty(withThumbnail)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_WITH_THUMBNAIL);
        }
        if (ObjectUtils.isEmpty(withDirectoryCreation)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_WITH_DIRECTORY_CREATION);
        }
        FsoPathCheckDto fsoPathCheckDto = pathExistCheck(pathFrom);
        File fileFrom = fsoPathCheckDto.getFile();
        String thumbPathFrom;
        String thumbPathTo;
        File fileTo = new File(pathTo);
        if (fileTo.exists()) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_DESTINATION_PATH_EXISTED, pathTo);
        } else {

            //به دست آوردن مسیر دایرکتوری والد مقصد
            String[] pathToDirectoryArray = pathTo.split("/");
            String pathToParentDirectory = String.join("/", Arrays.copyOf(pathToDirectoryArray, pathToDirectoryArray.length - 1));

            //در صورتی که در ورودی نیاز به ایجاد مسیر کامل مقصد باشد
            if (withDirectoryCreation) {
                //در صورت عدم وجود دایرکتوری پدر آن را ایجاد میکنیم
                pathDirectoryPrepare(pathToParentDirectory);
            }

            if (fileFrom.renameTo(fileTo) && withThumbnail) {
                for (Integer size : fsoConfigDto.getThumbSizeArray()) {
                    thumbPathFrom = pathFrom + "-" + size + "." + fsoConfigDto.getThumbExtension();
                    thumbPathTo = pathTo + "-" + size + "." + fsoConfigDto.getThumbExtension();
                    move(thumbPathFrom, thumbPathTo, false, fsoConfigDto, false);
                }
            }
        }
    }

    /**
     * این متد مسیر مبدا و مسیر مقصد و یک سوال که آیا مسیر ورودی تصویر بندانگشتی دارد یا خیر و یک سوال که آیا مسیر مقصد در صورت عدم وجود ایجاد شود و یک سوال که در صورت وجود مسیر در مقصد یک نام جدید با -copy بسازد را از ورودی دریافت میکند و مسیر مبدا را در مسیر مقصد کپی میکند <br>
     * اگر مسیر مقصد از قبل وجود داشته باشد مانند ویندوز نام مقصد را غیرتکراری میکند و کپی را انجام میدهد
     *
     * @param pathFrom              مسیر مبدا که میتواند دایرکتوری یا فایل باشد
     * @param pathTo                مسیر مقصد که اگر مسیر مبدا فایل بوده باید این مسیر نیز مسیر کامل فایل باشد
     * @param withThumbnail         مسیر مبدا حاوی تصویر بندانگشتی
     * @param fsoConfigDto          مدل تنظیمات ابزار فایل
     * @param withDirectoryCreation در صورت عدم وجود مسیر مقصد آن را ایجاد کند؟
     * @param withRenameOnExist     در صورت وجود مسیر در مقصد یک نام جدید با -copy بسازد
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     */
    static void copy(@NotNull String pathFrom, @NotNull String pathTo, boolean withThumbnail, FsoConfigDto fsoConfigDto, boolean withDirectoryCreation, @NotNull Boolean withRenameOnExist) throws IOException {
        if (ObjectUtils.isEmpty(pathFrom)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_PATH_FROM);
        }
        if (ObjectUtils.isEmpty(pathTo)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_PATH_TO);
        }
        if (ObjectUtils.isEmpty(withThumbnail)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_WITH_THUMBNAIL);
        }
        if (ObjectUtils.isEmpty(withDirectoryCreation)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_WITH_DIRECTORY_CREATION);
        }
        if (ObjectUtils.isEmpty(withRenameOnExist)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "withRenameOnExist");
        }
        FsoPathCheckDto fileFromFsoPathCheckDto = pathExistCheck(pathFrom);
        File fileFrom = fileFromFsoPathCheckDto.getFile();

        //به دست آوردن مسیر دایرکتوری والد مبدا
        String[] pathFromDirectoryArray = pathFrom.split("/");
        String pathFromParentDirectory = String.join("/", Arrays.copyOf(pathFromDirectoryArray, pathFromDirectoryArray.length - 1));

        //به دست آوردن مسیر دایرکتوری والد مقصد
        String[] pathToDirectoryArray = pathTo.split("/");
        String pathToParentDirectory = String.join("/", Arrays.copyOf(pathToDirectoryArray, pathToDirectoryArray.length - 1));

        //در صورتی که در ورودی نیاز به ایجاد مسیر کامل مقصد باشد
        if (withDirectoryCreation) {
            //در صورت عدم وجود دایرکتوری پدر آن را ایجاد میکنیم
            pathDirectoryPrepare(pathToParentDirectory);
        }

        //در صورت وجود فایل یا دایرکتوری با نام تکراری در مقصد در صورتی که درخواست تغییر نام از ورودی متد داشته باشیم نام جدید مانند ویندوز به آن میدهیم که روی قبلی کپی نشود ، در غیر این صورت خطا صادر میکنیم
        if (fileFromFsoPathCheckDto.getTypeEnum().equals(FsoPathCheckTypeEnum.DIRECTORY)) {
            String pathFromDirectoryName = pathFromDirectoryArray[pathFromDirectoryArray.length - 1];
            pathTo = pathCreateCheck(String.format("%s/%s", pathTo, pathFromDirectoryName), fileFromFsoPathCheckDto.getTypeEnum(), withRenameOnExist);
        } else {
            pathTo = pathCreateCheck(pathTo, fileFromFsoPathCheckDto.getTypeEnum(), withRenameOnExist);
        }
        File fileTo = new File(pathTo);

        //کپی اطلاعات
        if (fileFromFsoPathCheckDto.getTypeEnum().equals(FsoPathCheckTypeEnum.DIRECTORY)) {
            FileUtils.copyDirectory(fileFrom, fileTo);
        } else {
            FileUtils.copyFile(fileFrom, fileTo);
            if (withThumbnail) {
                String pathFromFileName = pathFromDirectoryArray[pathFromDirectoryArray.length - 1];
                pathToDirectoryArray = pathTo.split("/");
                String pathToFileName = pathToDirectoryArray[pathToDirectoryArray.length - 1];
                for (Integer size : fsoConfigDto.getThumbSizeArray()) {
                    fileFrom = new File(String.format("%s/%s-%s.%s", pathFromParentDirectory, pathFromFileName, size.toString(), fsoConfigDto.getThumbExtension()));
                    fileTo = new File(String.format("%s/%s-%s.%s", pathToParentDirectory, pathToFileName, size.toString(), fsoConfigDto.getThumbExtension()));
                    if (fileTo.exists()) {
                        delete(pathToParentDirectory + "/" + pathToFileName + "-" + size + "." + fsoConfigDto.getThumbExtension(), false, fsoConfigDto);
                    }
                    FileUtils.copyFile(fileFrom, fileTo);
                }
            }
        }
    }

    /**
     * این متد یک نشانی وب فایل را میخواند و محتویات آن را به صورت آرایه ای از بایت خروجی میدهد
     *
     * @param urlAddress نشانی وب
     * @return خروجی:  آرایه ای از بایت نشانی وب داده شده
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     */
    static byte[] downloadUrlAndRead(@NotNull String urlAddress) throws IOException {
        if (ObjectUtils.isEmpty(urlAddress)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "url");
        }
        URL url = new URL(urlAddress);
        URLConnection connection = url.openConnection();
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }


    /**
     * این متد یک مسیر فایل از ورودی دریافت میکند و  محتویات آن را به صورت آرایه ای از بایت خروجی میدهد
     *
     * @param fileFullPath مسیر فایل
     * @return خروجی: آرایه ای از بایت مسیر فایل داده شده
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     */
    static byte[] downloadPathAndRead(@NotNull String fileFullPath) throws IOException {
        if (ObjectUtils.isEmpty(fileFullPath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "fileFullPath");
        }
        File file = new File(fileFullPath);
        if (file.exists()) {
            return FileUtils.readFileToByteArray(file);
        } else {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_PATH_IS_NOT_EXISTED, "fileFullPath:" + fileFullPath);
        }
    }

    /**
     * این متد یک مسیر دایرکتوری و نام کامل فایل و آرایه بایت داده را از ورودی دریافت میکند و بعد از ثبت آرایه بایت در مسیر مورد نظر ، مسیر رمزگذاری شده فایل ثبت شده را خروجی میدهد
     *
     * @param directoryPath مسیر دایرکتوری
     * @param fileFullName  نام کامل فایل
     * @param fileBytes     آرایه بایت داده فایل
     * @param withThumbnail مسیر مبدا حاوی تصویر بندانگشتی
     * @param fsoConfigDto  مدل تنظیمات ابزار فایل
     * @return خروجی: مسیر رمزگذاری شده فایل ثبت شده
     * @throws IOException این متد ممکن است اکسپشن داشته باشد
     */
    static String uploadWriteToPath(@NotNull String directoryPath, @NotNull String fileFullName, byte[] fileBytes, boolean withThumbnail, FsoConfigDto fsoConfigDto) throws IOException {
        File file = new File(directoryPath + fileFullName);
        FileUtils.writeByteArrayToFile(file, fileBytes);
        if (withThumbnail) {
            for (Integer size : fsoConfigDto.getThumbSizeArray()) {
                ImageTools.createThumb(directoryPath, fileFullName, size, size);
            }
        }
        return EncodingTools.base64Encode(directoryPath + fileFullName);
    }

    /**
     * این متد رشته نام کامل فایل(نام و پسوند) را از ورودی دریافت میکند و رشته پسوند آن را خروجی میدهد
     *
     * @param fileName رشته نام کامل فایل(نام و پسوند)
     * @return خروجی: رشته پسوند فایل
     */
    @NotNull
    static String getFileExtension(@NotNull String fileName) {
        if (ObjectUtils.isEmpty(fileName)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_FILE_NAME);
        }
        int lastDot = fileName.lastIndexOf(".");
        if (ObjectUtils.isEmpty(lastDot) || lastDot < 1) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_FILE_NAME_FORMAT_IS_INVALID, fileName);
        }
        return fileName.substring(lastDot + 1);
    }

    /**
     * این متد رشته نام کامل فایل(نام و پسوند) را از ورودی دریافت میکند و رشته نام فایل بدون پسوند آن را خروجی میدهد
     *
     * @param fileName رشته نام کامل فایل(نام و پسوند)
     * @return خروجی: رشته نام فایل بدون پسوند آن
     */
    @NotNull
    static String getFileNameWithoutExtension(@NotNull String fileName) {
        if (ObjectUtils.isEmpty(fileName)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_FILE_NAME);
        }
        int lastDot = fileName.lastIndexOf(".");
        if (ObjectUtils.isEmpty(lastDot) || lastDot < 1) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_FILE_NAME_FORMAT_IS_INVALID, "fileName:" + fileName);
        }
        return fileName.substring(0, lastDot);
    }

    /**
     * این متد شناسه فایل را از ورودی دریافت میکند و بر اساس محدودیت پوشه عدد متناسب را خروجی میدهد
     *
     * @param fileId      شناسه فایل
     * @param folderLimit حداکثر تعداد مجاز فایل در یک دایرکتوری
     * @return خروجی: عدد متناسب
     */
    static int getRightLocationForSave(@NotNull Integer fileId, Integer folderLimit) {
        if (ObjectUtils.isEmpty(fileId)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "fileId");
        }
        return (((fileId - 1) / folderLimit) + 1);
    }


    /**
     * این متد رشته مسیری برای ایجاد یک پوشه در فایل سیستم و عدد صفر را ورودی میگیرد و مسیر پیشنهادی غیر تکراری پوشه با توجه به ورودی را خروجی میدهد
     *
     * @param directoryFullPath رشته مسیری برای ایجاد یک پوشه در فایل سیستم
     * @param counter           به صورت ثابت عدد صفر ورودی داده شود
     * @return خروجی:  مسیر پیشنهادی غیر تکراری پوشه با توجه به ورودی
     */
    @NotNull
    static String recursiveCreateName(@NotNull String directoryFullPath, @NotNull Integer counter) {
        if (ObjectUtils.isEmpty(directoryFullPath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "directoryFullPath");
        }
        if (ObjectUtils.isEmpty(counter)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "counter");
        }
        if (!(new File(directoryFullPath + RECURSIVE_COPY_NAME).exists())) {
            return directoryFullPath + RECURSIVE_COPY_NAME;
        } else {
            if ((new File(directoryFullPath + RECURSIVE_COPY_NAME + " (" + counter + ")").exists())) {
                counter++;
                return recursiveCreateName(directoryFullPath, counter);
            } else {
                return directoryFullPath + RECURSIVE_COPY_NAME + " (" + counter + ")";
            }
        }
    }

    /**
     * این متد رشته مسیر پوشه ، نام و پسوند فایل برای ایجاد یک فایل در فایل سیستم و عدد صفر را ورودی میگیرد و مسیر پیشنهادی غیر تکراری فایل با توجه به ورودی را خروجی میدهد
     *
     * @param pathTo    رشته مسیر پوشه
     * @param fileName  رشته نام فایل
     * @param extension رشته پسوند فایل
     * @param counter   به صورت ثابت عدد صفر ورودی داده شود
     * @return خروجی:  مسیر پیشنهادی غیر تکراری فایل با توجه به ورودی
     */
    @NotNull
    static String recursiveCreateName(@NotNull String pathTo, @NotNull String fileName, @NotNull String extension, @NotNull Integer counter) {
        if (ObjectUtils.isEmpty(pathTo)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_PATH_TO);
        }
        if (ObjectUtils.isEmpty(fileName)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, EXCEPTION_EMPTY_FILE_NAME);
        }
        if (ObjectUtils.isEmpty(extension)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "extension");
        }
        if (ObjectUtils.isEmpty(counter)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "counter");
        }
        String filePath = String.format("%s/%s%s.%s", pathTo, fileName, RECURSIVE_COPY_NAME, extension);
        if (!(new File(filePath).exists())) {
            return filePath;
        } else {
            filePath = String.format("%s/%s%s (%s).%s", pathTo, fileName, RECURSIVE_COPY_NAME, counter.toString(), extension);
            if ((new File(filePath).exists())) {
                counter++;
                return recursiveCreateName(pathTo, fileName, extension, counter);
            } else {
                return filePath;
            }
        }
    }


    /**
     * این متد مسیر یا نام یک فایل را از ورودی دریافت میکند و مدل mimeType آن را خروجی میدهد
     *
     * @param filePath مسیر یا نام کامل فایل
     * @return خروجی: مدل mimeType فایل ورودی
     */
    @NotNull
    static FsoMimeTypeDto getMimeTypeDto(@NotNull String filePath) {
        if (ObjectUtils.isEmpty(filePath)) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.METHOD_PARAMETER_IS_NULL_OR_EMPTY, "filePath");
        }
        try {
            String mimeType = Files.probeContentType(Paths.get(filePath));
            String[] mimeTypeArray = mimeType.split("/");
            switch (String.valueOf(mimeTypeArray[0]).toLowerCase()) {
                case "image":
                    return new FsoMimeTypeDto(mimeType, FsoMimeTypeEnum.IMAGE, getFileExtension(filePath));
                case "application":
                    return new FsoMimeTypeDto(mimeType, FsoMimeTypeEnum.APPLICATION, getFileExtension(filePath));
                default:
                    return new FsoMimeTypeDto(mimeType, FsoMimeTypeEnum.GENERAL, getFileExtension(filePath));
            }
        } catch (IOException e) {
            throw new UtilityException(FsoTools.class, UtilityExceptionKeyEnum.FSO_MIMETYPE_NOT_VALID_FILE_PATH, "filePath:" + filePath);
        }
    }

    /**
     * متد بررسی و اصلاح انتهای مسیرها که اسلش داشته باشد یا خیر
     *
     * @param path مسیر
     * @param endWithSlash مسیر با اسلش خاتمه یابد؟
     * @return خروجی: مسیر اصلاح شده طبق ورودی
     */
    static String fixPathTrailingSlash(String path, boolean endWithSlash) {
        if (ObjectUtils.isEmpty(path)) {
            return path;
        }
        if (endWithSlash && !path.endsWith(FileSystems.getDefault().getSeparator())) {
            path += FileSystems.getDefault().getSeparator();
        }
        if (!endWithSlash && path.endsWith(FileSystems.getDefault().getSeparator())) {
            path = path.substring(0, path.length() - FileSystems.getDefault().getSeparator().length());
        }
        return path;
    }
}
