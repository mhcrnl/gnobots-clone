import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 * Contains methods and data members used for displaying the game board.
 */
public class Board {
  
  static int numRow = 30;    // dimensions of the game board
  static int numCol = 45;
  static char[][] board = new char[numRow][numCol];      // creates the playing board
  static char[][] testBoard = new char[numRow][numCol];  // creates an identical board used for managing collisions
  Random random = new Random();
  static int robotCount = 0;  // keeps track of the amount of robots currently on the board
  static int placedRobot;
  
  /**
   * removes everything from the board
   */
  public void clearBoard() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        board[i][j] = ' ';
        GameDisplay.setBlank(i, j);
      }
    }
    robotCount = 0;
    Robot.amountOfRobots = 10;
  }
  
  /**
   * displays the game board and fills it with robots and a player
   */
  public void setBoard(Player player, Robot[] robots) {
    player.placePlayer();  // places the player on a random point
    for (int i = 0; i < robots.length; i++) {   // initializes robots and places them on the board
      robots[i] = new Robot();
    }
    Robot.brokenRobot();
    for (int i = 0; i < robots.length; i++) {
      placedRobot = i;
      robots[i].placeRobot();
    }
    for (int x = 0; x < numRow; x++) {
      for (int y = 0; y < numCol; y++) {
        if ( (board[x][y] == '@') || (board[x][y] == '*') || (board[x][y] == '+') || (board[x][y] == 'x') ) {    // fills unoccupied spaces with "spaces"
          continue;
        } 
        else {
          board[x][y] = ' ';
          GameDisplay.setBlank(x, y);
        }
      }
    }
    for (int i = 0; i < Robot.robots.length; i++) {
      if (Robot.robots[i] != null) {  // counts the amount of robots that haven't been destroyed
        robotCount++;
      }
    }
    Game.updateCount();
  }
  
  /**
   * Updates the playing board after a move.
   */
  public void newBoard() {
    robotCount = 0;  // resets the robot count
    for (int x = 0; x < numRow; x++) {
      for (int y = 0; y < numCol; y++) {
        if ( (board[x][y] == '@') || (board[x][y] == '*') || (board[x][y] == '+') || (board[x][y] == 'x') ) {
          continue;
        } 
        else {
          board[x][y] = ' ';
          GameDisplay.setBlank(x, y);
        }
      }
    }
    for (int i = 0; i < Robot.robots.length; i++) {
      if (Robot.robots[i] != null) {
        robotCount++;
      }
    }
    if (robotCount == 0) {  // determines when the level is won and displays a message
      Game.endMessage = "You Win!";
      Game.updateCount();
      Game.roundOver = true;
    }
    Game.updateCount();
  }
  
}