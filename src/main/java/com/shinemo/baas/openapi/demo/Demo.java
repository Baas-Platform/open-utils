package com.shinemo.baas.openapi.demo;

import com.shinemo.baas.openapi.demo.common.TestReqVo;
import com.shinemo.baas.openapi.demo.util.JsonUtils;
import com.shinemo.baas.openapi.demo.util.ReqHeaderUtils;

import java.util.Map;

/**
 * demo事例
 *
 * @date: 2021-01-21
 * @author: luchao
 * @email: luc@shinemo.com
 */
public class Demo {

    // 开放平台提供
    private final static String APP_ID = "123";
    // 开放平台提供
    private final static String APP_SECRET = "456";


    public static void main(String[] args) {
        System.out.println(getMapHeaderWithGet());
        System.out.println(getMapHeaderWithPost(TestReqVo.builder().id(1L).build()));
    }


    /**
     * GET方法 请求头
     *
     * @return
     */
    static Map<String, Object> getMapHeaderWithGet() {
        return ReqHeaderUtils.get(APP_ID, APP_SECRET, 0);
    }


    /**
     * POST方法 请求头
     *
     * @param reqObject 请求对象
     * @return
     */
    static Map<String, Object> getMapHeaderWithPost(Object reqObject) {
        String paramJson = JsonUtils.toJson(reqObject);
        return ReqHeaderUtils.get(APP_ID, APP_SECRET, paramJson.length());
    }
}
