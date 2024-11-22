package gp.arttx.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3FileUploadService {


    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /*public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            fileUrls.add(uploadFile(file));
        }
        return fileUrls;
    }*/

    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = convertMultiPartFileToFile(multipartFile);
        // String fileName = generateFileName(multipartFile);
        // System.out.println("bucketName = " + bucketName);
        //String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, amazonS3Client.getRegionName(), fileName);
        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        //file.delete(); // 임시 파일 삭제
        return fileName;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }

    private String generateFileName(MultipartFile multipartFile) {
        return UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
    }

    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3Client.deleteObject(bucketName, fileName);
    }

}
