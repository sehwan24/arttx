package gp.arttx.controller;

import gp.arttx.dto.TreeImageResponseDto;
import gp.arttx.response.ApiResponse;
import gp.arttx.response.SuccessCode;
import gp.arttx.service.ImageService;
import gp.arttx.service.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/image")
public class ImageController {




    private final ImageService imageService;
    private final S3FileUploadService s3FileUploadService;

    @PostMapping(value = "/house", consumes = "multipart/form-data")
    public Mono<ResponseEntity<ApiResponse>> houseUpload(@RequestPart(value = "image", required = true) MultipartFile houseImage) throws IOException {
        String houseFileName = null;
        if (!houseImage.isEmpty()) {
            houseFileName = s3FileUploadService.uploadFile(houseImage, "house.jpg");
        }

        // `getObjectDetection`의 결과를 비동기적으로 처리
        return imageService.getHouseObjectDetection(houseFileName)
                .map(houseImageResponseDto -> {
                    SuccessCode successCode = SuccessCode.OK; // todo: SuccessCode 생성
                    return ResponseEntity.status(successCode.getHttpStatus())
                            .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), houseImageResponseDto));
                });
    }



    @PostMapping(value = "/tree", consumes = "multipart/form-data")
    public Mono<ResponseEntity<ApiResponse>> treeUpload(@RequestPart(value = "image", required = true) MultipartFile treeImage) throws IOException {
        String treeFileName = null;
        if (!treeImage.isEmpty()) {
            treeFileName = s3FileUploadService.uploadFile(treeImage, "tree.jpg");
        }

        return imageService.getTreeObjectDetection(treeFileName)
                .map(treeImageResponseDto -> {
                    SuccessCode successCode = SuccessCode.OK;
                    return ResponseEntity.status(successCode.getHttpStatus())
                            .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), treeImageResponseDto));
                });
    }


    @PostMapping(value = "/person", consumes = "multipart/form-data")
    public Mono<ResponseEntity<ApiResponse>> personUpload(@RequestPart(value = "image", required = true) MultipartFile personImage) throws IOException {
        String personFileName = null;
        if (!personImage.isEmpty()) {
            personFileName = s3FileUploadService.uploadFile(personImage, "person.jpg");
        }

        return imageService.getPersonObjectDetection(personFileName)
                .map(personImageResponseDto -> {
                    SuccessCode successCode = SuccessCode.OK;
                    return ResponseEntity.status(successCode.getHttpStatus())
                            .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), personImageResponseDto));
                });
    }

}
