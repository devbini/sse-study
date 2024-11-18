# 🎈 Backend
- Java 17
- Spring Boot & Kotlin
- Gradle

## 🎈 시연
> ![시연화면_백](https://github.com/user-attachments/assets/a3803aa8-70f5-4881-bc8a-18f68e3d6a77)

## 🎈 프로젝트 시작 방법
### 1. InteliJ로 프로젝트 오픈 후 빌드
```
./gradlew build
```

### 2. 시작
```
./gradlew bootRun
```

## 🎈 주요 코드 일부 설명
- 대부분 주석이 있긴 하지만 일부를 서술합니다.
- MVC 기반으로 설계 되어있습니다.

> ### SecurityConfig.kt
> Spring Security 세팅이 있습니다.  
> 여기에 사전 설정 된 ID와 PW가 있어요.  
> 비밀번호는 기본 암호학을 사용하여 인코딩하고 있습니다. (그냥 하고싶어서 했어요)
> 
> ```
>val rawPassword = "pw";
>
>        val users = listOf(
>            User.withUsername("admin")
>                .password(passwordEncoder.encode(rawPassword))
>                .roles("ADMIN")
>                .build(),
>            User.withUsername("userB")
>                .password(passwordEncoder.encode(rawPassword))
>                .roles("userB")
>                .build(),
>            User.withUsername("userC")
>                .password(passwordEncoder.encode(rawPassword))
>                .roles("userC")
>                .build(),
>            User.withUsername("userD")
>                .password(passwordEncoder.encode(rawPassword))
>                .roles("userD")
>                .build()
>        );
> ```

> ### DTOs
> ```
> LoginRequest(val username: String, val password: String);
> LoginResponse(val username: String, val role: String);
> ```

> ### LoginService.kt
> 간단합니다. 위 DTO는 여기에서 사용해요.
>   ```
>    // LoginRequest: 사용자명과 비밀번호
>    // LoginResponse: 인증된 사용자 정보와 역할(role)
>    
>    fun authenticate(request: LoginRequest): LoginResponse
>    ```

> ### NotificationService.kt
> **getNotifications (함수) : 구독자 구독 상태를 처리합니다.**  
>   role : 구독하는 역할입니다.
>
> **sendNotification (함수) : SSE 전송 함수.**  
>   sourceRole: String, targetRole: String