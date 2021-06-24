package stylepatrick.minio;

import stylepatrick.minio.config.MinioConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MinioClient {

    private final MinioConfig minioConfig;

    @Bean
    public io.minio.MinioClient generateMinioClient() {
        try {
            io.minio.MinioClient client = new io.minio.MinioClient(minioConfig.getMinioUrl(), minioConfig.getAccessKey(), minioConfig.getAccessSecret());
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
