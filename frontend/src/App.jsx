import { useEffect, useState } from "react";
import "./App.css";
import DraftScreen from './DraftScreen'; //importing the draft screen component so that it can switch screens when the draft starts

//reads the server adress in env
const API = import.meta.env.VITE_API_URL;
const ROUNDS = 10;


function App() {
  const [teams, setTeams] = useState(3);
  const [budget, setBudget] = useState(200);
  // use state returns a curr value and setter func 
  const [draftStarted, setDraftStarted] = useState(false);
  const [draftCode, setDraftCode] = useState(null);
  const [error, setError] = useState(null);
  const [starting, setStarting] = useState(false);  

  const [serverOK, setServerOK] = useState(null);

  async function startDraft() {
    setError(null);
    setStarting(true);
    try {
      const response = await fetch(`${API}/api/drafts`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ teams, budget }),
      });
      const data = await response.json();
      if (response.ok) {
        setDraftCode(data.draftCode);
        setDraftStarted(true);
      } else {
        setError(data.error);
      }
    } catch {
      setError("An error occurred, check if the backend is running");
    } finally {
      setStarting(false);
    }
  }

  //fetches request -> java answers -> converts into java - > updates state
  useEffect(() => {
    fetch(`${API}/api/health`)
      .then((res) => res.json())
      .then(( data) => setServerOK(data.ok === true))
      .catch(() => setServerOK(false));
  }, []);

  return (
    <>
    {/* Draft screen component stored to be displayed when the draft is started */}

      {draftStarted ? (
        <DraftScreen
          teams={teams}
          rounds={ROUNDS}
          budget={budget}
          draftCode={draftCode}
          onBack={() => {
             setDraftStarted(false); 
            setDraftCode(null);
          }}
        />
      ) : (

        // Settings Screen
        <>
        <div>
          <h1>Ball Knowledge</h1>
          <p>Draft your favorite players!</p>
          {/* check for backend connection */}
          <p>Server: {serverOK == null ? "Checking..." : serverOK ? "Online" : "Offline"}</p>
          <h2>Draft Settings</h2>
          <p>Number of Teams: {teams}</p>

          {/* Allows you to change the number of teams, (minimium 2) */}

          <button onClick={() => setTeams(teams + 1)}>Add Team</button>

          <button disabled={teams <= 2} onClick={() => setTeams(teams - 1)}>
            Remove Team
          </button>

          <h2>Number of Rounds: {ROUNDS}</h2>

          <h2>Starting Budget: ${budget.toFixed(2)}</h2>

          {/* type in starting budget P.S backend rejects 0 */}
          <input
            type="number"
            min="1"
            value={budget}
            onChange={(event) => setBudget(Number(event.target.value))}
          />
          {/* tells backend to create draft, only if settings valid */}
        </div>
        <button onClick={startDraft} disabled={starting}>
          {starting ? "Starting..." : "Start Draft"}
        </button>
        <div>
          {/* error message if settings are invalid */}
          {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
        </>
      )}
    </>
  );
}
export default App;
