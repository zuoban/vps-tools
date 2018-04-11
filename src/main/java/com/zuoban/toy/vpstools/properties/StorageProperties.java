package com.zuoban.toy.vpstools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author wangjinqiang
 */
@ConfigurationProperties(prefix = "storage")
@Data
@Component
public class StorageProperties {
    /**
     * 存储文件类路径
     */
    private String location = "upload-dir";

    /**
     * 下载URL前缀，返回给客户端保存
     */
    private String urlPrefix = "localhost:8080/files";

    /**
     * 资源文件映射
     */
    private String resourceMapping = "/files/**";

    /**
     * 允许上传的文件类型
     */
    private String enableSuffix = "jpg;jpeg;png;jpg";
}
