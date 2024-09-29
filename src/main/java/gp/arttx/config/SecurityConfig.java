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
                        config.addAllowedHeader("*");
                        config.setAllowCredentials(true);
                        return config;
                    });
                })


                // 세션 사용하지 않으므로 STATELESS로 설정


                //== URL별 권한 관리 옵션 ==//
                .authorizeHttpRequests(authorize -> authorize
                        // 아이콘, css, js 관련
                        // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/favicon.ico")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/profile")).permitAll()
                        //, "/js/**", "/favicon.ico", "/h2-console/**", "/profile")).permitAll()))
                        //.requestMatchers("/api/member/sign-up").permitAll() // 회원가입 접근 가능
                        //.requestMatchers("/auth/token").permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/auth/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/swagger-ui/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/v3/api-docs/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/*")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/chatting/**")).permitAll()  //임시 권한 부여
                        .anyRequest().permitAll()); // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능


        return http.build();
    }

}
