package com.zuoban.toy.vpstools.entity;

import com.zuoban.toy.vpstools.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 上传文件
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
public class File extends BaseEntity {
    /**
     * 文件名
     */
    @Column(unique = true)
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 文件拓展名
     */
    private String extension;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件大小描述
     */
    private String fileSize;

    /**
     * md5
     */
    private String md5;
}
