package stylepatrick.minio.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("minio")
@Validated
@Setter
@Getter
public class MinioConfig {

    private String user;

    private String pwd;

    private String url;

    private String bucket;

    private String defaultFolder;

}
