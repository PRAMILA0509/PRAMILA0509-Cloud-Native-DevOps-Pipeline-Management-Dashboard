import React, { useState } from 'react';
import { LayoutDashboard, Users, GitBranch, LogOut } from 'lucide-react';
import Overview from './Overview';
import Teams from './Teams';
import Repos from './Repos';
import '../styles/Dashboard.css';

const Dashboard = ({ currentUser, onLogout }) => {
    const [activeTab, setActiveTab] = useState('overview');

    return (
        <div className="dashboard-container">
            <aside className="sidebar">
                <div className="sidebar-logo">
                    <h2>DevOps Pro</h2>
                </div>
                <nav className="sidebar-nav">
                    <button className={activeTab === 'overview' ? 'active' : ''} onClick={() => setActiveTab('overview')}>
                        <LayoutDashboard size={20} /> Overview
                    </button>
                    <button className={activeTab === 'teams' ? 'active' : ''} onClick={() => setActiveTab('teams')}>
                        <Users size={20} /> Teams
                    </button>
                    <button className={activeTab === 'repos' ? 'active' : ''} onClick={() => setActiveTab('repos')}>
                        <GitBranch size={20} /> Repositories
                    </button>
                </nav>
                <button className="logout-btn" onClick={onLogout}>
                    <LogOut size={20} /> Logout
                </button>
            </aside>

            <main className="main-content">
                <header className="main-header">
                    <h1>{activeTab.toUpperCase()}</h1>
                    <div className="user-profile">
                        <span>{currentUser?.name}</span>
                        <div className="avatar">{currentUser?.name?.charAt(0)}</div>
                    </div>
                </header>

                <div className="dashboard-content">
                    {activeTab === 'overview' && <Overview />}
                    {activeTab === 'teams' && <Teams />}
                    {/* Pass currentUser so Repo Registration works */}
                    {activeTab === 'repos' && <Repos currentUser={currentUser} />} 
                </div>
            </main>
        </div>
    );
};

export default Dashboard;