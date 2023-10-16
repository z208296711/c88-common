package com.c88.storage.service;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.StringJoiner;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class GCPFileUploadService {

    private final Storage storage;

    private final String[] imageExtensions = {"png", "jpg", "jpeg", "gif", "svg", "webp"};

    private String bucket;

    @Value("${spring.profiles.active:local}")
    private String profile;

    @PostConstruct
    public void init() {
        switch (profile) {
            case "k8s_prod":
                this.bucket = "prod-comebet";
                break;
            case "k8s_stg":
                this.bucket = "stg-comebet";
                break;
            case "k8s_pre":
                this.bucket = "pre-comebet";
                break;
            default:
                this.bucket = "dev-comebet";
        }
    }

    public Boolean delete(String fileOrigin) {
        BlobId blobId = BlobId.of(bucket, fileOrigin);
        return storage.delete(blobId);
    }

    public Blob upload(MultipartFile file, String filePath, boolean isImage) {
        String filename = file.getOriginalFilename();
        String subName = StringUtils.substringAfterLast(filename, ".");
        if (isImage && !StringUtils.equalsAnyIgnoreCase(subName, imageExtensions)) {
            return null;
        }

        try (InputStream fileInput = file.getInputStream()) {
            StringJoiner fileOrigin = new StringJoiner("/")
                    .add(filePath)
                    .add(filename);

            BlobId blobId = BlobId.of(bucket, fileOrigin.toString());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            Blob blob = storage.get(BlobId.of(bucket, filePath +"/"+ filename));
            if(blob == null) {
                blob = storage.create(blobInfo, fileInput.readAllBytes());
                storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            } else{
                WritableByteChannel channel = blob.writer();
                channel.write(ByteBuffer.wrap(fileInput.readAllBytes()));
                channel.close();
//                blob = storage.get(BlobId.of(bucket, filePath +"/"+ filename));
            }
            return blob;
        } catch (Exception e) {
            log.error("UploadUtil error", e);
        }

        return null;
    }

}
