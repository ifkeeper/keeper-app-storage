package com.mingrn.itumate.storage.config;

import com.mingrn.itumate.storage.vo.MediaProgress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传媒体监听事件
 *
 * @author zhang.shilin <br > zhang.shilin@yilutong.com
 * @date 2019/4/15 17:15
 */
@Component
public class MediaProgressListener implements ProgressListener {

    private HttpServletRequest request;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
        MediaProgress mediaProgress = new MediaProgress();
        request.getSession().setAttribute("mediaUploadProgress", mediaProgress);
    }

    @Override
    public void update(long readLength, long contextLength, int items) {
        MediaProgress mediaProgress = (MediaProgress) request.getSession().getAttribute("mediaUploadProgress");
        mediaProgress.setReadLength(readLength);
        mediaProgress.setContextLength(contextLength);
        mediaProgress.setItems(items);
    }
}