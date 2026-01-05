import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";
import { loginUser, getAllRepos, getBuildsForRepo } from "../services/api";

export default function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("user");
    navigate("/");
  };

  return (
    <nav className="navbar">
      <h3>DevOps Dashboard</h3>
      <div>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/repos">Repos</Link>
        <Link to="/builds">Builds</Link>
        <Link to="/pipeline">Pipeline</Link>
        <button onClick={logout}>Logout</button>
      </div>
    </nav>
  );
}
