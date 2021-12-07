package com.motaharinia.msutility.custom.customjson.sample;

import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customfield.CustomDateTime;
import com.motaharinia.msutility.custom.customvalidation.required.Required;


import com.motaharinia.msutility.tools.fso.view.FileViewDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس سمپل مدل برای تست سریالایز
 */

@Getter
@Setter
@NoArgsConstructor
public class MembershipRequestFrontDtoUpdate extends MembershipRequestFrontDto implements Serializable {

    @Required
    private Integer id;
    private String code;
    private CustomDateTime registerDateTime;
    private CustomDate meetingDate;
    private String meetingTime;
    private CustomDateTime meetingDoneDateTime;
    private CustomDateTime rejectDateTime;
    private String rejectDescription;
    private Boolean meetingRetry;
    private String status_langKey;
    private String status_value;
    private String description;
    private CustomDateTime statusDateTime;

    //user:
    private String user_gender_langKey;
    private String user_maritalStatus_langKey;
    private String user_maritalStatus_value;
    private String user_shType_langKey;
    private String user_nationality_langKey;
    private String user_inhabitancy_langKey;
    private String user_religion_langKey;
    private String user_religion_value;
    private String user_faith_langKey;
    private String user_faith_value;
    private String user_education_langKey;
    private String user_job_title;
    private String meetingLocation_langKey;
    //-----آیدی اطلاعات تماس جهت مراجعه حضوری عضو
    private Integer userContact_id;
    private String userContact_title;
    private String userContact_address;

    //socialGroup:
    private String socialGroup_name;
    private String socialGroup_code;
    private Integer socialGroup_trend_id;
    private String socialGroup_trend_name;
    private String socialGroup_defaultSocialEntityContact_city_name;
    private Integer socialGroup_defaultSocialEntityContact_city_id;
    private String socialGroup_defaultSocialEntityContact_address;
    private String socialGroup_defaultSocialEntityContact_postalCode;
    private String socialGroup_defaultSocialEntityContact_phoneNo;
    private String socialGroup_defaultSocialEntityContact_faxNo;

    //درخواست به افراد ارجاع شده
    private String referredBanker_name;
    // متصدی تایید نهایی
    private String referredFinalApprovalUser_name;

    //تصویر روی کارت ملی
    private List<FileViewDto> onNationalCartFileList = new ArrayList<>();

    //تصویر پشت کارت ملی
    private List<FileViewDto> backNationalCartFileList  = new ArrayList<>();

    // ویدیو یک دقیقه ایی
    private List<FileViewDto> selfieVideo = new ArrayList<>();

    //تصویر شناسنامه صفحه اول
    private List<FileViewDto> firstPageBirthCertificateFileList = new ArrayList<>();

    //تصویر شناسنامه صفحه توضیحات
    private List<FileViewDto> descriptionBirthCertificateFileList = new ArrayList<>();

    //تصویر عکس پرسنلی
    private List<FileViewDto> personalPicture = new ArrayList<>();

    //تصویر نمونه امضا
    private List<FileViewDto> signSample = new ArrayList<>();

    //تصویر عمومی
    private List<FileViewDto> personalityFileList = new ArrayList<>();

    //زمان پیشنهادی تحویل کارت
    private CustomDate cartDeliveryDateOffer;

}
