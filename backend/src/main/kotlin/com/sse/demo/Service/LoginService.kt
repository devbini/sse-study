package com.sse.demo.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

// 로그인 요청을 담당하는 서비스입니다.
@Service
class LoginService(private val authenticationManager: AuthenticationManager) {

    // 로그인 요청 DTO
    data class LoginRequest(val username: String, val password: String);

    // 로그인 응답 DTO
    data class LoginResponse(val username: String, val role: String);

    // LoginRequest: 사용자명과 비밀번호
    // LoginResponse: 인증된 사용자 정보와 역할(role)
    fun authenticate(request: LoginRequest): LoginResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(request.username, request.password);
        val authentication: Authentication = authenticationManager.authenticate(authenticationToken);

        val user = authentication.principal as UserDetails;
        val role = user.authorities.first().authority.replace("ROLE_", ""); // 자동으로 붙는 ROLE_ 글자 삭제

        return LoginResponse(user.username, role);
    }
}
