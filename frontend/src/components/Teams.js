import React, { useEffect, useState } from 'react';
import '../styles/Teams.css';
import { getAllUsers } from '../services/api';

const Teams = () => {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const getTeams = async () => {
            try {
                const res = await getAllUsers();
                // FIX: Use res.data instead of data
                setUsers(res.data); 
            } catch (err) {
                console.error("Failed to load teams", err);
            }
        };
        getTeams();
    }, []);

    return (
        <div className="teams-container">
            <header className="teams-header">
                <h1>Team Management</h1>
                <button className="add-btn">+ Add Member</button>
            </header>
            <div className="teams-grid">
    {users.length > 0 ? (
        users.map((user) => (
            <div key={user.id} className="team-card">
                {/* ... your existing card code ... */}
            </div>
        ))
    ) : (
        <p>No team members found.</p>
    )}
</div>
        </div>
    );
};

export default Teams;