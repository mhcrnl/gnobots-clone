import java.util.*;
import javax.swing.*;

/**
 * Used to create player objects and track the player's location
 */
public class Player {
  
  static int currentPlayerRow;   // current location of the player
  static int currentPlayerCol;  
  Random random = new Random();
  static boolean negativeInput = false;     // determines whether the user enters a negative number
  
  /**
   * Randomly places the player somewhere on the grid.
   */
  public void placePlayer() { 
    int i = 0;
    while (i < 1) {
      int playerRow = random.nextInt(Board.numRow);    // the row of the player
      int playerCol = random.nextInt(Board.numCol);    // the column of the player
      boolean isDebris = (Board.board[playerRow][playerCol] == '*');  // makes sure the player doesn't land on a robot/player/debris
      boolean isPlayer = (Board.board[playerRow][playerCol] == '@');
      boolean isRobot = (Board.board[playerRow][playerCol] == '+');
      boolean isBrokenRobot = (Board.board[playerRow][playerCol] == 'x');
      if (isDebris || isPlayer || isRobot || isBrokenRobot) {
        continue;   // repeats loop until empty location is found
      }
      else {
        Board.board[currentPlayerRow][currentPlayerCol] = ' ';  // clears the old location
        GameDisplay.setBlank(currentPlayerRow, currentPlayerCol);
        currentPlayerRow = playerRow;                           // places the player in the new location
        currentPlayerCol = playerCol;
        Board.board[playerRow][playerCol] = '@';
        GameDisplay.setPlayer(playerRow, playerCol);
        i++;
      }
    }
  }
  
  /**
   * Changes the player's position to a specified point
   * 
   * @param x the new row of the player
   * @param y the new column of the player
   */
  public void placePlayer(int x, int y) {  // places the player at a specified location
    if ( x > Board.numRow - 1 || x < 0 || y > Board.numCol - 1 || y < 0) {  // makes sure the player doesn't go out of bounds 
    }
    else {
      Board.board[currentPlayerRow][currentPlayerCol] = ' ';  // clears the old player location
      GameDisplay.setBlank(currentPlayerRow, currentPlayerCol);
      currentPlayerRow = x;   
      currentPlayerCol = y;                      // sets the new location to the arguments
      if (Board.board[x][y] != ' ') {            // ends the round if the player moves to an occupied spot
        Board.board[x][y] = '@';
        GameDisplay.setDeadPlayer(x, y);
        Game.endMessage = "Game Over. Would you like to play again?";
        Game.updateCount();
        Game.roundOver = true;
      }
      else {
        Board.board[x][y] = '@';        // moves the player to the new location
        GameDisplay.setPlayer(x, y);
      }
    }
  }
  
}