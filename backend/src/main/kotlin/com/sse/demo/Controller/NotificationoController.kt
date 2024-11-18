package com.sse.demo.controller

import com.sse.demo.service.NotificationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

// 구독 엔터티 컨트롤러입니다
@RestController
@RequestMapping("/notifications")
class NotificationController(private val notificationService: NotificationService) {

    // 입력 DTO
    data class NotiRequest(val sourceRole: String, val targetRole: String);

    // SSE 구독 엔드포인트 (GET)
    @GetMapping("/{role}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(@PathVariable role: String): Flux<String> {
        println("SSE 구독 시작: $role");
        return notificationService.getNotifications(role)
            .doOnSubscribe { println("$role 구독자가 연결되었습니다."); }
            .doFinally { println("$role 구독이 종료되었습니다."); }
    }


    // POST /notifications/send
    @PostMapping("/send")
    fun notifyRole(@RequestBody request: NotiRequest) {
        println("${request.sourceRole}이 ${request.targetRole} 에게 알림 전송!");
        notificationService.sendNotification(request.sourceRole, request.targetRole);
    }
}