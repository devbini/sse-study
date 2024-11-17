package com.sse.demo.controller

import com.sse.demo.service.NotificationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

// 알림 구독 및 데이터 전송을 처리하는 컨트롤러
@RestController
@RequestMapping("/notifications")
class NotificationController(private val notificationService: NotificationService) {

    // 알림을 구독하는 엔드포인트
    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(): Flux<String> {
        return notificationService.getNotifications(); // 서비스에서 알림 스트림 전달
    }

    // POST /notifications/role
    @PostMapping("/{role}")
    fun notifyAdmin(@PathVariable role: String) {
        notificationService.sendNotification(role);
    }
}
