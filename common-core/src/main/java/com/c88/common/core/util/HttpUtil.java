package com.c88.common.core.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class HttpUtil {

    public static String doGet(String url, String path, Object object) {

        return doGet(url, path, object, -1);
    }

    public static String doGet(String url, String path, Object object, int timeout) {

        RequestConfig config = timeout < 0 ? null
                                           : RequestConfig.custom()
                                                          .setConnectTimeout(timeout * 1000)
                                                          .setSocketTimeout(timeout * 1000)
                                                          .build();

        CloseableHttpClient client = config == null ? HttpClients.createDefault()
                                                    : HttpClients.custom().setDefaultRequestConfig(config).build();
        String responseText = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (object != null) {
                for (Field field : object.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (!Modifier.isTransient(field.getModifiers()) && field.get(object) != null) {
                        builder.setParameter(field.getName(), field.get(object).toString());
                    }
                }
            }
            if (!StringUtils.isEmpty(path)) {
                builder.setPath(path);
            }

            HttpGet method = new HttpGet(builder.build());
            if (config != null)
                method.setConfig(config);
            log.info("HttpUtil:request:{}", method.toString());
            response = client.execute(method);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseText = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("HttpUtil Exception.", e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }

                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("HttpUtil Exception.", e);
            }
        }
        log.info("HttpUtil:responseText:{}", responseText);
        return responseText;
    }

    public static String doGet(String url, String path, Map<String, ?> paramsMap) {

        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (paramsMap != null) {
                for (Map.Entry<String, ?> param : paramsMap.entrySet()) {
                    builder.setParameter(param.getKey(), param.getValue().toString());
                }
            }
            builder.setPath(path);
            HttpGet method = new HttpGet(builder.build());
            log.info("url:{}", builder.build());
            response = client.execute(method);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseText = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("HttpUtil Exception.", e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }

                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("HttpUtil Exception.", e);
            }
        }
        log.info("HttpUtil:responseText:{}", responseText);
        return responseText;
    }

    public static String convertParamsToQueryString(Map<String, ?> paramsMap) {

        StringJoiner sb = new StringJoiner("&");
        for (Map.Entry<String, ?> param : paramsMap.entrySet()) {
//            NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
//            paramList.add(pair);
//            sb.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "utf-8")).append("&");
            sb.add(param.getKey() + "=" + param.getValue());
        }
        return sb.toString();
    }

    public static String generateHtmlPostForm(String actionUrl, Map<String, String> params, boolean autoSubmitFromClientPage) {

        Set<String> key = params.keySet();
        String[] nameArr = key.toArray(new String[key.size()]);
        StringBuilder sb = new StringBuilder();
        sb.append("<form id=\"allPayAPIForm\" action=\"" + actionUrl + "\" method=\"post\">");
        for (int i = 0; i < nameArr.length; i++) {
            String name = nameArr[i];
            sb.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + params.get(name) + "\">");
        }
        if (autoSubmitFromClientPage) {
            sb.append("<script language=\"JavaScript\">");
            sb.append("allPayAPIForm.submit()");
            sb.append("</script>");
        }
        sb.append("</form>");
        return sb.toString();
    }

    public static String postByForm(String url, Map<String, ?> paramsMap) {

        return post(url, null, paramsMap);
    }

    public static String postByJson(String url, Object json) {

        return post(url, json, null);
    }

    public static String postByJsonWithHeader(String url, Object json, Map header){
        return post(url, json, null, null, header);
    }

    public static String postByJson(String url, Object json, Integer timeout) {

        return post(url, json, null, timeout);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Object json, Map<String, ?> paramsMap) {

        return post(url, json, paramsMap, null);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Object json, Map<String, ?> paramsMap, Integer timeout) {

        return post(url, json, paramsMap, timeout, null);
    }

    public static String post(String url, Object json, Map<String, ?> paramsMap, Integer timeout, Map<String, ?> extraHeader) {

        CloseableHttpClient client;
        if (Objects.nonNull(timeout)) {
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000)
                    .build();
            client = HttpClientBuilder.create()
                    .setDefaultRequestConfig(config)
                    .build();
        } else {
            client = HttpClients.createDefault();
        }

        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if(extraHeader != null)
                extraHeader.entrySet().forEach(h->{
                    method.setHeader(h.getKey(), (String) h.getValue());
                });
//            method.setHeader("reurl-api-key", "");
            log.info("HttpUtil:POST:url:{}", url);
            if (json != null) {
                String jsonString = JSON.toJSONString(json);
                log.info("HttpUtil:JSON Body:{}", jsonString);
                StringEntity stringEntity = new StringEntity(jsonString);
                stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
                stringEntity.setContentType("application/json");
                method.setEntity(stringEntity);
            }
            if (paramsMap != null) {
                String queryString = convertParamsToQueryString(paramsMap);
                log.info("HttpUtil:queryString:{}", queryString);
                StringEntity stringEntity = new StringEntity(queryString);
                stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
                stringEntity.setContentType("application/x-www-form-urlencoded");
                method.setEntity(stringEntity);
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            log.error("HttpUtil Exception.", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("HttpUtil Exception.", e);
            }
        }
        log.info("HttpUtil:responseText:{}", responseText);
        return responseText;
    }

    public static String post(String url, String requestString) {

        String contentType = "application/json";
        return post(url, contentType, requestString);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url           提交的URL
     * @param requestString 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, String contentType, String requestString) {

        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(requestString);
            stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
            stringEntity.setContentType(contentType);
            method.setEntity(stringEntity);
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            log.error("HttpUtil Exception.", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("HttpUtil Exception.", e);
            }
        }
        log.info("HttpUtil:responseText:{}", responseText);
        return responseText;
    }

    public static String buildGetUrl(String url, String path, Map<String, ?> paramsMap) {

        String buildUrl = "";
        try {
            URIBuilder builder = new URIBuilder(url);
            if (paramsMap != null) {
                for (Map.Entry<String, ?> param : paramsMap.entrySet()) {
                    builder.setParameter(param.getKey(), param.getValue().toString());
                }
            }
            builder.setPath(path);
            buildUrl = builder.build().toString();
        } catch (Exception e) {
            log.error("HttpUtil Exception.", e);
        }
        return buildUrl;
    }

    public static String parseMapToString(Map<String, Object> paramsMap) {

        StringJoiner sb = new StringJoiner("&");
        for (Map.Entry<String, Object> param : paramsMap.entrySet()) {
            sb.add(param.getKey() + "=" + param.getValue());
        }
        return sb.toString();
    }

    private void logParams(String prefix, HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            sb.append(paramName);
            sb.append(": ");
            sb.append(request.getParameter(paramName));
            sb.append(", ");
        }
        log.info(sb.toString());
    }

}
