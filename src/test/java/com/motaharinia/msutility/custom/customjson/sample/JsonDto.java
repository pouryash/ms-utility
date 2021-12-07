package com.motaharinia.msutility.custom.customjson.sample;

import com.motaharinia.msutility.custom.customfield.CustomDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author eng.motahari@gmail.com<br>
 * Description: <br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonDto implements Serializable {
    private CustomDate customDate;
    private EtcItemGender gender;
    private EtcItemGender genderNull;
    private Long dateOfBirth;
}
