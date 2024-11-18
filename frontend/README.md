# 🎈 Frontend
- React 단일

## 🎈 시연
> ![최종 시연화면](https://github.com/user-attachments/assets/91d77fe8-7de5-41d8-a13a-ecaf97e9fc03)

## 🎈 간단 설명
### 1. 기본 화면
> **Spring Security에 하드코딩 되어있는 계정 정보**를 통해 로그인합니다.  
> 관리자 계정의 경우...  
> ID : admin  
> PW : pw  
> 
> ![image](https://github.com/user-attachments/assets/40fcf47d-e84a-4952-a565-f5cc15aa2628)

### 2. 관리자 로그인 화면
> ### User*에게 알림 보내기?
> 해당 Role을 가진 이용자들에게 알림을 전송합니다.
>
> ### 알림 받는 중?
> 클릭시 알림 ON/OFF 토글 기능을 담당합니다.
>
> ### 로그아웃?
> 말 그대로 초기 화면으로 돌아갑니다. (1)  
> 
> ![image](https://github.com/user-attachments/assets/bf8ff41b-3b25-4b7b-93c0-7f032b217961)

### 3. 유저 로그인 화면
> ### 어드민에게 알림 보내기?
> ADMIN Role 사용자에게 알림을 전송합니다.
>
> ### 알림 받는 중?
> 클릭시 알림 ON/OFF 토글 기능을 담당합니다.
>
> ### 로그아웃?
> 말 그대로 초기 화면으로 돌아갑니다. (1)  
> 
> ![image](https://github.com/user-attachments/assets/b6bf69ef-8e9e-400b-84e5-0ed246c16ec3)

### 4. 알림 전송 화면
> list의 형태로 로그아웃 버튼 아래에 정렬되어 표시됩니다.
> ![image](https://github.com/user-attachments/assets/a0cad294-cca8-4e01-a5f8-559cf6d6a502)

## 🎈 프로젝트 시작 방법
### 1. "./front" 폴더 내에서 의존성 설치
```
npm i 
|| (or, 또는)
npm install
```

### 2. 리액트 프로젝트 시작
```
npm start
```

### 3. CORS 무시 브라우저 실행 (Chrome)
- Window
```
"C:\Program Files\Google\Chrome\Application\chrome.exe" --disable-web-security --user-data-dir="C:\chrome_dev"
```
- MAC
```
open -na "Google Chrome" --args --disable-web-security --user-data-dir="~/chrome_dev"
```
- Ubuntu
```
google-chrome --disable-web-security --user-data-dir="/home/your-username/chrome_dev"
```

### 4. 페이지 접속
```
localhost:3000
```

## 🎈 주요 코드 일부 설명
- 대부분 주석이 있긴 하지만, App과 Notification 일부를 서술합니다.

> ### App.tsx
> 로그인 성공 시 user 변수에 구조체를 집어넣습니다.  
> 데이터가 있으면 Noti... 화면을 보여주도록 구현했어요.
> ```
> const [user, setUser] = useState<{ username: string; role: string } | null>(null); // 로그인 상태관리
> 
> ...
>
> {user ? (
>   <Notifications role={user.role} onLogout={handleLogout} />
> ) : (
>   <Login onLogin={setUser} />
> )}
> ```

> ### Notification.tsx
> notifications : 알림 온 데이터 목록을 저장합니다. 배열 형태로...  
> eventSource : 회의때도 이야기 했었던 이벤트 소스입니다. SSE 연결 상태를 이거로 관리해요.  
> isReceiving : 알림을 받을지 안 받을지를 설정하는 토글 변수입니다.  
>  
> **sendNotification (함수) : 알림 메시지를 전송합니다.**  
>   sourcerole : 전송하는 사람의 role. 최종 구현에선 이름이 되겠죠  
>   targetRole : 전송 대상 role.
>
> **startSSE (함수) : SSE 연결을 시작합니다.** -> toggle에서 호출, **기본값.**   
>   이게 onerror로 강제 종료 상태를 처리 해 두긴 했는데, 제대로 동작을 안 하더라구요..?  
>
> **stopSSE (함수) : SSE 연결을 중단합니다.** -> toggle에서 호출  
>   이벤트 소스를 닫습니다.  
>
> ```
>  // SSE 알림 데이터 전송
>  const sendNotification = async (targetRole: string) => {
>    try {
>      await fetch(`http://localhost:8080/notifications/send`, {
>        method: "POST",
>        headers: {
>          "Content-Type": "application/json",
>        },
>        body: JSON.stringify({ sourceRole: role, targetRole }),
>      });
>    } catch (error) {
>      console.error("알림 전송 실패 원인 :", error);
>    }
>  };
> ```

