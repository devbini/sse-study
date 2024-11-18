package com.sse.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig {

    // 필터 체인인데.. 일단 CSRF & CORS 설정만 해 둔 상태입니다.
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() } // CSRF 비활성화
            .cors { it.disable() } // CORS 비활성화
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll() // 모든 요청 허용
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless 세션
            }
        return http.build();
    }


    // 기본 암호화 적용
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder();

    // 테스트 DB를 만들까 말까 하다가 일단 하드코딩으로 설정.
    // User 로그인 후 요청 보내면 ADMIN으로 데이터 전송 해야 함. (실제 배포 환경 대비)
    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val rawPassword = "pw";

        val users = listOf(
            User.withUsername("admin")
                .password(passwordEncoder.encode(rawPassword))
                .roles("ADMIN")
                .build(),
            User.withUsername("userB")
                .password(passwordEncoder.encode(rawPassword))
                .roles("userB")
                .build(),
            User.withUsername("userC")
                .password(passwordEncoder.encode(rawPassword))
                .roles("userC")
                .build(),
            User.withUsername("userD")
                .password(passwordEncoder.encode(rawPassword))
                .roles("userD")
                .build()
        );
        return InMemoryUserDetailsManager(users);
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager;
    }
}

