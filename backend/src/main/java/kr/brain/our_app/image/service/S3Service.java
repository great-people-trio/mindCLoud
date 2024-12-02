package kr.brain.our_app.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String bookmarkId , String imageUrl) throws IOException {
        String fileName = bookmarkId + "_" + imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        //profile.png가 들어오면 550e8400-e29b-41d4-a716-446655440000_profile.png 로 출력된다.
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucketName, fileName).toString();

        //여기에 경로에 접근해서 이미지 불러오지 못하는 예외의 경우, s3에 있는 기본이미지 경로를 넣어준다.
    }

    public List<String> listFiles() {
        List<String> fileUrls = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            String fileUrl = s3Client.getUrl(bucketName, os.getKey()).toString();
            fileUrls.add(fileUrl);
        }
        return fileUrls;
    }

    // 파일 삭제
    public void deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

}
