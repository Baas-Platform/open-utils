package com.shinemo.baas.openapi.demo;

import com.shinemo.baas.openapi.demo.common.TestReqVo;
import com.shinemo.baas.openapi.demo.util.JsonUtils;
import com.shinemo.baas.openapi.demo.util.OkHttpUtils;
import com.shinemo.baas.openapi.demo.util.ReqHeaderUtils;

import java.util.Map;

/**
 * demo示例
 *
 * @date: 2021-01-21
 * @author: luchao
 * @email: luc@shinemo.com
 */
public class Demo {


    /**
     * 开放平台提供，详细说明见文档
     */
//    private final static String APP_ID = "VphSlE9B";
    private final static String APP_ID = "AlQCsL5V";


    /**
     * 开放平台提供，详细说明见文档
     */
//    private final static String APP_SECRET = "HbU7KvreLBeZLlmE";
    private final static String APP_SECRET = "USmuTgBrzpc5Pbpk";


    /**
     * 请求基地址
     */
//    private final static String REQ_BASE_URL = "http://10.0.17.200:21006";
    private final static String REQ_BASE_URL = "http://59.36.10.215:41006/";


    public static void main(String[] args) {
//        System.out.println(getMapHeaderWithGet());
//        System.out.println(getMapHeaderWithPost(TestReqVo.builder().id(1L).build()));
        String ssoToken = "debug";
        System.out.println(getUserInfoBySsoToken(ssoToken));
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

    /**
     * SSOToken获取用户信息
     * <p>
     * 模拟
     *
     * @param ssoToken
     * @return
     */
    static String getUserInfoBySsoToken(String ssoToken) {
        // 文档里面有提供
        String apiUrl = "/openapi-cgw/openapi-login/sso/getUserInfoByToken";
        String url = REQ_BASE_URL + apiUrl + "?ssoToken=" + ssoToken;
        Map<String, Object> headers = getMapHeaderWithGet();
        return OkHttpUtils.syncHttps(url, "GET", headers, null, null);
    }
}
