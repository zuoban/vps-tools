package com.zuoban.toy.vpstools.entity;

import com.zuoban.toy.vpstools.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 下载任务
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
public class DownloadTask extends BaseEntity {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 下载路径
     */
    private String url;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 状态
     */
    private Integer status;

    @Column(length = 2000)
    private String errorMessage;

}
