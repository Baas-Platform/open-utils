package com.shinemo.baas.openapi.demo.util;

import com.shinemo.baas.openapi.demo.common.ApiException;
import com.shinemo.baas.openapi.demo.common.OKHttpClientFactory;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Create Time:2020/8/31
 * User: luchao
 * Email: luc@shinemo.com
 */
public class OkHttpUtils {

    private final static String FAIL_MSG = "call api %s failed. headers=%s, code=%s, message=%s";
    private final static String TIMEOUT_MSG = "call api %s timeout. headers=%s";
    private final static String GW_ERROR_MSG = "call api %s gateway error. headers=%s";

    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");


    public static String syncHttps(String url, String method, Map<String, Object> headers, String body, String contentType) {
        Call call = okHttpCall(url, method, headers, body, contentType);
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                okhttp3.ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    return responseBody.string();
                }
            }
            throw new ApiException(String.format(FAIL_MSG, url, headers, response.code(), response.message()), response.code());
        } catch (ApiException apie) {
            throw apie;
        } catch (SocketTimeoutException e) {
            throw new ApiException(String.format(TIMEOUT_MSG, url, headers), 504);
        } catch (Throwable throwable) {
            throw new ApiException(String.format(GW_ERROR_MSG, url, headers), 502);
        }
    }

    private static Call okHttpCall(String url, String method, Map<String, Object> headers, String body, String contentType) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> builder.header(k, v.toString()));
        }

        if (body != null && body.length() > 0) {
            builder.method(method, okhttp3.RequestBody.create(getMediaType(contentType), body));
        } else if ("POST".equalsIgnoreCase(method)) {
            builder.post(getRequestBody(contentType));
        }
        return OKHttpClientFactory
                .getClient()
                .newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
                .newCall(builder.build());
    }

    private static MediaType getMediaType(String contentType) {
        if (contentType == null || "".equals(contentType)
                || "application/json; charset=utf-8".equalsIgnoreCase(contentType)) {
            return APPLICATION_JSON;
        }
        return MediaType.parse(contentType);
    }


    private static okhttp3.RequestBody getRequestBody(String contentType) {
        if (contentType == null || "".equals(contentType)) {
            return EMPTY_BODY;
        }
        return new okhttp3.RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse(contentType);
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {

            }
        };
    }

    private static final okhttp3.RequestBody EMPTY_BODY = new okhttp3.RequestBody() {
        @Nullable
        @Override
        public MediaType contentType() {
            return APPLICATION_JSON;
        }

        @Override
        public void writeTo(BufferedSink bufferedSink) throws IOException {

        }
    };
}
