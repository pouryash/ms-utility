package com.motaharinia.msutility.custom.customjson.sample;

import com.motaharinia.msutility.custom.customfield.CustomDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author https://github.com/motaharinia<br>
 * Description: <br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonDto implements Serializable {
    private CustomDate customDate;
    private List<String> genderList;
    private Long dateOfBirth;
}
