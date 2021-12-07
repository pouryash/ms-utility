package com.motaharinia.msutility.custom.customresterrorhandler;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس غیر فعال کننده پرتاب اکسپشن برای resttemplate زمانی که خطا رخ میدهد
 */
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        response.getBody();
    }
}
