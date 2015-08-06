import java.util.*;
import javax.swing.*;

/**
 * Used to create multiple robots and access their locations
 */
public class Robot {
  
  static int amountOfRobots = 10;       // amount of robots to be created each round
  static Robot[] robots = new Robot[amountOfRobots];    // creates designated amount of robots
  static Random random = new Random();         
  int currentRobotRow;                                  // location of the current robot
  int currentRobotCol;
  boolean isBroken;
  int brokenMoveCount;
  static int skipRobot = -1;                            // counter used to skip robots that have already moved
  
  /**
   * breaks a robot 15% of the time
   */
  public static void brokenRobot() {
    for (int i = 0; i < amountOfRobots; i++) {
      double chance = random.nextDouble(); 
      if (chance < .15) { 
        robots[i].isBroken = true;  
        robots[i].brokenMoveCount = random.nextInt(4) + 1;
      }
      else
        robots[i].isBroken = false;
    }
  }
  
  /**
   * returns the robot object located at the current location
   * 
   * @param current row
   * @param current collumn
   */
  public Robot getRobot(int row, int col) {  
    int i;
    for (i = 0; i < amountOfRobots; i++) {
      if (robots[i] != null) {
        if ( (robots[i].currentRobotRow == row) && (robots[i].currentRobotCol == col) )
          break;
      }
    }
    return robots[i];
  }
  
  /**
   * Randomly places the robot somewhere on the grid.
   */
  public void placeRobot() {
    int i = 0;
    while (i < 1) {
      int robotRow = random.nextInt(Board.numRow);  // robot's row
      int robotCol = random.nextInt(Board.numCol);  // robot's column
      boolean isRobot = (Board.board[robotRow][robotCol] == '+');   // checks to see if there is a robot
      boolean isDebris = (Board.board[robotRow][robotCol] == '*');  // checks to see if there is debris
      boolean isPlayer = (Board.board[robotRow][robotCol] == '@');  // checks to see if there is a player
      boolean isBrokenRobot = (Board.board[robotRow][robotCol] == 'x');
      if (isDebris || isPlayer || isRobot || isBrokenRobot) {
        continue;  // doesn't place a robot on an occupied space
      }
      else {
        currentRobotRow = robotRow;
        currentRobotCol = robotCol;
        if (robots[Board.placedRobot].isBroken) {
          Board.board[robotRow][robotCol] = 'x';  
          GameDisplay.setBrokenRobot(robotRow, robotCol);
          i++;
        }
        else {
          Board.board[robotRow][robotCol] = '+';  
          GameDisplay.setRobot(robotRow, robotCol);
          i++;
        }
      }
    }
  }
  
  /**
   * Determines where the robot's new location will be after the player moves
   */
  public void moveRobot() {
    if ( (currentRobotRow == Player.currentPlayerRow) && (currentRobotCol < Player.currentPlayerCol) ) { 
      Board.board[currentRobotRow][currentRobotCol] = ' ';   // clears the robot's previous location  
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotCol = currentRobotCol + 1;                 // updates its new location
      judgeMove();                                           // moves the robot to this location
    }
    else if ( (currentRobotRow == Player.currentPlayerRow) && (currentRobotCol > Player.currentPlayerCol) ) {
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotCol = currentRobotCol - 1;
      judgeMove();
    }
    else if ( (currentRobotRow < Player.currentPlayerRow) && (currentRobotCol == Player.currentPlayerCol) ) {
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotRow = currentRobotRow + 1;
      judgeMove();
    }
    else if ( (currentRobotRow > Player.currentPlayerRow) && (currentRobotCol == Player.currentPlayerCol) ) {
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotRow = currentRobotRow - 1;
      judgeMove();
    }
    else if ( (currentRobotRow < Player.currentPlayerRow) && (currentRobotCol < Player.currentPlayerCol) ) {
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotRow = currentRobotRow + 1;
      currentRobotCol = currentRobotCol + 1;
      judgeMove();
    }
    else if ( (currentRobotRow < Player.currentPlayerRow) && (currentRobotCol > Player.currentPlayerCol) ) {
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotRow = currentRobotRow + 1;
      currentRobotCol = currentRobotCol - 1;
      judgeMove();
    }
    else if ( (currentRobotRow > Player.currentPlayerRow) && (currentRobotCol > Player.currentPlayerCol) ) {
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotRow = currentRobotRow - 1;
      currentRobotCol = currentRobotCol - 1;
      judgeMove();
    }
    else if ( (currentRobotRow > Player.currentPlayerRow) && (currentRobotCol < Player.currentPlayerCol) ) {  
      Board.board[currentRobotRow][currentRobotCol] = ' ';
      GameDisplay.setBlank(currentRobotRow, currentRobotCol);
      currentRobotRow = currentRobotRow - 1;
      currentRobotCol = currentRobotCol + 1;
      judgeMove();
    }
    else {
      Game.endMessage = "Game Over. Would you like to play again?";
      Game.updateCount();
      Game.roundOver = true;
    }
  }
  
  /**
   * moves a broken robot in a random direction
   */
  public void moveRobotRandomly() {
    int choice = random.nextInt(8);
    switch (choice) {
      case 0:
        Board.board[currentRobotRow][currentRobotCol] = ' ';     
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotCol = currentRobotCol + 1;                
        judgeMove();
        break;
      case 1:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotCol = currentRobotCol - 1;
        judgeMove();
        break;
      case 2:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotRow = currentRobotRow + 1;
        judgeMove();
        break;
      case 3:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotRow = currentRobotRow - 1;
        judgeMove();
        break;
      case 4:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        currentRobotRow = currentRobotRow + 1;
        currentRobotCol = currentRobotCol + 1;
        judgeMove();
        break;
      case 5:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotRow = currentRobotRow + 1;
        currentRobotCol = currentRobotCol - 1;
        judgeMove();
        break;
      case 6:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotRow = currentRobotRow - 1;
        currentRobotCol = currentRobotCol - 1;
        judgeMove();
        break;
      case 7:
        Board.board[currentRobotRow][currentRobotCol] = ' ';
        GameDisplay.setBlank(currentRobotRow, currentRobotCol);
        currentRobotRow = currentRobotRow - 1;
        currentRobotCol = currentRobotCol + 1;
        judgeMove();
        break;
    }
  }
  
  /**
   * Moves the robot to its new location
   */
  public void judgeMove() {  
    if (Board.board[currentRobotRow][currentRobotCol] == '*') {  // destroys a robot if it hits debris
      robots[Game.counter] = null;
    }
    else if (Board.board[currentRobotRow][currentRobotCol] == '@') { // ends the round if the robot hits a player
      GameDisplay.setDeadPlayer(currentRobotRow, currentRobotCol);
      Game.endMessage = "Game Over. Would you like to play again?";
      Game.updateCount();
      Game.roundOver = true;
    }
    else if (Board.board[currentRobotRow][currentRobotCol] == '+' || Board.board[currentRobotRow][currentRobotCol] == 'x') {   // handles robots that move to locations occupied by other robots
      if ( (Board.board[currentRobotRow][currentRobotCol] == '+' && Board.testBoard[currentRobotRow][currentRobotCol] != '+')
            || (Board.board[currentRobotRow][currentRobotCol] == 'x' && Board.testBoard[currentRobotRow][currentRobotCol] != 'x') ) {  // checks whether the robot on the new location has moved yet
        for (int i = 0; i < amountOfRobots; i++) {
          if (robots[i] == null || robots[Game.counter] == null) {   // skips robots that have already been destroyed
            continue;
          }
          else {
            if ( (robots[Game.counter].currentRobotRow == robots[i].currentRobotRow) && (robots[Game.counter].currentRobotCol == robots[i].currentRobotCol) ) {
              robots[Game.counter] = null;  // destroys both robots if they move to the same location
              robots[i] = null;
              Board.board[currentRobotRow][currentRobotCol] = '*';
              GameDisplay.setDebris(currentRobotRow, currentRobotCol);
            }
          }
        }
      }
      else { 
        for (int i = 0; i < amountOfRobots; i++) {
          if (robots[i] == null) {
            continue;
          }
          else if (robots[i] == getRobot(currentRobotRow, currentRobotCol)) {
            robots[i].moveRobot(); 
            if (robots[Game.counter] != null)
              robots[Game.counter].moveRobot(); 
            skipRobot = i;
            break;
          }
          else {
          }
        }
      }
    }
    else {  // if the new location is empty, the robot symbol is placed there
      if (robots[Game.counter].isBroken == true) {
        Board.board[currentRobotRow][currentRobotCol] = 'x';
        GameDisplay.setBrokenRobot(currentRobotRow, currentRobotCol);
      }
      else {
        Board.board[currentRobotRow][currentRobotCol] = '+';
        GameDisplay.setRobot(currentRobotRow, currentRobotCol);
      }
    }
  }
 
}