package com.shinemo.baas.openapi.demo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求header工具类
 *
 * @date: 2021-01-21
 * @author: luchao
 * @email: luc@shinemo.com
 */
public class ReqHeaderUtils {

    /**
     * 生成开放平台签名
     *
     * @param content
     * @param secret
     * @return
     */
    private static String generate(String content, String secret) {
        String sign = EncryptUtils.sha256(content);
        sign = EncryptUtils.md5Hex(sign + secret);
        return sign;
    }


    /**
     * 获取请求头参数
     *
     * @param appId
     * @param appSecret
     * @param bodyLength
     * @return
     */
    public static Map<String, Object> get(String appId, String appSecret, int bodyLength) {
        Map<String, Object> headers = new HashMap<>(4);
        long timestamp = System.currentTimeMillis();
        System.out.println((String.format("openTimestamp: %s; bodyLength: %s; openAppId: %s; appSecret: %s", timestamp, bodyLength, appId, appSecret)));
        headers.put("openTimestamp", String.valueOf(timestamp));
        headers.put("openAppId", appId);
        String content = appId + timestamp + bodyLength;
        headers.put("openSign", generate(content, appSecret));
        return headers;
    }
}
