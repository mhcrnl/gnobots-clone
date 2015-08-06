import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * contains the constructor for making the GUI and methods related to its display
 */
public class GameDisplay extends JFrame implements ActionListener, MouseListener {
  
  static Timer timer;
  static int tickCount = 0;
  static Player player1;
  static Board board1;
  static Container cPane;
  JButton upLeft, up, upRight, left, wait, right, downLeft, down, downRight, teleport;
  static JLabel darkLabel, lightLabel, levelLabel, robotsLabel;
  static Icon robot, brokenRobot, player, deadPlayer, debris;
  JMenuBar menuBar;
  JMenu  gameMenu;
  JMenuItem newGame;
  static JPanel boardPanel, buttonPanel, statusPanel;
  static JLabel[][] labelBoard = new JLabel[Board.numRow][Board.numCol]; // Initializing array for board population
  static Color darkColor = new Color(105, 130, 157);
  static Color lightColor = new Color(117, 144, 174);
  JButton lastPressed;

  /**
   * creates the GUI that displays the game
   */
  GameDisplay() {
    cPane = this.getContentPane();
    cPane.setLayout(new BorderLayout());
    boardPanel = createBoardPanel(Board.numRow, Board.numCol);
    cPane.add(boardPanel, BorderLayout.CENTER);
    buttonPanel = createButtonPanel();
    cPane.add(buttonPanel, BorderLayout.EAST);
    statusPanel = createStatusPanel();
    cPane.add(statusPanel, BorderLayout.SOUTH);
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenu gameMenu = new JMenu("Game");
    menuBar.add(gameMenu);
    newGame = new JMenuItem("New Game");
    gameMenu.add(newGame);
    newGame.addActionListener(this);
    board1.setBoard(player1, Robot.robots);  // fills board with player, robots, and empty space
    pack();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    timer = new Timer(200, this);
    timer.setInitialDelay(500);
    timer.start();
    setVisible(true); 
  }   
  
  /**
   * creates the panel that the player moves on
   */
  JPanel createBoardPanel(int height, int width) { 
    JPanel boardPanel = new JPanel(new GridLayout(height,width));
    //Example of displaying one the icons given to us. In this case, the standard robot
    robot = new ImageIcon("robot.png");
    brokenRobot = new ImageIcon("robot2.png");
    debris = new ImageIcon("debris.png");
    deadPlayer = new ImageIcon("dead.png");
    player = new ImageIcon("player.png");
    boardPanel.setBackground(Color.WHITE);
    boardPanel.setOpaque(true);
    //boardPanel.add(lightLabel);
    // for loop populating labelBoard[][] with the appropriate colored labels 
    // for displaying the game board
    for (int i = 0; i < Board.numRow; i++) {
      for (int j = 0; j < Board.numCol; j++) {
        boardPanel.add(labelBoard[i][j]);
      }
    }  
    return boardPanel;
  }
    
  /**
   * creates the panel that stores movement buttons 
   */
  JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridLayout(4,3));
    buttonPanel.setBackground(Color.WHITE);
    upLeft = new JButton("Up Left");
    up = new JButton("Up");
    upRight = new JButton("Up Right");
    left = new JButton("Left");
    wait = new JButton("Wait");
    right = new JButton("Right");
    downLeft = new JButton("Down Left");
    down = new JButton("Down");
    downRight = new JButton("Down Right");
    teleport = new JButton("Teleport");  
    upLeft.addActionListener(this);
    upLeft.addMouseListener(this);
    up.addActionListener(this);
    up.addMouseListener(this);
    upRight.addActionListener(this);
    upRight.addMouseListener(this);
    left.addActionListener(this);
    left.addMouseListener(this);
    wait.addActionListener(this);
    wait.addMouseListener(this);
    right.addActionListener(this);
    right.addMouseListener(this);
    downLeft.addActionListener(this);
    downLeft.addMouseListener(this);
    down.addActionListener(this);
    down.addMouseListener(this);
    downRight.addActionListener(this);
    downRight.addMouseListener(this);
    teleport.addActionListener(this);    
    teleport.addMouseListener(this);
    JButton[] buttonMaker = {upLeft, up, upRight, left, wait, right, downLeft, down, downRight, teleport};  
    for (int i = 0; i < 10; i++){
      buttonPanel.add(buttonMaker[i]);
    }  
    return buttonPanel;  
  }
    
  /**
   * creates the panel that displays the level and # of robots
   */
  static JPanel createStatusPanel() {
    JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    levelLabel = new JLabel("Level: " + Game.levelNum);
    statusPanel.add(levelLabel);
    robotsLabel = new JLabel("      \tRobots Remaining: " + Board.robotCount);
    statusPanel.add(robotsLabel);
    statusPanel.setBackground(Color.WHITE);  
    return statusPanel;
  }  
    
  /**
   * designates actions for each button
   */
  public void actionPerformed(ActionEvent e) {  
    if (e.getSource().equals(newGame)) {  
      Game.endMessage = "Reset";
      Game.roundOver = true;
      Game.initiateLevel(player1, board1);
    }
    if (e.getSource().equals(timer)) {
      if (lastPressed == upLeft) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow > 0) && (Player.currentPlayerCol == 0) ) {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol);
          }
          else if ( (Player.currentPlayerRow == 0) && (Player.currentPlayerCol > 0) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol -1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol - 1);
          }
        }
      if (Game.roundOver)
        Game.initiateLevel(player1, board1);
      }
      if (lastPressed == up) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == upRight) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow > 0) && (Player.currentPlayerCol == Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol);
          }
          else if ( (Player.currentPlayerRow == 0) && (Player.currentPlayerCol < Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol + 1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol + 1);
          }
     //   Game.playLevel(player1, board1);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == left) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol - 1);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == wait) {
      if (!Game.roundOver) {
        player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol);
      }
      if (Game.roundOver)
        Game.initiateLevel(player1, board1);
      }
      if (lastPressed == right) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol + 1);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == downLeft) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow) < Board.numRow - 1 && (Player.currentPlayerCol == 0) ) {    // allows the player to glide along walls
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol);        // updates location
          }
          else if ( (Player.currentPlayerRow == Board.numRow - 1) && (Player.currentPlayerCol > 0) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol - 1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol - 1);
          }
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == down) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == downRight) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow < Board.numRow - 1) && (Player.currentPlayerCol == Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol);
          }
          else if ( (Player.currentPlayerRow == Board.numRow - 1) && (Player.currentPlayerCol < Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol + 1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol + 1);
          }
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (lastPressed == teleport) {
        if (!Game.roundOver) {
          player1.placePlayer();
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      tickCount++;
      if (!Game.roundOver && (tickCount % 5 == 0) ) {
        Game.playLevel(player1, board1);
      }
      else if (!Game.roundOver) {
      }
      else {
        Game.initiateLevel(player1, board1);
      }
    }
  }
  
  /**
   * Makes a blank square
   */
  public static void setBlank(int i, int j) {
    labelBoard[i][j].setIcon(null);
    if ( ( (i + j) % 2) == 1) {
      labelBoard[i][j].setBackground(darkColor);
    }
    if ( ( (i + j) % 2) == 0) {
      labelBoard[i][j].setBackground(lightColor);
    }
    labelBoard[i][j].setOpaque(true);
    labelBoard[i][j].setPreferredSize(new Dimension(24, 24));
  }
  
  /**
   * Places a player image on a square
   */
  public static void setPlayer(int i, int j) {
    labelBoard[i][j].setIcon(player);
    labelBoard[i][j].setPreferredSize(new Dimension(24, 24));
  }
  
  /**
   * places a dead player image on a square
   */
  public static void setDeadPlayer(int i, int j) {
    labelBoard[i][j].setIcon(deadPlayer);
    labelBoard[i][j].setPreferredSize(new Dimension(24, 24));
  }
  
  /**
   * places a robot image on a square
   */
  public static void setRobot(int i, int j) {
    labelBoard[i][j].setIcon(robot);
    labelBoard[i][j].setPreferredSize(new Dimension(24, 24));
  }
  
  /**
   * places a broken robot image on a square
   */
  public static void setBrokenRobot(int i, int j) {
    labelBoard[i][j].setIcon(brokenRobot);
    labelBoard[i][j].setPreferredSize(new Dimension(24, 24));
  }
  
  /**
   * places a debris image on a square
   */
  public static void setDebris(int i, int j) {
    labelBoard[i][j].setIcon(debris);
    labelBoard[i][j].setPreferredSize(new Dimension(24, 24));
  }
  
  /**
   * Moves the player every time the mouse is clicked, in case the click is too fast for mousePressed()
   */
  public void mouseClicked(MouseEvent e) {
    if (e.getSource().equals(upLeft)) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow > 0) && (Player.currentPlayerCol == 0) ) {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol);
          }
          else if ( (Player.currentPlayerRow == 0) && (Player.currentPlayerCol > 0) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol -1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol - 1);
          }
        }
      if (Game.roundOver)
        Game.initiateLevel(player1, board1);
    }
    if (e.getSource().equals(up)) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(upRight)) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow > 0) && (Player.currentPlayerCol == Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol);
          }
          else if ( (Player.currentPlayerRow == 0) && (Player.currentPlayerCol < Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol + 1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow - 1, Player.currentPlayerCol + 1);
          }
     //   Game.playLevel(player1, board1);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(left)) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol - 1);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(wait)) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(right)) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol + 1);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(downLeft)) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow) < Board.numRow - 1 && (Player.currentPlayerCol == 0) ) {    // allows the player to glide along walls
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol);        // updates location
          }
          else if ( (Player.currentPlayerRow == Board.numRow - 1) && (Player.currentPlayerCol > 0) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol - 1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol - 1);
          }
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(down)) {
        if (!Game.roundOver) {
          player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol);
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(downRight)) {
        if (!Game.roundOver) {
          if ( (Player.currentPlayerRow < Board.numRow - 1) && (Player.currentPlayerCol == Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol);
          }
          else if ( (Player.currentPlayerRow == Board.numRow - 1) && (Player.currentPlayerCol < Board.numCol - 1) ) {
            player1.placePlayer(Player.currentPlayerRow, Player.currentPlayerCol + 1);
          }
          else {
            player1.placePlayer(Player.currentPlayerRow + 1, Player.currentPlayerCol + 1);
          }
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
      if (e.getSource().equals(teleport)) {
        if (!Game.roundOver) {
          player1.placePlayer();
        }
        if (Game.roundOver)
          Game.initiateLevel(player1, board1);
      }
  }

  /**
   * detects when a mouse enters a component
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * detects when a mouse exits a component
   */
  public void mouseExited(MouseEvent e) {
  }
        
  /**
   * tells which button the mouse last pressed
   */
  public void mousePressed(MouseEvent e) {
    if (e.getSource().equals(upLeft)) {
      lastPressed = upLeft;
    }
    if (e.getSource().equals(up)) {
      lastPressed = up;
    }
    if (e.getSource().equals(upRight)) {
      lastPressed = upRight;
    }
    if (e.getSource().equals(left)) {
      lastPressed = left;
    }
    if (e.getSource().equals(wait)) {
      lastPressed = wait;
    }
    if (e.getSource().equals(right)) {
      lastPressed = right;
    }
    if (e.getSource().equals(downLeft)) {
      lastPressed = downLeft;
    }
    if (e.getSource().equals(down)) {
      lastPressed = down;
    }
    if (e.getSource().equals(downRight)) {
      lastPressed = downRight;
    }
    if (e.getSource().equals(teleport)) {
      lastPressed = teleport;
    }
  }

  /**
   * tells the player to wait when the mouse is released
   */
  public void mouseReleased(MouseEvent e) {
    lastPressed = wait;
  }
    
}