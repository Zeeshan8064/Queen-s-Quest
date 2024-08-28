# Queen's Quest

This project is a puzzle game called "Queen's Quest" that challenges players to place the Queen to the bottom left (a1) square of the board. The game is implemented using Java programming language and the JavaFX framework for the user interface. Player and win information is stored using JSON (Jackson library).

## Game Rules

1. The game board consists of 8 rows and 8 columns.
2. The game contains a white Queen that is placed randomly in the top row or the right most column.
3. The goal is to place the queen at the a1 square.
4. The Queen can move downwards, Go left or can travel diagonally down-left.

## User Interface

The user interface consists of three screens:

1. **Start Page**: This is the first screen displayed when the game is started. The players are prompted to enter their name.
2. **Game UI**: After entering the player names, the game board is displayed, and the player can make moves.
3. **High Scores**: This screen displays the top 5 results, including the winner name and the number of games won by the player. The information is retrieved from a JSON file where the player names, Start time of the game, Winner name, Number of moves made by the players is stored.

## Dependencies

The project uses the following dependencies:

- JavaFX: A framework for building Java applications with a graphical user interface.
- JSON (Jackson library): Used to store player and score information in a JSON format.
- JUNIT 5(Jupiter): Used to test the functionality of the Game model, using Unit tests
- TinyLog 2: For Logging

## Plugins

The project uses the following plugins:

- Maven Javadoc Plugin : For Documentation.
- Maven JXR Plugin
- Maven CheckStyle Plugin
- Maven Surefire report Plugin
- JoCoCo Maven Plugin

## Implementation Details

The project is implemented using Java programming language, JavaFX framework, and JSON (Jackson library) for data storage. The game logic is implemented based on the rules described above, and the user interface is designed using JavaFX to provide an interactive gaming experience.

Player and score information is stored in a JSON file. Each game's details, including the date and time, winner name, player names, number of moves, are recorded and stored in the JSON file. The high scores screen retrieves this information and displays the top 5 results.

## Conclusion

"Queen's Quest" is an engaging game that challenges the players to place the Queen to the bottom left (a1) square of the board. The project utilizes Java, JavaFX, and JSON to provide a user-friendly interface and store player and score information. The high scores feature adds competitiveness and allows players to track their progress. Enjoy playing the game and aim for the top of the high score table!
