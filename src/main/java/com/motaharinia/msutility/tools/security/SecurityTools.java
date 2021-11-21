package com.motaharinia.msutility.tools.security;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @author https://github.com/motaharinia<br>
 * این اینترفیس متدهای ابزاری امنیتی را دارا میباشد
 */
public interface SecurityTools {


    /**
     * متد تولید کانتکست SSL (Untrusted) برای استفاده در تنظیمات RestTemplate و JsoupBuilder پروژه ها
     * @param protocol پروتوکل و الگوریتم مانند TLSv1.2
     * @return خروجی: کانتکست SSL
     * @throws NoSuchAlgorithmException خطا
     * @throws KeyManagementException خطا
     */
    static SSLContext getUntrustedSSLContext(String protocol) throws NoSuchAlgorithmException, KeyManagementException {
        /*
         * Ignore untrusted certificates
         */
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        //Ignore untrusted certificates
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        //Ignore untrusted certificates
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sslContext = SSLContext.getInstance(protocol);
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        return sslContext;
    }
}
