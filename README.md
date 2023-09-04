# Automatic Hadron Player - AI Project

*Project related to the '**Artificial Intelligence**' exam @ **University of Calabria.***   
***TEAM MEMBERS:** Alessandro Viscomi, Alessia Ramogida, Gianluca Massara*

The project is based on a game called ***Hadron (by Mark Steree)***.

## Game Rules
Hadron is a two player game played on a 5x5 (or 7x7...) square board, initially empty.  
The two players, Red and Blue, take turns adding their own tiles to the board, one tile per turn, starting with Red.  
If you have a placement available, you must make one. Passing is not allowed.  
Draws cannot occur in Hadron.

The last player to make a placement wins. If you don't have a placement available on your turn, you lose.

## Project Description
The project is based on a client-server architecture. The server is primarily responsible for managing the chessboard and the actions performed on it, while the client pertains to the individual player and its logic.

Both the server and client have been provided by the professors and implemented in Java.
The client relies on the *Negamax* search algorithm and a heuristic module (initially empty).
The project's goal is to implement a heuristic that allows the AI player to win in most cases.


## Design Implementation
After testing the game to understand its details, we came to the conclusion that, in order to win, one must reach the end of it with an odd number of available moves (before our player moves). However, this is not sufficient; the game presents situations in which certain moves can block or unblock others. Therefore, unlike what was initially thought, it is not enough to only consider the parity-oddness issue at the current level.

Following this, and after a discussion with some of the most dedicated Hadron players, which did not yield much useful information regarding the game, we decided to consider, in addition to the parity-odd heuristic, some of the heuristics that are usually considered valid for any board-based game, particularly mobility. Similar to what happens in Simulated Annealing, we also considered a random logic.

After these premises, we finally created a heuristic consisting of three components:

1. **Random component:** assigns a random heuristic value to the current configuration.
2. **Mobility component:** assigns a higher value to configurations that have a greater number of available moves (positive mobility).
3. **Parity-odd component:** assigns a higher heuristic value to boards where the ratio between the number of configurations that leave an even number of moves available to the opponent and the total number of moves is larger.

The weighted average of these three components is calculated, where the weights, after various trials, have been assigned as follows: the weight of mobility has a fixed value of 30 (which is equivalent to 30% of the total weights); the weight of the random heuristic is initially set at 70 but decreases with each turn following an exponential probability distribution (according to the logic of simulated annealing, as previously mentioned) and tends towards 0 in the endgame phase; the weight of the parity-odd heuristic starts at 0 and increases with each step with a logic opposite to that of randomness.