package com.mingrn.itumate.storage.vo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 媒体进度条
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019/4/16 19:57
 */
@Component
public class MediaProgress implements Serializable {

    public static final Long serialVersionUID = -1L;

    /**
     * 已加载数据
     */
    private long readLength = 0L;
    /**
     * 总数据
     */
    private long contextLength = 0L;
    /**
     * 已加载文件数
     */
    private int items;
    /**
     * 已加载数据占比
     */
    private double percent;

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getContextLength() {
        return contextLength;
    }

    public void setContextLength(long contextLength) {
        this.contextLength = contextLength;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public double getPercent() {
        return ((double) readLength / (double) contextLength);
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}