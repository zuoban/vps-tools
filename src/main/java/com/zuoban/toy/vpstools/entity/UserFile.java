package com.zuoban.toy.vpstools.entity;

import com.zuoban.toy.vpstools.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */

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
public class UserFile extends BaseEntity {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文件id
     */
    private Long fileId;


    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件大小
     *
     */
    private Long size;

    /**
     * 文件大小描述
     */
    private String fileSize;
}
