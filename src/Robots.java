/*
 * name: Steven Albertson
 * cs login: alberts2
 * REC1
 * 04/05/12
 * 
 * name: Tyler Skibo
 * cs login: tskibo
 * REC4
 * 04/05/12
 * 
 * name: Jeffrey Franklin
 * cs login: frankli4
 * REC1
 * 04/05/12
 */

import javax.swing.*;
import java.awt.*;

/**
 * @author Steven Albertson
 * @author Jeffrey Franklin
 * @author Tyler Skibo
 * @since 2012-03-08
 */
public class Robots {
  
  static JLabel label;
    
  /**
   * Determines the control flow of the program.
   * 
   * @param command line arguments
   */
  public static void main(String[] args) { 
    for (int i = 0; i < Robot.amountOfRobots; i++) {  // creates specified amount of robots
      Robot.robots[i] = new Robot();
    }
    for (int i = 0; i < Board.numRow; i++) {
      for (int j = 0; j < Board.numCol; j++) {
        label = new JLabel();
        if ( ( (i + j) % 2) == 1) {
          label.setBackground(GameDisplay.darkColor);
        }
        if ( ( (i + j) % 2) == 0) {
          label.setBackground(GameDisplay.lightColor);
        }
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(24, 24));    
        GameDisplay.labelBoard[i][j] = label;
      }
    }
    GameDisplay.player1 = new Player();         // creates our player
    GameDisplay.board1 = new Board();         // creates playing board
    GameDisplay display = new GameDisplay();
  }

}