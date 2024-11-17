package com.sse.demo.controller

import com.sse.demo.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// 로그인 요청을 담당하는 컨트롤러입니다.
@RestController
@RequestMapping("/api")
class LoginController(private val loginService: LoginService) {

    // POST /api/login
    @PostMapping("/login")
    fun login(@RequestBody request: LoginService.LoginRequest): ResponseEntity<LoginService.LoginResponse> {
        println("입력, ${request.username}"); // 디버깅용
        return ResponseEntity.ok(loginService.authenticate(request));
    }
}
