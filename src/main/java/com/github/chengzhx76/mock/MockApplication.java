package com.github.chengzhx76.mock;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.github.chengzhx76.mock.utils.HttpClient;
import com.github.chengzhx76.mock.utils.IpAdrressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: hp
 * @date: 2019/6/4
 */
@RestController
@SpringBootApplication
public class MockApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
    }

    private String domain = "https://time.geekbang.org";

    @GetMapping(value = "**")
    public String mockApi4GetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String ip = IpAdrressUtil.getIpAdrress(request);
        String path = request.getServletPath();
        String params = request.getQueryString();

        Map<String, String> mapHeaders = new HashMap<>();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            mapHeaders.put(key, request.getHeader(key));
        }

        logger.info("GET->请求\nIP[{}]\n路径[{}]\n头[{}]\n参数[{}]", ip, path, MapUtil.join(mapHeaders, "\n", "->"), params);
        String result = "";
        if (StrUtil.isNotBlank(domain)) {
            String url = domain + path + (params == null ? "" : "?" + params);
            try {
                result = HttpClient.httpGet(url, mapHeaders);
            } catch (Exception e) {
                logger.error("GET->error", e);
            }
            logger.info("GET->[{}]-RES[{}]", url, result);
        }
        return result;
    }

    @PostMapping(value = "**")
    public String mockApi4PostUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 取到 header cookie

        String ip = IpAdrressUtil.getIpAdrress(request);
        String path = request.getServletPath();
        String params = request.getQueryString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IoUtil.read(reader);

        Map<String, String> mapHeaders = new HashMap<>();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            mapHeaders.put(key, request.getHeader(key));
        }
        logger.info("POST->请求\nIP[{}]\n路径[{}]\n头[{}]\n参数[{}]\nBody参数[{}]", ip, path,
                MapUtil.join(mapHeaders, "\n", "->"), params, body);
        String result = "";
        if (StrUtil.isNotBlank(domain)) {
            String url = domain + path + (params == null ? "" : "?" + params);
            try {
                result = HttpClient.httpPost(url, mapHeaders, body);
            } catch (Exception e) {
                logger.error("POST->error", e);
            }
            logger.info("POST->[{}]-RES[{}]", url, result);
        }

        return result;
    }



    @GetMapping(value = "test")
    public String test() {
        return "test";
    }

}
