import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import './App.css';

function App() {
  // Initialize state from localStorage to prevent "Session Expired" on refresh
  const [currentUser, setCurrentUser] = useState(() => {
    const savedUser = localStorage.getItem('user');
    return savedUser ? JSON.parse(savedUser) : null;
  });

  const [isRegistering, setIsRegistering] = useState(false);

  // Function to handle successful login
  const handleLoginSuccess = (userData) => {
    localStorage.setItem('user', JSON.stringify(userData)); // Save to browser storage
    setCurrentUser(userData);
  };

  // Function to handle logout
  const handleLogout = () => {
    localStorage.removeItem('user'); // Clear storage
    setCurrentUser(null);
  };

  return (
    <Router>
      <div className="app">
        <Routes>
          {/* If logged in, go to Dashboard. If not, show Login/Register */}
          <Route 
            path="/" 
            element={
              currentUser ? (
                <Navigate to="/dashboard" />
              ) : isRegistering ? (
                <Register onSwitchToLogin={() => setIsRegistering(false)} />
              ) : (
                <Login 
                  onLoginSuccess={handleLoginSuccess} 
                  onSwitchToRegister={() => setIsRegistering(true)} 
                />
              )
            } 
          />

          {/* Dashboard Route - Passing currentUser and Logout function */}
          <Route 
            path="/dashboard/*" 
            element={
              currentUser ? (
                <Dashboard currentUser={currentUser} onLogout={handleLogout} />
              ) : (
                <Navigate to="/" />
              )
            } 
          />

          {/* Fallback for undefined routes */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;