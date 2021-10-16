package com.motaharinia.msutility.tools.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.motaharinia.msutility.custom.customdto.ClientResponseDto;
import com.motaharinia.msutility.custom.customexception.externalcall.ExternalCallException;
import com.motaharinia.msutility.custom.customjson.CustomObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * @author https://github.com/motaharinia<br>
 * این اینترفیس متدهای ابزاری فراخوانی rest و کنترل خطای آن را دارا میباشد
 */
public interface RestTools {
    RestTemplate restTemplate = new RestTemplate();
    CustomObjectMapper customObjectMapper = new CustomObjectMapper();


    /**
     * متد فراخوانی Rest به سرویسهای بیرونی
     *
     * @param httpMethod   روش ارسال درخواست POST,GET,PUT,DELETE
     * @param requestUrl   نشانی درخواست
     * @param requestCode  کد درخواست که در ترجمه و لاگ خطا از آن استفاده میشود
     * @param httpHeaders  هدرهای درخواست
     * @param body         بدنه درخواست
     * @param responseType پاسخ پارامتری درخواست
     * @param classType    نوع کلاس ارسال کننده درخواست به سرویس بیرونی که در خطا از آن استفاده میشود
     * @param <T>          نوع داده بدنه درخواست
     * @param <R>          نوع داده پاسخ درخواست
     * @param <C>          نوع کلاس ارسال کننده درخواست به سرویس بیرونی
     * @return خروجی: داده پاسخ درخواستی
     */
    static <T, R, C> R call(@NotNull HttpMethod httpMethod, @NotNull String requestUrl, @NotNull String requestCode, @NotNull HttpHeaders httpHeaders, T body, @NotNull ParameterizedTypeReference<R> responseType, C classType) {
        //تولید مدل و مسیر درخواست
        ResponseEntity<R> responseEntity;
        try {
            //ارسال درخواست
            if (body != null) {
                responseEntity = restTemplate.exchange(requestUrl, httpMethod, new HttpEntity<>(body, httpHeaders), responseType);
            } else {
                responseEntity = restTemplate.exchange(requestUrl, httpMethod, new HttpEntity<>(httpHeaders), responseType);
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            //خطاهای کلاینت مانند خطاهای عدم وجود نشانی 404 و خطای بیزینس 400
            ClientResponseDto<String> responseDto;
            try {
                responseDto = customObjectMapper.readValue(httpClientErrorException.getResponseBodyAsString(), new TypeReference<>() {
                });
            } catch (Exception exception) {
                throw new ExternalCallException(classType.getClass(), requestUrl, httpMethod, requestCode, httpClientErrorException.getStatusCode().toString(), "", httpClientErrorException);
            }
            throw new ExternalCallException(classType.getClass(), requestUrl, httpMethod, requestCode, httpClientErrorException.getStatusCode().toString(), responseDto.getException().getMessage(), httpClientErrorException);
        } catch (HttpServerErrorException httpServerErrorException) {
            //خطاهای سرور مانند خطای شبکه 503
            throw new ExternalCallException(classType.getClass(), requestUrl, httpMethod, requestCode, httpServerErrorException.getStatusCode().toString(), "", httpServerErrorException);
        } catch (ResourceAccessException resourceAccessException) {
            //خطای I/O درخواست
            throw new ExternalCallException(classType.getClass(), requestUrl, httpMethod, requestCode, "", "I/O error. Connection refused: connect", resourceAccessException);
        } catch (Exception exception) {
            //خطای درخواست
            throw new ExternalCallException(classType.getClass(), requestUrl, httpMethod, requestCode, "", "", exception);
        }

        return responseEntity.getBody();
    }

}
