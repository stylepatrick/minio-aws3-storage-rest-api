package stylepatrick.minio.service;

import stylepatrick.minio.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;


    public List<Bucket> listAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<String> listObjects(String path) throws Exception {
        List<String> list = new ArrayList<>();
        minioClient.listObjects(minioConfig.getBucketName(), minioConfig.getDefaultFolder() + "/" + path + "/").forEach(x -> {
            try {
                list.add(x.get().objectName());
            } catch (InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

    public void uploadObject(String path, String objectName, byte[] content) {
        File file = new File("C:\\\\tmp\\\\" + objectName);
        file.canWrite();
        file.canRead();
        try {
            FileOutputStream iofs = new FileOutputStream(file);
            iofs.write(content);
            minioClient.putObject(minioConfig.getBucketName(), minioConfig.getDefaultFolder() + "/" + path + "/" + objectName, file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public byte[] downloadObject(String path, String objectName) {
        try {
            InputStream obj = minioClient.getObject(minioConfig.getBucketName(), minioConfig.getDefaultFolder() + "/" + path + "/" + objectName);

            byte[] content = IOUtils.toByteArray(obj);
            obj.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteObject(String path, String objectName) throws Exception {
        try {
            minioClient.removeObject(minioConfig.getBucketName(), minioConfig.getDefaultFolder() + "/" + path + "/" + objectName);
        } catch (Exception e) {
            return "failed to delete" + e.getMessage();
        }
        return "successfully deleted";
    }
}
