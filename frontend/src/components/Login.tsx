import React, { useState } from "react";

// 비밃번호 구조체
interface LoginProps {
  onLogin: (data: { username: string; role: string }) => void;
}

const Login: React.FC<LoginProps> = ({ onLogin }) => {
  const [username, setUsername] = useState<string>(""); // 사용자 이름
  const [password, setPassword] = useState<string>(""); // 비밀번호

  // 로그인 버튼 클릭 시 호출
  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        throw new Error("서버 에러 발생");
      }

      const data = await response.json();
      onLogin(data);
    } catch (error) {
      console.error(error); // 로그인 실패 로그 (디버그용)
      alert("계정 정보 불일치!");
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default Login;
