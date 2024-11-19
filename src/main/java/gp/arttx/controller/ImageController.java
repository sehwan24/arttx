package gp.arttx.controller;

import gp.arttx.dto.HouseImageResponseDto;
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

    //todo : 이미지 하나씩 보낼지 or 한번에 보낼  선택 + S3 보낸 걸 파이썬이 S3 직접 접근 / 제출하기 버튼이 트리거인지..



    private final ImageService imageService;
    private final S3FileUploadService s3FileUploadService;

    @PostMapping(value = "/house", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> houseUpload(@RequestPart(value = "image", required = true) MultipartFile houseImage) throws IOException {
        String houseFileName = null;
        if (!houseImage.isEmpty()) {
            houseFileName = s3FileUploadService.uploadFile(houseImage);
        }
        Mono<HouseImageResponseDto> houseImageResponseDto = imageService.getObjectDetection(houseFileName);
        SuccessCode successCode = SuccessCode.OK; //todo : successcode 만들기
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), houseImageResponseDto));
    }


    @PostMapping(value = "/tree", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> treeUpload(@RequestPart(value = "image", required = true) MultipartFile treeImage) throws IOException {

        TreeImageResponseDto treeImageResponseDto = imageService.uploadTreeImage();
        SuccessCode successCode = SuccessCode.OK;
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), treeImageResponseDto));
    }


}
