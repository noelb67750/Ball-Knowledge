import { useState } from "react";

//Constant list of players with their positions and prices for the draft screen, dummy data for now to test functionality.
const players = [
  { id: 1, name: "LeBron James", position: "SF", price: 10 },
  { id: 2, name: "Stephen Curry", position: "PG", price: 8 },
  { id: 3, name: "Kevin Durant", position: "PF", price: 6 },
  { id: 4, name: "Kobe Bryant", position: "SG", price: 5 },
];

function DraftScreen({ teams, rounds, budget, draftCode, onBack }) {
  // State to keep track of drafted players using an array
  const [draftedPlayers, setDraftedPlayers] = useState([]);

  // State to keep track of the player ID entered by the user
  const [playerId, setPlayerId] = useState("");

  // State to display error messages
  const [error, setError] = useState("");

  // Calculate the current pick and round
  const currentPick = draftedPlayers.length;
  const currentRound = Math.floor(currentPick / teams) + 1;
  const totalPicks = teams * rounds;
  const draftComplete = currentPick >= totalPicks;

  // Calculate the current team using snake draft order
  const pickInRound = currentPick % teams;
  const currentTeam = draftComplete
    ? null
    : currentRound % 2 === 1
    ? pickInRound + 1
    : teams - pickInRound;

  //function to select a player and add them to the drafted players array using a spread operator
  // Function to select a player based on their ID
  function selectPlayer(event) {
    event.preventDefault();
    setError("");

    // Prevent additional picks after the draft is complete
    if (draftComplete) {
      return;
    }

    // Find the player with the entered ID
    const player = players.find((player) => player.id === Number(playerId));

    // Check if the player exists
    if (!player) {
      setError("Invalid player ID.");
      return;
    }

    // Check if the player has already been drafted
    if (draftedPlayers.some((p) => p.id === player.id)) {
      setError("This player has already been drafted.");
      return;
    }

    // Check if the current team can afford the player
    if (player.price > remainingBudget) {
      setError("Not enough remaining budget.");
      return;
    }

    // Add the player to the drafted players array
    setDraftedPlayers([
      ...draftedPlayers,
      {
        ...player,
        team: currentTeam,
        round: currentRound,
      },
    ]);

    // Clear the input after a successful pick
    setPlayerId("");
  }

  // Calculate the remaining budget for any team
  function getRemainingBudget(team) {
    const totalSpent = draftedPlayers
      .filter((player) => player.team === team)
      .reduce((sum, player) => sum + player.price, 0);

    return budget - totalSpent;
  }

  // Calculate the remaining budget for the current team
  const remainingBudget = getRemainingBudget(currentTeam);

  // Create arrays for the team columns and round rows
  const teamNumbers = Array.from({ length: teams }, (_, index) => index + 1);

  const roundNumbers = Array.from({ length: rounds }, (_, index) => index + 1);

  return (
    // Draft Screen: displays the draft room
    <div className="draft-room">

      {/* Draft information */}
      <header className="draft-header">
        <h1>Draft Room</h1>
        <p>Welcome to the draft!</p>
        <p>Draft code: {draftCode}</p>
        <p>Teams: {teams}</p>
        <p>Rounds: {rounds}</p>
        <p>Starting Budget: ${budget}</p>

        <p>
          Round: {Math.min(currentRound, rounds)} / {rounds}
        </p>
        <p>
          Completed Picks: {currentPick} / {totalPicks}
        </p>
      </header>

      {/* Container for the draft board and sidebar */}
      <div className="draft-layout">

        {/* Main content */}
        <main className="draft-main">
          <h2>Draft Board</h2>

          {/* Display the draft board with rounds and teams */}
          <table className="draft-board">
            <thead>
              <tr>
                <th>Round</th>

                {/* Create a column for each team */}
                {teamNumbers.map((team) => (
                  <th key={team}>Team {team}</th>
                ))}
              </tr>
            </thead>

            <tbody>
              {/* Create a row for each round */}
              {roundNumbers.map((round) => (
                <tr key={round}>
                  <th>
                    {round} {round % 2 === 1 ? "→" : "←"}
                  </th>

                  {/* Create a draft cell for each team */}
                  {teamNumbers.map((team) => {
                    const player = draftedPlayers.find(
                      (p) => p.team === team && p.round === round
                    );

                    // Check if this cell represents the current pick
                    const isActive =
                      !draftComplete &&
                      team === currentTeam &&
                      round === currentRound;

                    return (
                      <td
                        key={team}
                        className={isActive ? "active-pick" : ""}
                      >
                        {player ? (
                          <>
                            <strong>{player.name}</strong>
                            <p>
                              {player.position} - ${player.price}
                            </p>
                          </>
                        ) : isActive ? (
                          "On the Clock"
                        ) : (
                          "—"
                        )}
                      </td>
                    );
                  })}
                </tr>
              ))}
            </tbody>
          </table>
        </main>

        {/* Sidebar containing draft controls and information */}
        <aside className="draft-sidebar">

          {/* Player selection panel */}
          <section className="pick-panel">
            {draftComplete ? (
              <h2>Draft Complete!</h2>
            ) : (
              <>
                <h2>Team {currentTeam}'s Turn</h2>
                <p>Remaining Budget: ${remainingBudget}</p>
              </>
            )}

            <h2>Make Your Pick</h2>

            {/* Form to draft a player using their ID */}
            <form onSubmit={selectPlayer}>
              <label htmlFor="playerId">Player ID: </label>

              <input
                id="playerId"
                type="number"
                min="1"
                step="1"
                value={playerId}
                onChange={(event) =>
                  setPlayerId(event.target.value)
                }
                placeholder="Enter player ID"
                disabled={draftComplete}
                required
              />

              <button type="submit" disabled={draftComplete}>
                Confirm Pick
              </button>
            </form>

            {/* Display an error message if the pick is invalid */}
            {error && <p role="alert">{error}</p>}
          </section>

          {/* Display the remaining budget for every team */}
          <section className="budget-panel">
            <h2>Team Budgets</h2>

            {teamNumbers.map((team) => (
              <div
                key={team}
                className={team === currentTeam ? "active-team" : ""}
              >
                <h3>Team {team}</h3>
                <p>Remaining Budget: ${getRemainingBudget(team)}</p>
              </div>
            ))}
          </section>

          {/* Display the IDs of players who have already been drafted */}
          <section className="taken-players">
            <h2>Taken Player IDs</h2>

            {draftedPlayers.length === 0 ? (
              <p>No players drafted yet.</p>
            ) : (
              <div className="taken-ids">
                {draftedPlayers.map((player) => (
                  <span key={player.id}>#{player.id} </span>
                ))}
              </div>
            )}

            <p>{draftedPlayers.length} players drafted</p>
          </section>

        </aside>
      </div>

      {/* Button that allows you to go back to the settings screen once clicked */}
      <button onClick={onBack}>Back to Settings</button>
    </div>
  );
}

export default DraftScreen;