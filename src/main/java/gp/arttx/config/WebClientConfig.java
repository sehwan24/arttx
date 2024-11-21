package gp.arttx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.data.python-server.address}")
    private String address;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(address)
                .filter((request, next) -> {
                    System.out.println("Request: " + request.method() + " " + request.url());
                    return next.exchange(request)
                            .doOnNext(response -> System.out.println("Response status: " + response.statusCode()));
                })
                .build();
    }

}
