import React, { useState, useEffect } from 'react';
import { registerRepo, getAllRepos } from '../services/api';
import { Plus, ExternalLink } from 'lucide-react';

const Repos = ({ currentUser }) => {
    const [repos, setRepos] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        url: '',
        owner: '',
        defaultBranch: 'main'
    });

    useEffect(() => {
        loadRepos();
    }, []);

    const loadRepos = async () => {
        const res = await getAllRepos();
        setRepos(res.data);
    };

    const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Debugging: Open your browser console (F12) to see this
    console.log("Logged in User:", currentUser);

    if (!currentUser || !currentUser.id) {
        alert("Session expired. Please log out and log back in.");
        return;
    }

    try {
        // This matches @PostMapping("/register/{userId}") in your Java controller
        await registerRepo(currentUser.id, formData);
        alert("Repository Registered!");
        loadRepos();
    } catch (err) {
        console.error("Full Error Object:", err);
        alert("Failed to register. Check console for details.");
    }
};
    return (
        <div className="repos-container">
            <div className="section-header">
                <h2>Manage Repositories</h2>
                <button className="add-btn" onClick={() => setShowForm(!showForm)}>
                    <Plus size={18} /> {showForm ? 'Cancel' : 'Register New Repo'}
                </button>
            </div>

            {showForm && (
                <form className="repo-form card" onSubmit={handleSubmit}>
                    <div className="input-grid">
                        <input type="text" placeholder="Repo Name (e.g. My-App)" required
                            onChange={(e) => setFormData({...formData, name: e.target.value})} />
                        <input type="text" placeholder="GitHub URL" required
                            onChange={(e) => setFormData({...formData, url: e.target.value})} />
                        <input type="text" placeholder="Owner" required
                            onChange={(e) => setFormData({...formData, owner: e.target.value})} />
                        <input type="text" placeholder="Default Branch" defaultValue="main"
                            onChange={(e) => setFormData({...formData, defaultBranch: e.target.value})} />
                    </div>
                    <button type="submit" className="save-btn">Save Repository</button>
                </form>
            )}

            <div className="repo-list card">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Owner</th>
                            <th>Default Branch</th>
                            <th>Link</th>
                        </tr>
                    </thead>
                    <tbody>
                        {repos.map(repo => (
                            <tr key={repo.id}>
                                <td><strong>{repo.name}</strong></td>
                                <td>{repo.owner}</td>
                                <td><code>{repo.defaultBranch}</code></td>
                                <td>
                                    <a href={repo.url} target="_blank" rel="noreferrer">
                                        <ExternalLink size={16} />
                                    </a>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Repos;