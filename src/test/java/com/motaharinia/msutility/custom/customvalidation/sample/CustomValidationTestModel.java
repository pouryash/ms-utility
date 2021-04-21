package com.motaharinia.msutility.custom.customvalidation.sample;

import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customvalidation.companyphone.CompanyPhone;
import com.motaharinia.msutility.custom.customvalidation.daterange.DateRange;
import com.motaharinia.msutility.custom.customvalidation.decimalcount.DecimalCount;
import com.motaharinia.msutility.custom.customvalidation.doublerange.DoubleRange;
import com.motaharinia.msutility.custom.customvalidation.email.Email;
import com.motaharinia.msutility.custom.customvalidation.integerrange.IntegerRange;
import com.motaharinia.msutility.custom.customvalidation.latincharacters.LatinCharacters;
import com.motaharinia.msutility.custom.customvalidation.listlength.ListLength;
import com.motaharinia.msutility.custom.customvalidation.listnoduplicate.ListNoDuplicate;
import com.motaharinia.msutility.custom.customvalidation.listnoduplicatebyfields.ListNoDuplicateByFields;
import com.motaharinia.msutility.custom.customvalidation.mobile.Mobile;
import com.motaharinia.msutility.custom.customvalidation.nationalcode.NationalCode;
import com.motaharinia.msutility.custom.customvalidation.organizationeconomiccode.OrganizationEconomicCode;
import com.motaharinia.msutility.custom.customvalidation.organizationnationalcode.OrganizationNationalCode;
import com.motaharinia.msutility.custom.customvalidation.organizationregistrationno.OrganizationRegistrationNo;
import com.motaharinia.msutility.custom.customvalidation.password.Password;
import com.motaharinia.msutility.custom.customvalidation.persiancharacters.PersianCharacters;
import com.motaharinia.msutility.custom.customvalidation.personphone.PersonPhone;
import com.motaharinia.msutility.custom.customvalidation.postalcode.PostalCode;
import com.motaharinia.msutility.custom.customvalidation.price.Price;
import com.motaharinia.msutility.custom.customvalidation.required.Required;
import com.motaharinia.msutility.custom.customvalidation.stringlength.StringLength;
import com.motaharinia.msutility.custom.customvalidation.stringpattern.StringPattern;
import com.motaharinia.msutility.custom.customvalidation.usernameemailormobile.UsernameEmailOrMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس مدل نمونه برای تست اعتبارسنجی CustomValidation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomValidationTestModel implements Serializable {

    //CompanyPhone:
    @CompanyPhone
    private String companyPhoneNo;

    //DateRange:
    @DateRange(from = "1399-01-01", to = "1400-12-29")
    private CustomDate dateRange1;
    @DateRange(from = "1399-01-01")
    private CustomDate dateRange2;
    @DateRange(to = "1400-12-29")
    private CustomDate dateRange3;
    @DateRange(from = "today", to = "1400-12-29")
    private CustomDate dateRange4;

    //DecimalCount:
    @DecimalCount(min = 2, max = 4)
    private Double decimalCount1;
    @DecimalCount(exact = 3)
    private Double decimalCount2;

    //DoubleRange:
    @DoubleRange(min = 12, max = 13)
    private Double doubleRange;

    //Email:
    @Email
    private String email;

    //IntegerRange:
    @IntegerRange(min = 12, max = 13)
    private Integer integerRange;

    //LatinCharacters:
    @LatinCharacters
    private String latinCharacter;

    //ListLength:
    @ListLength(min = 2, max = 4)
    private List<String> listLength1;
    @ListLength(exact = 3)
    private List<String> listLength2;

    //ListNoDuplicate:
    @ListNoDuplicate
    private List<String> listNoDuplicate;

    //ListNoDuplicateByFields:
    @ListNoDuplicateByFields(fields = {"field1"})
    private List<ListNoDuplicateByFieldsModel> listNoDuplicateByFields;

    //Mobile:
    @Mobile
    private String mobile;

    //NationalCode:
    @NationalCode
    private String nationalCode;

    //OrganizationEconomicCode:
    @OrganizationEconomicCode
    private String organizationEconomicCode;

    //OrganizationNationalCode:
    @OrganizationNationalCode
    private String organizationNationalCode;

    //OrganizationRegistrationNo:
    @OrganizationRegistrationNo
    private String organizationRegistrationNo;

    //Password:
    @Password(minLength = 6, maxLength = 16, complicated = false)
    private String password1;
    @Password(minLength = 2, maxLength = 8, complicated = false)
    private String password2;
    @Password(minLength = 6, maxLength = 16, complicated = true)
    private String password3;
    @Password(minLength = 6, maxLength = 16, complicated = true ,complicatedSpecialChars = "~")
    private String password4;

    //PersianCharacters:
    @PersianCharacters
    private String persianCharacters;

    //PersonPhone:
    @PersonPhone
    private String personPhone;

    //PostalCode:
    @PostalCode
    private String postalCode;

    //Price:
    @Price
    private BigDecimal price;

    //Required:
    @Required
    private String required1;
    @Required
    private Integer required2;
    @Required
    private Boolean required3;
    @Required
    private List<String> required4;
    @Required
    private Map<Integer, String> required5;

    //StringLength:
    @StringLength(min = 2, max = 4)
    private String stringLength1;
    @StringLength(exact = 3)
    private String stringLength2;

    //StringPattern (Visa Card. All Visa card numbers start with a 4. New cards have 16 digits. Old cards have 13):
    @StringPattern(pattern = "^4[0-9]{12}(?:[0-9]{3})?$")
    private String stringPattern;

    //UsernameEmailOrMobile:
    @UsernameEmailOrMobile
    private String username1;
    @UsernameEmailOrMobile
    private String username2;
}
