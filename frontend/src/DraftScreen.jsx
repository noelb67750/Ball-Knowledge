
import { useState } from "react";

//Constant list of players with their positions and prices for the draft screen, dummy data for now to test functionality.
const players = [
  { id: 1, name: "LeBron James", position: "SF", price: 10 },
  { id: 2, name: "Stephen Curry", position: "PG", price: 8 },
  { id: 3, name: "Kevin Durant", position: "PF", price: 6 },
  { id: 4, name: "Kobe Bryant", position: "SG", price: 5 },
];

function DraftScreen({ teams, rounds, budget, onBack }) {
  // State to keep track of drafted players using an array
  const [draftedPlayers, setDraftedPlayers] = useState([]);
  const [currentTeam, setCurrentTeam] = useState(1);

  //function to select a player and add them to the drafted players array using a spread operator
  function selectPlayer(player) {
    if (player.price > budget) {
      return;
    }
    setDraftedPlayers([
        ...draftedPlayers,
        {...player, team: currentTeam},]);

        setCurrentTeam((currentTeam % teams) + 1); // Move to the next team
  }

  // Calculate the total spent by the current team, *filter the drafted players by the current team, and then *reduce to sum their prices
  const totalSpent = draftedPlayers
    .filter((player) => player.team === currentTeam)
    .reduce((sum, player) => sum + player.price, 0);

    // Calculate the remaining budget for the current team
    const remainingBudget = budget - totalSpent;
 

  return (
    // Draft Screen: displays the draft room with the list of players, their positions, and prices.
    <div>
      <h1>Draft Room</h1>
      <p>Welcome to the draft!</p>

      <p>Teams: {teams}</p>
      <p>Rounds: {rounds}</p>
      <p>Starting Budget: ${budget}</p>

      <h2>Current Team: {currentTeam}</h2>
      
      <h2>Team {currentTeam}'s Turn</h2>
      <p>Remaining Budget: ${remainingBudget}</p>

      <h2>Available Players</h2>

      {/* Display the list of players, position, and price, along with a button to select each player */}
      {players
        .filter((player) => !draftedPlayers.some((p) => p.id === player.id)) // Filter out drafted players
        .map((player) => (
          <div key={player.id}>
            <h3>{player.name}</h3>
            <p>Position: {player.position}</p>
            <p>Price: ${player.price}</p>
            <button
            disabled={player.price > remainingBudget} // Disable button if player price exceeds remaining budget 
            onClick={() => selectPlayer(player)}>
              Select Player
            </button>
          </div>
        ))}

      <h2>Drafted Players</h2>

      {/* Display the list of drafted players per team */}
      {draftedPlayers.map((player) => (
        <p key={player.id}>
          Team {player.team}: {player.name} - ${player.price}
        </p>
      ))}

      {/* Button that allows you to go back to the settings screen once clicked */}
      <button onClick={onBack}>Back to Settings</button>
    </div>
  );
}

export default DraftScreen;
