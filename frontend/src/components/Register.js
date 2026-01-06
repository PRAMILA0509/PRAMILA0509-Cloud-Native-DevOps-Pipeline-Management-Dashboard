import React, { useState } from 'react';
import '../styles/Login.css'; // Reusing styles for consistency
import axios from 'axios';
import { registerUser } from '../services/api';
const Register = ({ onSwitchToLogin }) => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    githubUsername: ''
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await registerUser(formData);
      alert("Registration successful! You can now login.");
      onSwitchToLogin();
    } catch (err) {
      alert("Registration failed. Email might already exist.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-brand">
        <h1>DevOps<br/>Dashboard</h1>
      </div>
      <div className="login-card">
        <h2>Sign Up</h2>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label>Full Name</label>
            <input type="text" onChange={(e) => setFormData({...formData, name: e.target.value})} required />
          </div>
          <div className="input-group">
            <label>Email</label>
            <input type="email" onChange={(e) => setFormData({...formData, email: e.target.value})} required />
          </div>
          <div className="input-group">
            <label>Password</label>
            <input type="password" onChange={(e) => setFormData({...formData, password: e.target.value})} required />
          </div>
          <div className="input-group">
            <label>GitHub Username</label>
            <input type="text" onChange={(e) => setFormData({...formData, githubUsername: e.target.value})} />
          </div>
          <button type="submit" className="login-btn">Create Account</button>
        </form>
        <p className="signup-text">Already have an account? <button onClick={onSwitchToLogin} className="link-btn">Login</button></p>
      </div>
    </div>
  );
};

export default Register;