import React, { useEffect, useState, useCallback } from 'react';
import { getLatestBuildForRepo, triggerDeployment } from '../services/api';
import { Play, CheckCircle2, Clock, Loader2, GitBranch, User } from 'lucide-react';
import '../styles/Dashboard.css';

const PipelineCard = ({ repo }) => {
    const [latestBuild, setLatestBuild] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    // Fetch the specific row from your SQL table for this repo
    const loadBuildDetails = useCallback(async () => {
        try {
            const buildRes = await getLatestBuildForRepo(repo.id);
            setLatestBuild(buildRes.data);
        } catch (err) {
            console.log("No build history found in DB for", repo.name);
        }
    }, [repo.id, repo.name]);

    useEffect(() => {
        loadBuildDetails();
        const interval = setInterval(loadBuildDetails, 10000); // Poll DB every 10s
        return () => clearInterval(interval);
    }, [loadBuildDetails]);

    const handleRun = async () => {
        setIsLoading(true);
        try {
            await triggerDeployment(repo.id);
            setTimeout(loadBuildDetails, 2000);
        } catch (err) {
            alert("Trigger failed. Ensure backend is running.");
        } finally {
            setIsLoading(false);
        }
    };

    // Maps SQL "status" to CSS classes for the horizontal flow
    const getStatusClass = (buildStatus) => {
        if (!buildStatus) return 'pending';
        if (buildStatus === 'SUCCESS') return 'success';
        if (buildStatus === 'RUNNING') return 'running';
        if (buildStatus === 'FAILED') return 'failed';
        return 'pending';
    };

    return (
        <div className="pipeline-card">
            <div className="card-header">
                <div className="repo-info">
                    <h3>{repo.name}</h3>
                    <span className="branch-tag"><GitBranch size={12}/> {repo.defaultBranch}</span>
                </div>
                <button className="run-btn" onClick={handleRun} disabled={isLoading}>
                    {isLoading ? <Loader2 className="spinner" size={16} /> : <Play size={16} fill="currentColor" />}
                    Manual Trigger
                </button>
            </div>

            {/* This is the horizontal visual tracker */}
            <div className="pipeline-flow">
                {['BUILD', 'TEST', 'DEPLOY'].map((stage, index) => (
                    <React.Fragment key={stage}>
                        <div className={`stage-node ${getStatusClass(latestBuild?.status)}`}>
                            <div className="node-circle">
                                {latestBuild?.status === 'SUCCESS' ? <CheckCircle2 size={18}/> : index + 1}
                            </div>
                            <span>{stage}</span>
                        </div>
                        {index < 2 && <div className={`connector ${getStatusClass(latestBuild?.status)}`}></div>}
                    </React.Fragment>
                ))}
            </div>

            <div className="card-footer">
                {latestBuild ? (
                    <div className="build-meta">
                        <div className="meta-row">
                            <span className="author"><User size={14}/> {latestBuild.commitAuthor}</span>
                            <span className="status-label">{latestBuild.status}</span>
                        </div>
                        <p className="commit-msg">"{latestBuild.commitMessage}"</p>
                        <div className="time-info">
                            <Clock size={12}/> {new Date(latestBuild.startTime).toLocaleString()}
                        </div>
                    </div>
                ) : (
                    <div className="no-data">Waiting for GitHub Webhook...</div>
                )}
            </div>
        </div>
    );
};

export default PipelineCard;