package com.motaharinia.msutility.custom.customdto.search.data;


import com.motaharinia.msutility.custom.customdto.search.data.columnconfig.SearchDataColumnConfigDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

/**
 * @author https://github.com/motaharinia<br>
 * این کلاس مدل حاوی نتیجه جستجوی اطلاعات میباشد
 */

@Slf4j
@Data
@NoArgsConstructor
public class SearchDataDto<T> implements Serializable {
    /**
     * تعداد کل سطرهای قابل نمایش که صفحه بندی شده اند و به صفحات کوچکتر تبدیل شده اند
     */
    private Long totalRecordSize;
    /**
     * شماره صفحه فعلی
     */
    private Long pageNo;
    /**
     * لیست سطرهای داده صفحه
     */
    private List<T> pageRowList = new ArrayList<>();
    /**
     * لیست تنظیمات ستونها
     */
    private List<SearchDataColumnConfigDto> columnConfigList = new ArrayList<>();
    /**
     * تعداد کل صفحات در صفحه بندی اطلاعات
     */
    private Long totalPageSize;
    /**
     * اطلاعات اضافی
     */
    private Map<String, String> userDataMap = new HashMap<>();


}
