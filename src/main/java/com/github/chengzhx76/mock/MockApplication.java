package com.github.chengzhx76.mock;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
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

/**
 * @desc:
 * @author: hp
 * @date: 2019/6/4
 */
@RestController
@SpringBootApplication
public class MockApplication {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
    }

    @GetMapping(value = "**")
    public String mockApi4GetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ip = IpAdrressUtil.getIpAdrress(request);
        String url = request.getServletPath();
        String params = request.getQueryString();
        LOG.info("GET->请求IP {}-路径 {}-参数 {}", ip, url, params);

        return DateUtil.now();
    }

    @PostMapping(value = "**")
    public String mockApi4PostUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String result = IoUtil.read(reader);

        String ip = IpAdrressUtil.getIpAdrress(request);
        String url = request.getServletPath();
        String params = request.getQueryString();

        LOG.info("POST->请求IP {}-路径 {}-参数 {}-Body参数{}", ip, url, params, result);

        return DateUtil.now();
    }



    @GetMapping(value = "test")
    public String test() {
        return "test";
    }

}
