package com.motaharinia.msutility.custom.custommodel.search.data;


import com.motaharinia.msutility.custom.custommodel.search.data.col.SearchDataColDto;
import com.motaharinia.msutility.custom.custommodel.search.data.row.SearchDataRowDto;
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
public class SearchDataDto implements Serializable {
    /**
     * شماره صفحه فعلی
     */
    private Integer page;
    /**
     * تعداد کل سطرهای قابل نمایش که صفحه بندی شده اند و به صفحات کوچکتر تبدیل شده اند
     */
    private Long records;
    /**
     * لیست سطرهای داده
     */
    private List<SearchDataRowDto> rowModelList = new ArrayList<>();
    /**
     * لیست اطلاعات ستونها
     */
    private List<SearchDataColDto> colModelList = new ArrayList<>();
    /**
     * تعداد صفحات در صفحه بندی اطلاعات
     */
    private Long total;
    /**
     * اطلاعات اضافی
     */
    private Map<String, String> userDataMap = new HashMap<>();


}
