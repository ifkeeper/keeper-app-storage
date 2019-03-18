package com.mingrn.itumate.storage.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingrn.itumate.global.result.ResponseMsgUtil;
import com.mingrn.itumate.global.result.Result;
import com.mingrn.itumate.storage.enums.FileTypesEnum;
import com.mingrn.itumate.storage.utils.FileBrowserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * File Browser 文件存储-Controller
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-03-17 18:12
 */
@RestController
@RequestMapping("/fileBrowserController")
@Api(description = "File Browser 文件存储-Controller")
public class FileBrowserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileBrowserController.class);

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

    /**
     * 临时
     */
    private static volatile String TokenAuth = null;

    @GetMapping("/login")
    @ApiOperation(value = "用户登录", notes = "File Browser 用户登录")
    public Result login() throws IOException {
        TokenAuth = FileBrowserUtil.login(browserBasePath + browserLoginPath, browserLoginUsername, browserLoginPassword);
        return ResponseMsgUtil.success(TokenAuth);
    }

    @PostMapping(value = "/uploadForRequest", consumes = "multipart/*", headers = "Content-Type=multipart/form-data")
    @ApiOperation(value = "文件上传", notes = "HttpServletRequest 请求文件上传")
    public Result uploadForRequest(HttpServletRequest request) throws IOException {
        JSONArray result = FileBrowserUtil.uploadFile(browserBasePath + browserUploadPath, FileTypesEnum.IMG, TokenAuth, request);
        return ResponseMsgUtil.success(result);
    }

    @PostMapping(value = "/uploadForMultipart", consumes = "multipart/*", headers = "Content-Type=multipart/form-data")
    @ApiOperation(value = "文件上传", notes = "MultipartFile 请求文件上传")
    public Result uploadForMultipart(@ApiParam(value = "上传文件") @RequestParam(name = "multipartFile") MultipartFile multipartFile) throws IOException {
        JSONObject result = FileBrowserUtil.uploadFile(browserBasePath + browserUploadPath, FileTypesEnum.IMG, TokenAuth, multipartFile);
        return ResponseMsgUtil.success(result);
    }
}
