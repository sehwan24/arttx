package gp.arttx.controller;

import gp.arttx.dto.ChattingMessageDto;
import gp.arttx.response.ApiResponse;
import gp.arttx.response.SuccessCode;
import gp.arttx.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/chatting")
public class ChattingController {

    private final ChattingService chattingService;


    @PostMapping(value = "/new")
    public Mono<ResponseEntity<ApiResponse>> sendChatting(ChattingMessageDto chattingMessageDto) {

        System.out.println("chattingMessageDto = " + chattingMessageDto);
        System.out.println("chattingMessageDto.getMessage() = " + chattingMessageDto.getMessage());

        // `getObjectDetection`의 결과를 비동기적으로 처리
        return chattingService.sendChatting(chattingMessageDto)
                .map(chattingResponseMessageDto -> {
                    SuccessCode successCode = SuccessCode.OK; // todo: SuccessCode 생성
                    return ResponseEntity.status(successCode.getHttpStatus())
                            .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), chattingResponseMessageDto));
                });
    }

    @GetMapping(value = "/first")
    public Mono<ResponseEntity<ApiResponse>> getFirstChatting() {

        // `getObjectDetection`의 결과를 비동기적으로 처리
        return chattingService.getFirstChatting()
                .map(firstChattingResponseDto -> {
                    SuccessCode successCode = SuccessCode.OK; // todo: SuccessCode 생성
                    return ResponseEntity.status(successCode.getHttpStatus())
                            .body(ApiResponse.of(successCode.getCode(), successCode.getMessage(), firstChattingResponseDto));
                });
    }
}
