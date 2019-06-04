package com.github.chengzhx76.mock.utils;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @desc:
 * @author: hp
 * @date: 2018/6/14
 */
public class HttpClient {
    public static String httpGet(String url) throws IOException {
        return Request
                .Get(url)
                .connectTimeout(10000)
                .socketTimeout(10000)
                .execute()
                .returnContent()
                .asString(Charset.forName("utf-8"));
    }
}
