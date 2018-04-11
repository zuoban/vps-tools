package com.zuoban.toy.vpstools.service;

/**
 * @author wangjinqiang
 */
public interface DownloadService {

    /**
     * 下载文件
     * @param url
     */
    void download(String url);

    /**
     * 使用 aria2 下载
     * @param url
     */
    void aria2Download(String url);

    /**
     * 用 wget 下载
     * @param url
     */
    void wgetDownload(String url);
}
