package pioneer.common.minio;

import pioneer.common.minio.MinIOProperties;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class MinIOService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOProperties properties;

    /**
     * 上传文件
     * @param file
     * @return
     */
    public String upload(MultipartFile file) {
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 构造新的文件名，名字不重复
        String objectName = UUID.randomUUID().toString() + suffix;
        // 上传文件  文件名，mime类型，文件大小，文件流，桶名
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1) //-1表示文件的分区有服务器自己指定
                    .bucket(properties.getBucketName())
                    .object(objectName)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
        return properties.getEndpoint() + "/" + properties.getBucketName() + "/" + objectName;
    }
/**
     * 上传文件
     * @param name
     * @param inputStream
     * @param contentType
     * @return
     */
    public String upload(String name, InputStream inputStream, String contentType){
        // 上传文件
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType(contentType)
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(properties.getBucketName())
                    .object(name)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
        return properties.getEndpoint() + "/" + properties.getBucketName() + "/" + name;
    }
    /**
     * 删除文件
     * @param url
     */
    public void delete(String url){
        String objectName = url
            .replace(properties.getEndpoint()+"/","")
            .replace(properties.getBucketName()+"/","");

        RemoveObjectArgs args = RemoveObjectArgs
                .builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build();
        try {
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败: " + e.getMessage());
        }
    }
     /**
     * 下载文件
     * @param url
     * @return
     */
    public InputStream download(String url){
        String objectName = url
            .replace(properties.getEndpoint()+"/","")
            .replace(properties.getBucketName()+"/","");

        GetObjectArgs args = GetObjectArgs
             	.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build();

        InputStream inputStream = null;

        try {
            inputStream = minioClient.getObject(args);
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败: " + e.getMessage());
        }
        return inputStream;
    }
}
