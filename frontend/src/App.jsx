import { useState } from 'react'
import './App.css'

function App() {
  const [teams, setTeams] = useState(3)
  const [rounds, setRounds] = useState(10)

  return (
    <>
      <div>
        <h1>Ball Knowledge</h1>
        <p>Draft your favorite players!</p>
        <button>Start Draft</button>

        <h2>Draft Settings</h2>
        <p>Number of Teams: {teams}</p>

        <button onClick={() => setTeams(teams + 1)}>
        Add Team
        </button>
        
        <button onClick={() => 
          {
            if (teams > 2) {
              setTeams(teams - 1);
            }
          }}>
          Remove Team
        </button>

        <h2>Number of Rounds: {rounds}</h2>
        <button onClick={() => setRounds(rounds + 1)}>
          + 
        </button>

        <button 
        disabled={rounds <= 1}
        onClick={() => setRounds(rounds - 1)}>
            -
          </button>


      </div>
    </>
  )
}

export default App
