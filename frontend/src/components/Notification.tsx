import React, { useCallback, useEffect, useState } from "react";

interface NotificationsProps {
  role: string; // 사용자 역할
  onLogout: () => void; // 로그아웃 함수
}

const Notifications: React.FC<NotificationsProps> = ({ role, onLogout }) => {
  const [notifications, setNotifications] = useState<string[]>([]); // 알림목록
  const [eventSource, setEventSource] = useState<EventSource | null>(null); // SSE 연결 상태
  const [isReceiving, setIsReceiving] = useState<boolean>(true); // 기본 알림 받기 상태

  // SSE 알림 데이터 전송
  const sendNotification = async (targetRole: string) => {
    try {
      await fetch(`http://localhost:8080/notifications/send`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ sourceRole: role, targetRole }),
      });
    } catch (error) {
      console.error("알림 전송 실패 원인 :", error);
    }
  };

  // SSE 연결
  const startSSE = useCallback(() => {
    if (eventSource) {
      return;
    }
    const newEventSource = new EventSource(
      `http://localhost:8080/notifications/${role}`
    );
    setEventSource(newEventSource);

    newEventSource.onmessage = (event: MessageEvent) => {
      setNotifications((prev) => [...prev, event.data]); // 알림 추가
    };

    newEventSource.onerror = () => {
      newEventSource.close();
      console.log("SSE 연결 강제 종료됨 (onerror)");
      setEventSource(null); // 상태 초기화
    };
  }, [eventSource, role]);

  // SSE 종료
  const stopSSE = useCallback(() => {
    if (eventSource) {
      eventSource.close();
      setEventSource(null); // 상태 초기화
    } else {
      console.log("이미 SSE 없음");
    }
  }, [eventSource]);

  // SSE 구독 토글
  const toggleNotifications = () => {
    if (isReceiving) {
      stopSSE(); // 알림 받기 중지
    } else {
      startSSE(); // 알림 받기 시작
    }
    setIsReceiving(!isReceiving);
  };

  // 처음 실행 시
  useEffect(() => {
    if (isReceiving) {
      startSSE(); // 기본적으로 알림 받기 시작
    }

    return () => {
      console.log("SSE 연결 종료");
      stopSSE();
    };
  }, [isReceiving, startSSE, stopSSE]);

  // 로그아웃 토글
  const handleLogout = () => {
    stopSSE(); // SSE 연결 종료
    onLogout();
  };

  return (
    <div className="container">
      {role === "ADMIN" ? (
        <h1>관리자입니다.</h1>
      ) : (
        <h3>사용자입니다. (Role : {role})</h3>
      )}
      {role === "ADMIN" ? (
        <div>
          <button onClick={() => sendNotification("userB")}>
            userB에게 알림 보내기
          </button>
          <br />
          <br />
          <button onClick={() => sendNotification("userC")}>
            userC에게 알림 보내기
          </button>
          <br />
          <br />
          <button onClick={() => sendNotification("userD")}>
            userD에게 알림 보내기
          </button>
        </div>
      ) : (
        <button onClick={() => sendNotification("ADMIN")}>
          어드민에게 알림 보내기
        </button>
      )}

      <br />
      <br />
      <div>
        <button onClick={toggleNotifications} className={isReceiving ? "noti" : "nonenoti"}>
          {isReceiving ? "알림 받는 중" : "알림 안 받는 중"}
        </button>
        <button onClick={handleLogout} style={{ marginLeft: "10px" }}>
          로그아웃
        </button>
      </div>
      <ul>
        {notifications.map((notification, index) => (
          <li key={index}>
            {index} : {notification}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Notifications;
