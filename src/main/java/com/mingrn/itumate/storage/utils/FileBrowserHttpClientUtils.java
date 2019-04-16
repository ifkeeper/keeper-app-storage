package com.mingrn.itumate.storage.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * File Browser Http Client 工具类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-03-17 15:44
 */
final class FileBrowserHttpClientUtils {

    private FileBrowserHttpClientUtils() {
    }

    /**
     * Socket 超时时间
     */
    private static int SOCKET_TIMEOUT;
    /**
     * 连接超时时间
     */
    private static int CONNECT_TIMEOUT;
    /**
     * 请求超时时间
     */
    private static int CONNECTION_REQUEST_TIMEOUT;
    /**
     * 默认请求配置
     */
    private static RequestConfig DEFAULT_CONFIG;

    static {
        SOCKET_TIMEOUT = 60000;
        CONNECT_TIMEOUT = 10000;
        CONNECTION_REQUEST_TIMEOUT = 1000;
        DEFAULT_CONFIG = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).build();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(FileBrowserHttpClientUtils.class);

    /**
     * Http Clint Post 请求
     *
     * @param url    请求地址
     * @param entity 请求 Entity extend {@link org.apache.http.HttpEntity}
     * @param header 请求 Header
     * @param config 请求配置 {@link org.apache.http.client.config.RequestConfig}
     * @return response is ok?
     */
    public static boolean postForEntityIsOK(final String url, final InputStreamEntity entity,
                                            final HashMap<String, String> header, final RequestConfig config) throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpUriRequest uriRequest = requestBuilder(RequestMethod.POST, url, entity, header, config);
            CloseableHttpResponse response = httpClient.execute(uriRequest);

            return (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Http Clint 请求
     *
     * @param method 请求类型 {@link org.springframework.web.bind.annotation.RequestMethod}
     * @param url    请求地址
     * @param entity 求 Entity {@link org.apache.http.HttpEntity}
     * @param header 请求 Header
     * @param config 请求配置 {@link org.apache.http.client.config.RequestConfig}
     * @return response result
     */
    public static String executeForEntityIsStr(final RequestMethod method, final String url, final HttpEntity entity,
                                               final HashMap<String, String> header, final RequestConfig config) throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpUriRequest uriRequest = requestBuilder(method, url, entity, header, config);
            CloseableHttpResponse response = httpClient.execute(uriRequest);

            if (null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new IOException((null != response ? response.getEntity() : null) == null ? "未知请求错误" : EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 构建 Http Client {@link org.apache.http.client.methods.HttpUriRequest}
     *
     * @param method 请求类型 {@link org.springframework.web.bind.annotation.RequestMethod}
     * @param url    请求地址
     * @param entity 求 Entity {@link org.apache.http.HttpEntity}
     * @param header 请求 Header
     * @param config 请求配置 {@link org.apache.http.client.config.RequestConfig}
     * @return {@link org.apache.http.client.methods.HttpUriRequest}
     */
    private static HttpUriRequest requestBuilder(final RequestMethod method, final String url, HttpEntity entity,
                                                 final HashMap<String, String> header, final RequestConfig config) {

        RequestBuilder builder;
        switch (method) {
            case GET:
                builder = RequestBuilder.get().setUri(url);
                break;
            default:
                builder = RequestBuilder.post().setUri(url);
                break;
        }

        builder.setConfig(config == null ? DEFAULT_CONFIG : config);

        if (null != entity) {
            builder.setEntity(entity);
        }

        Set<String> kSet = header.keySet();
        for (String key : kSet) {
            builder.setHeader(key, header.get(key));
        }

        return builder.build();
    }

}