package com.mingrn.itumate.storage.enums;

/**
 * 文件存储类型枚举类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-03-16 15:30
 */
public enum FileTypesEnum {

    /**
     * 图片
     */
    IMG("/img"),
    /**
     * 文档
     */
    DOCS("/docs"),
    /**
     * 视频
     */
    VIDEO("/video"),
    /**
     * 媒体
     */
    MEDIA("/media");

    private String basePath;

    FileTypesEnum(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }
}
