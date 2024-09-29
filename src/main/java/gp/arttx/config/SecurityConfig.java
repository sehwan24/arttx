package gp.arttx.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)// FormLogin 사용 X
                .httpBasic(AbstractHttpConfigurer::disable)// httpBasic 사용 X
                .csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(cors -> {
                    cors.configurationSource(request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.addAllowedOrigin("http://localhost:3000");
                        config.addAllowedOrigin("https://sehwan24.github.io/arttx_fe");
                        config.addAllowedOrigin("http://127.0.0.1:3000");
                        config.addAllowedOrigin("https://artpings.com");
                        config.addAllowedOrigin("https://www.artpings.com");
                        config.addAllowedMethod("GET");
                        config.addAllowedMethod("POST");
                        config.addAllowedMethod("PUT");
                        config.addAllowedMethod("DELETE");
                        config.addAllowedMethod("OPTIONS");
                        config.addAllowedHeader(CorsConfiguration.ALL);
                        config.setAllowCredentials(true);
                        return config;
                    });
                })


                // 세션 사용하지 않으므로 STATELESS로 설정


                //== URL별 권한 관리 옵션 ==//
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()); // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능


        return http.build();
    }

}
