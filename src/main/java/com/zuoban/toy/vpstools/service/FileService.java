package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.entity.File;
import com.zuoban.toy.vpstools.repository.FileRepository;
import com.zuoban.toy.vpstools.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 文件 service
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Service
public class FileService extends BaseService<File, FileRepository> {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * 通过文件名获取文件
     *
     * @param filename 文件名
     */
    public Optional<File> findByName(String filename) {
        File file = new File();
        file.setName(filename);
        return fileRepository.findOne(Example.of(file));
    }
}
