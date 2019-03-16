package com.mingrn.itumate.storage.web;

import com.alibaba.fastjson.JSONArray;
import com.mingrn.itumate.global.result.ResponseMsgUtil;
import com.mingrn.itumate.global.result.Result;
import com.mingrn.itumate.storage.enums.FileTypesEnum;
import com.mingrn.itumate.storage.utils.FileBrowserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class Api {

    private static final Logger LOGGER = LoggerFactory.getLogger(Api.class);

    @Value("${file.browser.base.path}")
    private String browserBasePath;

    @Value("${file.browser.login.username}")
    private String browserLoginUsername;

    @Value("${file.browser.login.password}")
    private String browserLoginPassword;

    @Value("${file.browser.login.path}")
    private String browserLoginPath;

    @Value("${file.browser.upload.path}")
    private String browserUploadPath;

    private static String Auth = null;

    @PostMapping("/login")
    public Result login() {
        Auth = FileBrowserUtil.login(browserBasePath + browserLoginPath, browserLoginUsername, browserLoginPassword);
        return ResponseMsgUtil.success(Auth);
    }

    @PostMapping("/upload")
    public Result upload(HttpServletRequest request) {
        JSONArray result = FileBrowserUtil.uploadFile(browserBasePath + browserUploadPath, FileTypesEnum.IMG, Auth, request);
        return ResponseMsgUtil.success(result);
    }
}
