package com.zuoban.toy.vpstools.vo;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 文件信息
 *
 * @author wangjinqiang
 */
@Data
public class FileInfo {
    private String fileName;
    private String relativePath;
    private String fileSize;

    private LocalDateTime lastModifiedTime;
}
