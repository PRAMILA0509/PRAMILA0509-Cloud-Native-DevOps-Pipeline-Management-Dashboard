import React, { useEffect, useState, useMemo } from 'react';
import { getAllRepos, getBuildsForRepo } from '../services/api';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip } from 'recharts';
import PipelineCard from './PipelineCard';
import { User, CheckCircle, PlayCircle, AlertCircle } from 'lucide-react';

import '../styles/Dashboard.css';

const Overview = () => {
    const [pipelines, setPipelines] = useState([]);
    const [allBuilds, setAllBuilds] = useState([]);

    useEffect(() => {
        const loadData = async () => {
            try {
                const repoRes = await getAllRepos();
                const repos = repoRes.data || [];
                setPipelines(repos);

                // Fetch builds for all repos in parallel and flatten
                if (repos.length > 0) {
                    const promises = repos.map(r => getBuildsForRepo(r.id).then(res => res.data || []).catch(() => []));
                    const results = await Promise.all(promises);
                    const merged = results.flat();

                    // Sort by startTime desc and keep most recent 20 builds
                    const sorted = merged.sort((a, b) => new Date(b.startTime) - new Date(a.startTime));
                    setAllBuilds(sorted.slice(0, 20));
                } else {
                    setAllBuilds([]);
                }
            } catch (err) {
                console.error("Error loading overview data", err);
            }
        };
        loadData();
    }, []);

    const COLORS = ['#10b981', '#ef4444'];

    // Derive chart data from current builds
    const chartData = useMemo(() => {
        if (!allBuilds || allBuilds.length === 0) return [
            { name: 'Success', value: 0 },
            { name: 'Failed', value: 0 },
        ];
        const success = allBuilds.filter(b => b.status === 'SUCCESS').length;
        const failed = allBuilds.filter(b => b.status === 'FAILED' || b.status === 'ERROR').length;
        const other = allBuilds.length - success - failed;
        // Normalize to percentages for display if needed
        return [
            { name: 'Success', value: success },
            { name: 'Failed', value: failed + other },
        ];
    }, [allBuilds]);

    const statusClass = (s) => {
        if (!s) return 'unknown';
        return s.toLowerCase();
    };

    return (
        <div className="overview-container">
            {/* Top Row: Metrics and Chart */}
            <div className="metrics-grid">
                <div className="card chart-card">
                    <h4>Overall Success Rate</h4>
                    <ResponsiveContainer width="100%" height={180}>
                        <PieChart>
                            <Pie data={chartData} innerRadius={60} outerRadius={80} dataKey="value" stroke="none">
                                {chartData.map((e, i) => <Cell key={i} fill={COLORS[i % COLORS.length]} />)}
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
                        <span className="label">Recent Builds</span>
                        <span className="value">{allBuilds.length}</span>
                    </div>
                    <div className="stat-item">
                        <span className="label">System Status</span>
                        <span className="value status-online">NGROK ACTIVE</span>
                    </div>
                </div>
            </div>

            {/* Section 1: Build Tracking Table (SQL Data View) */}
            <section className="dashboard-section">
                <h3>Build History (Recent)</h3>
                <div className="table-container card">
                    <table className="build-table">
                        <thead>
                            <tr>
                                <th>Build ID</th>
                                <th>Repo</th>
                                <th>Branch</th>
                                <th>Author</th>
                                <th>Commit Message</th>
                                <th>Status</th>
                                <th>Start Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            {allBuilds.length === 0 && (
                                <tr><td colSpan={7} style={{textAlign:'center', padding: '18px'}}>No recent builds</td></tr>
                            )}

                            {allBuilds.map(build => (
                                <tr key={build.id}>
                                    <td>#{build.id}</td>
                                    <td>{build.repo?.name || '-'}</td>
                                    <td><code className="branch-code">{build.branch || '-'}</code></td>
                                    <td><div className="user-cell"><User size={14}/> {build.commitAuthor || '-'}</div></td>
                                    <td className="msg-cell" title={build.commitMessage || ''}>{build.commitMessage || '-'}</td>
                                    <td>
                                        <span className={`status-badge ${statusClass(build.status)}`}>
                                            {build.status === 'SUCCESS' && <CheckCircle size={12}/>}
                                            {build.status === 'RUNNING' && <PlayCircle className="spin" size={12}/>}
                                            {(build.status === 'FAILED' || build.status === 'ERROR') && <AlertCircle size={12}/>}
                                            {build.status || 'UNKNOWN'}
                                        </span>
                                    </td>
                                    <td className="time-cell">{build.startTime ? new Date(build.startTime).toLocaleString() : '-'}</td>
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