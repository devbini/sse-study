import React, { useState } from "react";
import Login from "./components/Login";
import Notifications from "./components/Notification";
import "./App.css";

const App: React.FC = () => {
  const [user, setUser] = useState<{ username: string; role: string } | null>(null); // 로그인 상태관리

  const handleLogout = () => {
    setUser(null);  
  };

  return (
    <div>
      {user ? (
        // 로그인 후
        <Notifications role={user.role} onLogout={handleLogout} />
      ) : (
        // 로그인 전
        <Login onLogin={setUser} />
      )}
    </div>
  );
};

export default App;
