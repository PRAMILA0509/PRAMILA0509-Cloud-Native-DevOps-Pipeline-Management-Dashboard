import { useState } from "react";
import "../styles/Login.css";

function AuthForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [isLogin, setIsLogin] = useState(true);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    const url = isLogin
      ? "http://localhost:8080/api/users/login"
      : "http://localhost:8080/api/users";

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Request failed");
      }

      const data = await response.json();

      if (isLogin) {
        console.log("Logged in user:", data);
        alert("Login successful!");
        // TODO: store JWT / redirect
      } else {
        console.log("User created:", data);
        alert("Sign up successful! Please login.");
        setIsLogin(true);
      }

      setEmail("");
      setPassword("");
    } catch (err) {
      console.error(err);
      setError(err.message);
    }
  };

  return (
    <div className="login-container">
      <div className="login-left">
        <h1>
          DevOps Pipeline<br />Management Dashboard
        </h1>
      </div>

      <div className="login-right">
        <div className="login-card">
          <h2>{isLogin ? "Login" : "Sign Up"}</h2>

          <form onSubmit={handleSubmit}>
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />

            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            {error && <p className="error">{error}</p>}

            <button type="submit">
              {isLogin ? "Login" : "Create Account"}
            </button>
          </form>

          <p className="signup">
            {isLogin ? "Don’t have an account?" : "Already have an account?"}
            <span onClick={() => setIsLogin(!isLogin)}>
              {isLogin ? " Sign up" : " Login"}
            </span>
          </p>
        </div>
      </div>
    </div>
  );
}

export default AuthForm;
