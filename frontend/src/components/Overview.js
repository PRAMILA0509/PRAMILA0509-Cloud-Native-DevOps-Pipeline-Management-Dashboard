import React, { useEffect, useState } from 'react';
import { getAllRepos, getBuildsForRepo } from '../services/api';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip } from 'recharts';
import PipelineCard from './PipelineCard';
import { Clock, User, CheckCircle, PlayCircle, AlertCircle } from 'lucide-react';
import '../styles/Dashboard.css';

const Overview = () => {
    const [pipelines, setPipelines] = useState([]);
    const [allBuilds, setAllBuilds] = useState([]);

    useEffect(() => {
        const loadData = async () => {
            try {
                const repoRes = await getAllRepos();
                setPipelines(repoRes.data);
                
                // Fetch recent history for the first repo as a summary
                if (repoRes.data.length > 0) {
                    const buildRes = await getBuildsForRepo(repoRes.data[0].id);
                    setAllBuilds(buildRes.data);
                }
            } catch (err) {
                console.error("Error loading overview data", err);
            }
        };
        loadData();
    }, []);

    const COLORS = ['#10b981', '#ef4444'];
    const chartData = [
        { name: 'Success', value: 85 },
        { name: 'Failed', value: 15 },
    ];

    return (
        <div className="overview-container">
            {/* Top Row: Metrics and Chart */}
            <div className="metrics-grid">
                <div className="card chart-card">
                    <h4>Overall Success Rate</h4>
                    <ResponsiveContainer width="100%" height={180}>
                        <PieChart>
                            <Pie data={chartData} innerRadius={60} outerRadius={80} dataKey="value" stroke="none">
                                {chartData.map((e, i) => <Cell key={i} fill={COLORS[i]} />)}
                            </Pie>
                            <Tooltip />
                        </PieChart>
                    </ResponsiveContainer>
                </div>
                
                <div className="card stats-summary-card">
                    <div className="stat-item">
                        <span className="label">Total Repositories</span>
                        <span className="value">{pipelines.length}</span>
                    </div>
                    <div className="stat-item">
                        <span className="label">System Status</span>
                        <span className="value status-online">NGROK ACTIVE</span>
                    </div>
                </div>
            </div>

            {/* Section 1: Build Tracking Table (SQL Data View) */}
            <section className="dashboard-section">
                <h3>Build History Tracking</h3>
                <div className="table-container card">
                    <table className="build-table">
                        <thead>
                            <tr>
                                <th>Build ID</th>
                                <th>Branch</th>
                                <th>Author</th>
                                <th>Commit Message</th>
                                <th>Status</th>
                                <th>Start Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            {allBuilds.map(build => (
                                <tr key={build.id}>
                                    <td>#{build.id}</td>
                                    <td><code className="branch-code">{build.branch}</code></td>
                                    <td><div className="user-cell"><User size={14}/> {build.commitAuthor}</div></td>
                                    <td className="msg-cell">{build.commitMessage}</td>
                                    <td>
                                        <span className={`status-badge ${build.status.toLowerCase()}`}>
                                            {build.status === 'SUCCESS' && <CheckCircle size={12}/>}
                                            {build.status === 'RUNNING' && <PlayCircle className="spin" size={12}/>}
                                            {build.status}
                                        </span>
                                    </td>
                                    <td className="time-cell">{new Date(build.startTime).toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>

            {/* Section 2: Visual Pipeline Tracking */}
            <section className="dashboard-section">
                <h3>Live Project Pipelines</h3>
                <div className="pipeline-grid">
                    {pipelines.map(repo => (
                        <PipelineCard key={repo.id} repo={repo} />
                    ))}
                </div>
            </section>
        </div>
    );
};

export default Overview;