package com.shinemo.baas.openapi.demo.common;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Create Time:2020/8/31
 * User: luchao
 * Email: luc@shinemo.com
 */
public class OKHttpClientFactory {

    private static Logger LOG = LoggerFactory.getLogger("info.log");


    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient
            .Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(chain -> {
                long start = System.nanoTime();
                try {
                    return chain.proceed(chain.request());
                } finally {
                    LOG.info("Received response for {} in {} ms", chain.request().url(), TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
                }
            })
            .build();

    private static OkHttpClient OK_HTTP_CLIENT_WITH_PROXY;

    public static OkHttpClient getClient() {
        return OK_HTTP_CLIENT;
    }
}
