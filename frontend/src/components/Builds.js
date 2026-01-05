import "../styles/Builds.css";
import Navbar from "./Navbar";

function Builds() {
  return (
    <>
      <Navbar />
      <div className="builds">
        <h1>Build History</h1>

        <table>
          <thead>
            <tr>
              <th>Commit</th>
              <th>Branch</th>
              <th>Status</th>
              <th>Time</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Initial commit</td>
              <td>main</td>
              <td className="success">SUCCESS</td>
              <td>2 min ago</td>
            </tr>
            <tr>
              <td>Fix pipeline bug</td>
              <td>develop</td>
              <td className="failed">FAILED</td>
              <td>10 min ago</td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
}

export default Builds;
