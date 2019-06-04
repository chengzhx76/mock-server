package com.github.chengzhx76.mock.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: hp
 * @date: 2018/6/14
 */
public class HttpClient {

    public static String httpGet(String url, Map<String, String> headers) {
        return HttpUtil.createGet(url)
                .addHeaders(headers)
                .cookie(headers.get("Cookie"))
                .timeout(5000)
                .execute()
                .body();
    }

    public static String httpPost(String url, Map<String, String> headers, String body) {
        return HttpUtil.createPost(url)
                .addHeaders(headers)
                .cookie(headers.get("Cookie"))
                .timeout(5000)
                .body(body)
                .execute()
                .body();
    }

    public static void main(String[] args) {
        Map<String, String> mapHeaders = new HashMap<>();
        mapHeaders.put("1", "a");
        mapHeaders.put("2", "b");
        System.out.println(MapUtil.join(mapHeaders, "\n", "->"));
    }
}
