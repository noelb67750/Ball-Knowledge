import { useState } from "react";
import "./App.css";

function App() {
  const [teams, setTeams] = useState(3);
  const [rounds, setRounds] = useState(10);
  const [budget, setBudget] = useState(200);

  return (
    <>
      <div>
        <h1>Ball Knowledge</h1>
        <p>Draft your favorite players!</p>
        <button>Start Draft</button>

        <h2>Draft Settings</h2>
        <p>Number of Teams: {teams}</p>

        {/* Allows you to change the number of teams, (minimium 2) */}

        <button onClick={() => setTeams(teams + 1)}>Add Team</button>

        <button disabled={teams <= 2} onClick={() => setTeams(teams - 1)}>
          Remove Team
        </button>

        <h2>Number of Rounds: {rounds}</h2>

        {/* ALlows you to change the number of rounds (minimim 1) */}

        <button onClick={() => setRounds(rounds + 1)}>+</button>

        <button disabled={rounds <= 1} onClick={() => setRounds(rounds - 1)}>
          -
        </button>

        <h2>Starting Budget</h2>

        {/* Allows you to change the budget by typing in a specifc amount (Validation pending) */}

        <input
          type="number"
          min="0"
          value={budget}
          onChange={(event) => setBudget(Number(event.target.value))}
        />
      </div>
    </>
  );
}
export default App;
