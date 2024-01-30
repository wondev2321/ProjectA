package com.nndev.projecta.config;

import com.nndev.projecta.auth.security.filter.JwtAuthenticationFilter;
import com.nndev.projecta.auth.security.jwt.CustomAuthenticationEntryPoint;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, final CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
                //CSRF 보호를 비활성화
                .csrf(csrf -> csrf.disable())

                //세션을 생성하지 않고 세션을 사용하지 않는 Stateless방식의 보안을 설정합니다.
                //API 기반 인증에서는 세션이 필요하지 않으므로
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //URL 패턴 별로 권한 부여
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/members/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/members").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/newsfeeds").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/mail").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/refreshToken").permitAll()
                        .anyRequest().authenticated())

                //커스텀 JWT인증 필터인 jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 추가한다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                //인증 실패ㅣ 발생하는 예외를 처리하는 핸들러 customAuthenticationEntryPoint를 설정
                .exceptionHandling(handle -> handle.authenticationEntryPoint(customAuthenticationEntryPoint))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
