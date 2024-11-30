package gp.arttx.service;

import gp.arttx.dto.ChattingMessageDto;
import gp.arttx.dto.FirstChattingDto;
import gp.arttx.dto.HouseImageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChattingService {

    @Autowired
    private WebClient webClient;

    public Mono<ChattingMessageDto> sendChatting(ChattingMessageDto chattingMessageDto) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("message", chattingMessageDto.getMessage());
        System.out.println("chattingMessageDto = " + chattingMessageDto.getMessage());

        return webClient.post()
                .uri("/chatting")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(ChattingMessageDto.class)
                .doOnSubscribe(subscription -> {
                    // 요청 정보 로그
                    System.out.println("Sending POST request to /chatting");
                    System.out.println("Request Body: " + requestBody);
                })
                .doOnNext(response -> {
                    // 성공적으로 응답을 받았을 때 로그
                    System.out.println("Response received: " + response);
                })
                .doOnError(WebClientResponseException.class, e -> {
                    // 에러 발생 시 로그
                    System.err.println("HTTP Status: " + e.getStatusCode());
                    System.err.println("Response Body: " + e.getResponseBodyAsString());
                })
                .doOnError(e -> {
                    // 기타 예외
                    System.err.println("An error occurred: " + e.getMessage());
                });
    }

    public Mono<FirstChattingDto> getFirstChatting() {

        return webClient.get()
                .uri("/first")
                .retrieve()
                .bodyToMono(FirstChattingDto.class)
                .doOnSubscribe(subscription -> {
                    // 요청 정보 로그
                    System.out.println("Sending GET request to /chatting");
                })
                .doOnNext(response -> {
                    // 성공적으로 응답을 받았을 때 로그
                    System.out.println("Response received: " + response);
                })
                .doOnError(WebClientResponseException.class, e -> {
                    // 에러 발생 시 로그
                    System.err.println("HTTP Status: " + e.getStatusCode());
                    System.err.println("Response Body: " + e.getResponseBodyAsString());
                })
                .doOnError(e -> {
                    // 기타 예외
                    System.err.println("An error occurred: " + e.getMessage());
                });

    }
}
