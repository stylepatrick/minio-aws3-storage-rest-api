package stylepatrick.minio.resource;

import stylepatrick.minio.service.MinioService;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class MinioResource {

    private final MinioService minioService;

    @GetMapping(path = "/buckets")
    public List<Bucket> listAllBuckets() {
        return minioService.listAllBuckets();
    }

    @GetMapping("/{path}/objects")
    public List<String> listObjects(
            @PathVariable("path") String path
    ) throws Exception {
        return minioService.listObjects(path);
    }

    @PostMapping(path = "/{path}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<String> uploadFile(
            @PathVariable(value = "path") String path,
            @RequestPart(value = "objects", required = false) List<MultipartFile> objects
    ) {
        List<String> result = new ArrayList<>();
        objects.forEach(object -> {
            try {
                minioService.uploadObject(path, object.getOriginalFilename(), object.getBytes());
                result.add(object.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    @GetMapping(path = "/{path}/download/{object}")
    public ResponseEntity<ByteArrayResource> uploadFile(
            @PathVariable(value = "path") String path,
            @PathVariable(value = "object") String object
    ) {
        byte[] data = minioService.downloadObject(path, object);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + object + "\"")
                .body(resource);

    }

    @GetMapping(path = "/{path}/delete/{object}")
    public String deleteFile(
            @PathVariable(value = "path") String path,
            @PathVariable(value = "object") String object
    ) throws Exception {
        return minioService.deleteObject(path, object);
    }

}
