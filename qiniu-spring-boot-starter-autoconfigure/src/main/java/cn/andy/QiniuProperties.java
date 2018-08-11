package cn.andy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {
    private String accesskey;
    private String secretkey;
    private String bucket;
    private String folder;
    private String host;
}
