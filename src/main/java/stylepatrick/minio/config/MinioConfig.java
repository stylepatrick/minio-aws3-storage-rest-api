package stylepatrick.minio.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter(AccessLevel.PROTECTED)
public class MinioConfig {

    @Value("${minio.access.name}")
    String accessKey;

    @Value("${minio.access.secret}")
    String accessSecret;

    @Value("${minio.url}")
    String minioUrl;

    @Value("${minio.bucket.name}")
    String bucketName;

    @Value("${minio.default.folder}")
    String defaultFolder;


}
