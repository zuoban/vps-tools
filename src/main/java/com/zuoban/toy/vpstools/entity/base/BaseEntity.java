package com.zuoban.toy.vpstools.entity.base;

import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjinqiang
 * @date 2018-09-18
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 3375513602384640303L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date creationTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;


    private String creationUser;
    private String updateUser;

    @PrePersist
    protected void onCreate() {
        if (UserHolder.getUser() != null) {
            creationUser = UserHolder.getUser().getUsername();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (UserHolder.getUser() != null) {
            updateUser = UserHolder.getUser().getUsername();
        }
    }

}
