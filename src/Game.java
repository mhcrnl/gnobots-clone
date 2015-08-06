import java.awt.*;
import javax.swing.*;

/**
 * Contains details of the game logic
 */
public class Game {
  
  static boolean gameOver = false;
  static boolean roundOver = false;  // determines when to go to the next level/back to the first level
  static String endMessage;          // says whether you won or lost
  static int levelNum = 1;           // the current level
  static int counter = 0;  // index of the robot object being used
  
  /**
   * determines the correct level and decides when to increase/reset the level to level 1
   */
  public static void initiateLevel(Player player, Board board) {  // this method will be used to handle levels
    GameDisplay.timer.stop();
    GameDisplay.tickCount = 0;
    if (endMessage.equals("You Win!")) {
      JOptionPane.showMessageDialog(null, endMessage, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (endMessage.equals("Game Over. Would you like to play again?")) {    
      int temp = JOptionPane.showConfirmDialog(null, "Game Over.\nWould you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (temp == 0) {
      }
      else {
        System.exit(0);
      }
    }
    else {
    }
    board.clearBoard();
      if (Game.endMessage.equals("You Win!")) {
        levelNum++;
        Robot.amountOfRobots = 10 * levelNum;
        Robot.robots = new Robot[Robot.amountOfRobots];
        for (int i = 0; i < Robot.amountOfRobots; i++) {  // creates specified amount of robots
          Robot.robots[i] = new Robot();
        }
        player = new Player();              // creates our player
        board = new Board();                 // creates playing board
        board.setBoard(player, Robot.robots);     // fills board with player, robots, and empty space
        Game.roundOver = false;
      }
      else {
        levelNum = 1;
        Robot.amountOfRobots = 10;
        Robot.robots = new Robot[Robot.amountOfRobots];
        for (int i = 0; i < Robot.amountOfRobots; i++) {  // creates specified amount of robots
          Robot.robots[i] = new Robot();
        }
        player = new Player();              // creates our player
        board = new Board();                 // creates playing board
        board.setBoard(player, Robot.robots);     // fills board with player, robots, and empty space
        roundOver = false;
      }
      GameDisplay.timer.start();
  }
  
  /**
   * determines the new locations of robots after a player moves and updates the board
   */
  public static void playLevel(Player player, Board board) {
    for (int x = 0; x < Board.numRow; x++) {
      for (int y = 0; y < Board.numCol; y++) {
        Board.testBoard[x][y] = Board.board[x][y];  // creates a copy of the board used to detect collisions
      }
    }
    for (int i = 0; i < Robot.amountOfRobots; i++) {
      if (Robot.robots[i] != null) {  // skips robots that have been destroyed
        if (i == Robot.skipRobot) {
          if (roundOver)
          counter++;
          continue;                   // doesn't move the skipped robot
        }
        else if (Robot.robots[i].isBroken == true) {
          if (Robot.robots[i].brokenMoveCount != 4) {
            Robot.robots[i].moveRobot();
            counter++;
            if (Robot.robots[i] != null)
              Robot.robots[i].brokenMoveCount++;
          }
          else {
            Robot.robots[i].moveRobotRandomly();
            counter++;
            if (Robot.robots[i] != null)
              Robot.robots[i].brokenMoveCount = 1;
          }
        }
        else {
          Robot.robots[i].moveRobot();  // moves all the robots
          counter++; 
        }
      }
      else {
        counter++;
      }
    }
    Robot.skipRobot = -1;  // resets the skipped robot for the next set of moves 
    counter = 0;           // resets counter for the next set of moves
    board.newBoard();     // updates board after all the robots move
  }
  
  /**
   * updates the status panel with new values
   */
  public static void updateCount() {
    GameDisplay.statusPanel = GameDisplay.createStatusPanel();
    GameDisplay.cPane.add(GameDisplay.statusPanel, BorderLayout.SOUTH);
  }

}