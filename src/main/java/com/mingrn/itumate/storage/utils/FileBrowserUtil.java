package com.mingrn.itumate.storage.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingrn.itumate.commons.utils.secure.GeneratorIDFactory;
import com.mingrn.itumate.commons.utils.object.ObjectUtils;
import com.mingrn.itumate.storage.enums.FileTypesEnum;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * File Browser 工具类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-03-16 15:31
 */
public final class FileBrowserUtil {

    private FileBrowserUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(FileBrowserUtil.class);

    /**
     * File Browser 用户登录
     *
     * @param url      登录地址
     * @param username 用户名
     * @param password 用户密码
     * @return 登录成功 Token
     */
    public static String login(final String url, final String username, final String password) throws IOException {

        JSONObject params = new JSONObject();
        params.put("username", username);
        params.put("password", password);

        StringEntity entity = new StringEntity(params.toString(), StandardCharsets.UTF_8);
        entity.setContentType(MediaType.APPLICATION_JSON_VALUE);

        HashMap<String, String> header = new HashMap<>(1);
        header.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return FileBrowserHttpClientUtils.executeForEntityIsStr(RequestMethod.POST, url, entity, header, null);
    }

    /**
     * 文件上传
     *
     * @param url       上传地址
     * @param typesEnum 文件上传地址枚举类 {@link com.mingrn.itumate.storage.enums.FileTypesEnum}
     * @param authToken 文件上传 token 认证 {@link FileBrowserUtil#login(String, String, String)}
     * @return 文件上传成功详情信息
     */
    public static JSONArray uploadFile(String url, FileTypesEnum typesEnum, String authToken, HttpServletRequest request) throws IOException {
        return uploadFile(url, typesEnum, authToken, (MultipartHttpServletRequest) request);
    }

    /**
     * 文件上传
     *
     * @param url                         上传地址
     * @param typesEnum                   文件上传地址枚举类 {@link com.mingrn.itumate.storage.enums.FileTypesEnum}
     * @param authToken                   文件上传 token 认证 {@link FileBrowserUtil#login(String, String, String)}
     * @param multipartHttpServletRequest 文件流(允许多个) {@link org.springframework.web.multipart.MultipartHttpServletRequest}
     * @return 文件上传成功详情信息
     */
    public static JSONArray uploadFile(final String url, FileTypesEnum typesEnum, String authToken,
                                       MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {

        if (ObjectUtils.isNull(typesEnum)) {
            throw new RuntimeException("file type can not be null");
        }

        JSONArray responseArr = new JSONArray();
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        while (iterator.hasNext()) {

            String original = iterator.next();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(original);

            JSONObject result = uploadFile(url, typesEnum, authToken, multipartFile);
            responseArr.add(result);
        }
        return responseArr;
    }


    /**
     * 文件上传
     *
     * @param url           上传地址
     * @param typesEnum     文件上传地址枚举类 {@link com.mingrn.itumate.storage.enums.FileTypesEnum}
     * @param authToken     文件上传 token 认证 {@link FileBrowserUtil#login(String, String, String)}
     * @param multipartFile 文件流{@link org.springframework.web.multipart.MultipartFile}
     * @return 文件上传成功详情信息
     */
    public static JSONObject uploadFile(final String url, FileTypesEnum typesEnum,
                                        String authToken, MultipartFile multipartFile) throws IOException {

        if (ObjectUtils.isNull(typesEnum)) {
            throw new RuntimeException("file type can not be null");
        }

        if (null == multipartFile) {
            throw new RuntimeException("文件不存在");
        }

        JSONObject result = null;
        String original = multipartFile.getOriginalFilename();

        String prefixPath = typesEnum.getBasePath() + "/" + GeneratorIDFactory.generatorUUID();
        String ext = original.substring((original != null ? original.lastIndexOf(".") : 0) + 1).toLowerCase();
        String rename = prefixPath + "." + ext;

        try {
            InputStreamEntity entity = new InputStreamEntity(multipartFile.getInputStream());

            HashMap<String, String> header = new HashMap<>(1);
            header.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            header.put("X-Auth", authToken);

            String requestUri = url + rename;
            LOGGER.info("upload file to browser... : {}", requestUri);
            if (FileBrowserHttpClientUtils.postForEntityIsOK(requestUri, entity, header, null)) {
                String response = FileBrowserHttpClientUtils.executeForEntityIsStr(RequestMethod.GET, requestUri, null, header, null);
                result = JSONObject.parseObject(response);
                LOGGER.info("file upload success: {}", response);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
}
