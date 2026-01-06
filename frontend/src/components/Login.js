import React, { useState } from 'react';
import '../styles/Login.css';
import { loginUser } from '../services/api';

const Login = ({ onLoginSuccess, onSwitchToRegister }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const credentials = { email, password };
      const response = await loginUser(credentials);

      console.log("Login Success:", response.data);
      onLoginSuccess(response.data);
    } catch (err) {
      console.error("Login Error:", err);
      const errorMsg = err.response?.data || "Server connection failed. Check console.";
      alert("Login failed: " + errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-brand">
        <h1>DevOps<br/>Dashboard</h1>
      </div>
      <div className="login-card">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              required
            />
          </div>
          <div className="input-group">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              required
            />
          </div>
          <button type="submit" className="login-btn" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>
        <p className="signup-text">
          Don't have an account?
          <button type="button" onClick={onSwitchToRegister} className="link-btn">Sign up</button>
        </p>
      </div>
    </div>
  );
};

export default Login;