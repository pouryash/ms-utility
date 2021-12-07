package com.motaharinia.msutility.custom.customvalidation.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * Description: <br>
 *     مدل نمونه برای تست اعتبارسنجی ListNoDuplicateByFields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListNoDuplicateByFieldsDto implements Serializable {
    String field1;
    String field2;
}
