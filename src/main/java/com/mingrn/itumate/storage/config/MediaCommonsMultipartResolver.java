package com.mingrn.itumate.storage.config;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 媒体上传 {@link org.springframework.web.multipart.commons.CommonsMultipartResolver} 扩展
 *
 * @author zhang.shilin <br > zhang.shilin@yilutong.com
 * @date 2019/4/15 17:29
 */
@Component
public class MediaCommonsMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {

    private HttpServletRequest request;

    @Resource
    private MediaProgressListener mediaProgressListener;

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        fileUpload.setSizeMax(-1);

        if (request != null) {
            mediaProgressListener.setRequest(request);
            fileUpload.setProgressListener(mediaProgressListener);
        }

        return fileUpload;
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        this.request = request;
        return super.resolveMultipart(request);
    }

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest httpServletRequest) {
        request.getSession().removeAttribute("mediaUploadProgress");
        super.cleanupMultipart(httpServletRequest);
    }
}