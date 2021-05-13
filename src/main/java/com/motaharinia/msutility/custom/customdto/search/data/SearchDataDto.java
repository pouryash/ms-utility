package com.motaharinia.msutility.custom.customdto.search.data;


import com.motaharinia.msutility.custom.customdto.search.data.columnconfig.SearchDataColumnConfigDto;
import com.motaharinia.msutility.custom.customdto.search.filter.SearchFilterDto;
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
public class SearchDataDto<T extends Serializable> implements Serializable {
    /**
     * تعداد کل سطرهای قابل نمایش که صفحه بندی شده اند و به صفحات کوچکتر تبدیل شده اند
     */
    private Long totalRecordSize;
    /**
     * شماره صفحه فعلی
     */
    private Integer pageNo;
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
    private Integer totalPageSize;
    /**
     * اطلاعات اضافی
     */
    private Map<String, String> userDataMap = new HashMap<>();


    /**
     * متد صفحه بندی مدل حاوی نتیجه جستجوی اطلاعات بر اساس مدل فیلتر جستجو و لیست کل داده ها
     *
     * @param searchFilterDto  مدل فیلتر جستجو
     * @param allRowList       لیست کل داده ها
     * @param columnConfigList لیست تنظیمات ستونها
     * @param userDataMap      اطلاعات اضافی
     * @param <T>              نوع داده
     * @return خروجی: مدل حاوی نتیجه جستجوی اطلاعات
     */
    public static <T extends Serializable> SearchDataDto<T> paginateAllRowList(SearchFilterDto searchFilterDto, List<T> allRowList, List<SearchDataColumnConfigDto> columnConfigList, Map<String, String> userDataMap) {

        //محاسبه اندیسهای ابتدا و انتها جهت برش از لیست کلی
        int fromIndex = searchFilterDto.getPageNo() * searchFilterDto.getPageRowSize();
        int toIndex;
        if (fromIndex >= allRowList.size()) {
            fromIndex = allRowList.size() - 1;
        }
        toIndex = fromIndex + searchFilterDto.getPageRowSize();
        if (toIndex > allRowList.size()) {
            toIndex = allRowList.size() - 1;
        }
        //آماده سازی مدل داده جستجو با توجه به اندیسهای ابتدا و انتها و مدل فیلتر داده
        SearchDataDto<T> searchDataDto = new SearchDataDto<>();
        searchDataDto.setTotalRecordSize((long) allRowList.size());
        searchDataDto.setPageNo(searchFilterDto.getPageNo());
        searchDataDto.setPageRowList(allRowList.subList(fromIndex, toIndex));
        searchDataDto.setColumnConfigList(columnConfigList);
        searchDataDto.setUserDataMap(userDataMap);
        return searchDataDto;

    }
}
