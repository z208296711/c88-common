package com.c88.common.xxljob.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.c88.common.xxljob.dto.UpdateXxlJobDto;
import com.c88.common.xxljob.enums.XxlJobPathEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XxlJobService {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String executorAppname;

    @Value("${xxl.job.admin.username:admin}")
    private String username;

    @Value("${xxl.job.admin.password:123456}")
    private String password;

    @Value("${xxl.job.admin.jobGroup:2}")
    private Integer jobGroup;

    private String cookie;


    /**
     * 获取登录cookie
     *
     * @return
     */
    private String getCookie() {
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("userName", username);
        paramsMap.put("password", password);
        HttpResponse response = HttpRequest.post(String.format("%s%s", adminAddresses, "/login"))
                .form(paramsMap).execute();
        if (HttpStatus.HTTP_OK != response.getStatus()) {
            throw new RuntimeException(String.format("xxl-job-admin登录失败:statusCode=%s", response.getStatus()));
        }

        List<HttpCookie> cookies = response.getCookies();

        if (cookies.isEmpty()) {
            throw new RuntimeException(String.format("xxl-job-admin登录失败:[userName=%s,password=%s]", username, password));
        }

        return cookies.stream().map(cookie -> cookie.toString()).collect(Collectors.joining());
    }

    /**
     * 啟動job
     *
     * @param id
     */
    public void startJob(int id) {
        doRequest(XxlJobPathEnum.START, Map.of("id",id));
    }

    /**
     * 停止job
     *
     * @param id
     */
    public void stopJob(int id) {
        doRequest(XxlJobPathEnum.STOP, Map.of("id",id));
    }


    /**
     * 更新job
     *
     * @param updateXxlJob
     */
    public void updateJob(UpdateXxlJobDto updateXxlJob) {
        updateJob(JSONUtil.parseObj(updateXxlJob));
    }

    private void updateJob(Map<String, Object> paramMap) {
        JSONObject result = doRequest(XxlJobPathEnum.UPDATE, paramMap);

        if (HttpStatus.HTTP_OK != result.getInt("code")) {
            throw new RuntimeException(String.format("xxl-job-admin更新Job失败:%s", result.getStr("msg")));
        }
    }

    /**
     * 远程调用xxl-job-admin
     *
     * @param xxlJobPathEnum
     * @param paramMap
     * @return
     */
    private JSONObject doRequest(XxlJobPathEnum xxlJobPathEnum, Map<String, Object> paramMap) {
        if (StrUtil.isBlank(cookie)) {
            cookie = getCookie();
        }

        HttpResponse response = HttpRequest.post(String.format("%s%s", adminAddresses, xxlJobPathEnum.getPath()))
                .cookie(cookie).form(paramMap).execute();
        if (HttpStatus.HTTP_OK != response.getStatus()) {
            throw new RuntimeException(String.format("xxl-job-admin%s请求失败:statusCode=%s",
                    xxlJobPathEnum.getDesc(), response.getStatus()));
        }

        JSONObject result = JSONUtil.parseObj(response.body());

        Integer code = result.getInt("code");
        if (code != null && HttpStatus.HTTP_OK != code) {
            throw new RuntimeException(String.format("xxl-job-admin%s失败:msg=%s", xxlJobPathEnum.getDesc(), result.getStr("msg")));
        }

        return result;
    }


}
