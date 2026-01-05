import React, { useState } from 'react';
import '../styles/Login.css';
import { loginUser } from '../services/api'; // This uses the axios.create instance you shared

const Login = ({ onLoginSuccess, onSwitchToRegister }) => {
  // 1. Initialize state for the input fields
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // 2. Prepare the payload to match @RequestBody User in Java
      const credentials = { email, password }; 
      
      // 3. Call the API
      const response = await loginUser(credentials);
      
      // 4. Extract data and move to dashboard
      console.log("Login Success:", response.data);
      onLoginSuccess(response.data); 
    } catch (err) {
      console.error("Login Error:", err);
      // Accessing the error message from your Spring Boot ResponseEntity
      const errorMsg = err.response?.data || "Invalid email or password";
      alert("Login failed: " + errorMsg);
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
          <button type="button" className="forgot-pass-btn">Forgot password?</button>
          <button type="submit" className="login-btn">Login</button>
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