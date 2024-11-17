package com.sse.demo.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

// 알림 구독 및 데이터 전송을 처리하는 서비스
@Service
class NotificationService {

    // 알림 스트림 관리 객체 (멀티캐스트, 싱글로도 운영 가능! << Admin 에게만 가는건지, 일반 사용자에게도 가는건지 확인이 필요해서...)
    private val adminSink = Sinks.many().multicast().onBackpressureBuffer<String>()

    // 구독자에게 알림 스트림을 전달하는 메서드
    fun getNotifications(): Flux<String> {
        return adminSink.asFlux();
    }

    // 알림을 전송하는 메서드
    fun sendNotification(role: String) {
        adminSink.tryEmitNext("Notification from $role user");
    }
}
