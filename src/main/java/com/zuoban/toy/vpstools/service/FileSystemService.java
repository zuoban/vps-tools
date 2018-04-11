package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.vo.FileInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * 文件系统 Service
 * @author wangjinqiang
 */
public interface FileSystemService {
   /**
    * 列表指定目录里所有文件
    * @param directory
    * @return
    */
   List<FileInfo> listFile(Path directory) throws IOException;
}
