package com.sse.demo.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.SignalType
import reactor.core.publisher.Sinks

// 구독 엔터티 서비스입니다
@Service
class NotificationService {

    // 역할별로 스트림 관리 객체 생성
    private val sinksMap = mutableMapOf<String, Sinks.Many<String>>();

    // 특정 역할 구독자에게 알림 스트림 전달
    fun getNotifications(role: String): Flux<String> {
        val sink = sinksMap.getOrPut(role) { Sinks.many().multicast().onBackpressureBuffer(); }
        return sink.asFlux()
            .doFinally { signalType ->
                println("$role 스트림 종료 신호: $signalType")
                if (signalType == SignalType.CANCEL || signalType == SignalType.ON_COMPLETE) {
                    handleClientDisconnection(role)
                }
            }
            .doOnCancel {
                println("$role 구독 취소됨.")
            }
    }



    // 특정 역할 대상에게 알림 전송
    fun sendNotification(sourceRole: String, targetRole: String) {
        val sink = sinksMap[targetRole];

        if (sink == null || sink.currentSubscriberCount() == 0) {
            println("연결정보 없음 : $targetRole");
            return;
        }

        try {
            sink.emitNext(
                "$sourceRole 으로부터 알림 받음!",
                Sinks.EmitFailureHandler { signalType, emitResult ->
                    println("전송 실패: $emitResult")
                    emitResult != Sinks.EmitResult.FAIL_NON_SERIALIZED
                }
            );
        } catch (ex: Exception) {
            println("$targetRole 중단 감지: ${ex.message}");
            handleClientDisconnection(targetRole);
        }
    }

    // 클라이언트 강제 종료 또는 연결 해제 처리
    private fun handleClientDisconnection(role: String) {
        println("$role 스트림 제거 중...");
        sinksMap.remove(role);
    }
}