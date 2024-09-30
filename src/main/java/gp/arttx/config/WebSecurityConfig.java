package gp.arttx.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    // WebMvcConfigurer를 사용한 CORS 설정 (특정 경로에만 적용)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("https://api.artpings.com")  // 특정 도메인 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메소드
                        .allowedHeaders("Authorization", "Cache-Control", "Content-Type", "X-Requested-With")  // 허용할 헤더 지정
                        .exposedHeaders("Authorization", "Content-Disposition")  // 클라이언트가 접근할 수 있는 응답 헤더
                        .allowCredentials(true)  // 쿠키나 인증 관련 정보 허용
                        .maxAge(TimeUnit.DAYS.toSeconds(1));  // CORS 요청에 대한 브라우저 캐싱 시간 설정 (1일)
            }
        };
    }

    // CorsConfigurationSource를 사용한 CORS 설정 (전체 경로에 적용)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "https://www.artpings.com",  // 다른 출처도 허용
                "https://api.artpings.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));  // 허용할 HTTP 메서드 확장
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));  // 허용할 헤더 설정
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));  // 노출할 헤더 설정
        configuration.setAllowCredentials(true);  // 자격 증명 허용 (쿠키, 인증)
        configuration.setMaxAge(3600L);  // CORS 캐싱 시간 설정 (1시간)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 적용
        return source;
    }

    // Spring Security 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정 적용
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화 (REST API의 경우)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 세션 비활성화 (JWT 같은 상태 비저장 방식 사용 시)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Preflight 요청 허용
                        .anyRequest().permitAll())  // 모든 요청 허용 (추후 인증 필요 시 수정 가능)
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).preload(true)));  // HSTS 적용

        return http.build();
    }
}
