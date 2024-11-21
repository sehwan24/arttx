package gp.arttx.service;

import gp.arttx.dto.HouseImageResponseDto;
import gp.arttx.dto.TreeImageResponseDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ImageService {

    @Autowired
    private WebClient webClient;

    public Mono<HouseImageResponseDto> getObjectDetection(String houseFileName) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("houseFileName", houseFileName);

        return webClient.post()
                .uri("/house")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(HouseImageResponseDto.class)
                .doOnSubscribe(subscription -> {
                    // 요청 정보 로그
                    System.out.println("Sending POST request to /house");
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


    public TreeImageResponseDto uploadTreeImage() {
        return new TreeImageResponseDto(); //todo
    }
}
