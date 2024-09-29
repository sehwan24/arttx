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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("https://www.artpings.com");  // 허용할 도메인 추가
        config.addAllowedMethod("*");  // 모든 HTTP 메서드 허용 (GET, POST, PUT 등)
        config.addAllowedHeader("*");  // 모든 헤더 허용
        config.setAllowCredentials(true);  // 자격 증명 허용 (쿠키, 인증 정보 등)

        source.registerCorsConfiguration("/api/**", config);  // /api/** 경로에 CORS 설정 적용
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)// FormLogin 사용 X
                .httpBasic(AbstractHttpConfigurer::disable)// httpBasic 사용 X
                .csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))



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
