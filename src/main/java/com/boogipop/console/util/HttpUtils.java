package com.boogipop.console.util;
import okhttp3.*;
import java.io.IOException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.util.HashMap;
import java.util.Map;
public class HttpUtils {
    public static String cmd;
    public static Map<String, String> customHeaders=new HashMap<>();
    public static OkHttpClient createInsecureOkHttpClient() throws IOException {
        // 创建自定义的X509TrustManager，绕过证书验证
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // 创建SSL上下文并初始化它，允许绕过证书验证
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            // 创建OkHttpClient并配置自定义的SSL上下文
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCertificates[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IOException("创建自定义OkHttpClient时发生错误", e);
        }
    }
    public static String sendHttpsGetRequest(String urlStr) {
        try {
            OkHttpClient client = createInsecureOkHttpClient();

            Request request = new Request.Builder()
                    .url(urlStr)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return responseBody;
            } else {
                System.err.println("HTTP Error: " + response.code());
            }
        } catch (IOException e) {
            return "connection error";
        }
        return "connection error";
    }
    public static String sendHttpGetRequest(String urlStr) {
        OkHttpClient client = new OkHttpClient();

        // 创建Request对象
        Request request = new Request.Builder()
                .url(urlStr)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return responseBody;
            } else {
                return "HTTP Error: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    public static String sendJsonPostRequestWithHeaders(String urlStr, String jsonInputString, Map<String, String> customHeaders) throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonInputString);

        Request.Builder requestBuilder = new Request.Builder()
                .url(urlStr)
                .post(requestBody);

        if (customHeaders != null) {
            for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new Exception(String.format("Error: %s",response.body().string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    public static String sendHttpsJsonPostRequestWithHeaders(String urlStr, String jsonInputString, Map<String, String> customHeaders) throws Exception {
        OkHttpClient client = createInsecureOkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonInputString);

        Request.Builder requestBuilder = new Request.Builder()
                .url(urlStr)
                .post(requestBody);

        if (customHeaders != null) {
            for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new Exception(String.format("Error: %s",response.body().string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
