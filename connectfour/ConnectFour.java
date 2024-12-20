/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026221177 - Muhammad Ariq Alwin
 * 2 - 5026231065 - Beh Siu Li
 * 3 - 5026231168 - Okky Priscila Putri
 */
package connectfour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */

public class ConnectFour extends JPanel {

   public static final String TITLE = "Connect Four";
   public static final Color COLOR_BG = Color.BLACK;
//   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
public static final Color COLOR_BG_STATUS = new Color(0, 0, 0);

   public static final Color COLOR_CROSS = new Color(239, 105, 80);
   public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
   public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);


   private Board board;
   private State currentState;
   private Seed currentPlayer;
   private JLabel statusBar;
   private Timer animationTimer;
   private String player1Name;
   private String player2Name;
   private MusicPlayer musicPlayer;
   private JButton musicControlButton;
   private boolean isMusicPlaying;

   public ConnectFour() {

      musicPlayer = new MusicPlayer();
      isMusicPlaying = true;

      // Add music control button
//      🔇
      musicControlButton = new JButton("Music");
      musicControlButton.setPreferredSize(new Dimension(150, 100));
      musicControlButton.setContentAreaFilled(false);
      musicControlButton.setBorder(BorderFactory.createEmptyBorder());
      musicControlButton.setFocusPainted(false);
      musicControlButton.setFont(new Font("Arial", Font.PLAIN, 48));
      musicControlButton.setForeground(Color.WHITE);

      musicControlButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (isMusicPlaying) {
               musicPlayer.stop();
               musicControlButton.setText("\uD83D\uDD07"); // Muted speaker
            } else {
               musicPlayer.play("audio/background_music.wav");
               musicControlButton.setText("\uD83D\uDD0A"); // Speaker with sound waves
            }
            isMusicPlaying = !isMusicPlaying;
         }
      });

      JPanel musicControlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      musicControlPanel.setOpaque(false);
      musicControlPanel.add(musicControlButton);

      // Add the music control panel to the layout
      super.add(musicControlPanel, BorderLayout.NORTH);

      super.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int col = mouseX / Cell.SIZE;

            if (currentState == State.PLAYING) {
               if (col >= 0 && col < Board.COLS) {
                  for (int row = Board.ROWS - 1; row >= 0; row--) {
                     if (board.cells[row][col].content == Seed.NO_SEED) {
                        board.cells[row][col].content = currentPlayer;
                        currentState = stepGame(currentPlayer, row, col);
                        SoundEffects.EAT_FOOD.play(); // Play move sound
                        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
                           SoundEffects.EXPLODE.play(); // Play win sound
                           JOptionPane.showMessageDialog(null, (currentPlayer == Seed.CROSS ? "X" : "O") + " won!");
                        }
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        startAnimation();
                        break;
                     }
                  }
               }
            } else {
               newGame();
            }
            repaint();
         }
      });

      statusBar = new JLabel();
      statusBar.setFont(FONT_STATUS);
      statusBar.setBackground(Color.white);
      statusBar.setOpaque(true);
      statusBar.setPreferredSize(new Dimension(200, 50)); // Adjust size as needed
      statusBar.setHorizontalAlignment(JLabel.LEFT);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
      statusBar.setForeground(new Color(0, 255, 255)); // Set status text to neon blue

      JPanel rightPanel = new JPanel(new BorderLayout());
      rightPanel.add(statusBar, BorderLayout.NORTH);

      super.setLayout(new BorderLayout());
      super.add(rightPanel, BorderLayout.EAST); // Add to the right side
      super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH + 200, Board.CANVAS_HEIGHT)); // Adjust width for sidebar
      super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

      initGame();
      newGame();
   }

   public void initGame() {
      board = new Board();
      getPlayerNames(); // Get player names at the start of a new game


   }
   public void newGame() {
      for (int row = 0; row < Board.ROWS; ++row) {
         for (int col = 0; col < Board.COLS; ++col) {
            board.cells[row][col].newGame(); // Clear the cell content
         }
      }
      currentPlayer = Seed.CROSS; // Reset the current player
      currentState = State.PLAYING; // Reset the game state
      updateStatusBar(); // Update the status bar
      repaint(); // Repaint the board to reflect the changes
   }


   public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
      int count = 0;
      for (int col = 0; col < Board.COLS; ++col) {
         if (board.cells[rowSelected][col].content == theSeed) {
            ++count;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
      count = 0;
      for (int row = 0; row < Board.ROWS; ++row) {
         if (board.cells[row][colSelected].content == theSeed) {
            ++count;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
      count = 0;
      for (int row = 0, col = colSelected - rowSelected; row < Board.ROWS && col < Board.COLS; ++row, ++col) {
         if (col >= 0 && col < Board.COLS && board.cells[row][col].content == theSeed) {
            ++count;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
      count = 0;
      for (int row = 0, col = colSelected + rowSelected; row < Board.ROWS && col >= 0; ++row, --col) {
         if (col < Board.COLS && board.cells[row][col].content == theSeed) {
            ++count;
            if (count == 4) return true;
         } else {
            count = 0;
         }
      }
      return false;
   }

   public State stepGame(Seed player, int selectedRow, int selectedCol) {
      board.cells[selectedRow][selectedCol].content = player;

      if (hasWon(player, selectedRow, selectedCol)) {
         return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
      } else {
         for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
               if (board.cells[row][col].content == Seed.NO_SEED) {
                  return State.PLAYING;
               }
            }
         }
         return State.DRAW;
      }
   }

   private void updateStatusBar() {
      String statusText;
      if (currentState == State.PLAYING) {
         statusText = (currentPlayer == Seed.CROSS ? player1Name : player2Name) + "'s Turn";
      } else if (currentState == State.DRAW) {
         statusText = "It's a Draw! Click to play again.";
      } else if (currentState == State.CROSS_WON) {
         statusText = player1Name + " Won! Click to play again.";
      } else if (currentState == State.NOUGHT_WON) {
         statusText = player2Name + " Won! Click to play again.";
      } else {
         statusText = "";
      }
      statusBar.setText("<html>Player 1: " + player1Name + "<br>Player 2: " + player2Name + "<br><br>" + statusText + "</html>");
      statusBar.setForeground(new Color(0, 255, 255)); // Set status text to neon blue
   }

@Override
public void paintComponent(Graphics g) {
   super.paintComponent(g);
   setBackground(COLOR_BG); // Set background to black
   board.paint(g);
   updateStatusBar(); // Update the status bar


      if (currentState == State.PLAYING) {
         statusBar.setForeground(Color.BLACK);
         statusBar.setText((currentPlayer == Seed.CROSS) ? player1Name + "'s Turn" : player2Name + "'s Turn");
      } else if (currentState == State.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
      } else if (currentState == State.CROSS_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText(player1Name + " Won! Click to play again.");
      } else if (currentState == State.NOUGHT_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText(player2Name + " Won! Click to play again.");
      }
   }

   public void startAnimation() {
      if (animationTimer != null && animationTimer.isRunning()) {
         animationTimer.stop();
      }
      animationTimer = new Timer(15, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            boolean animationInProgress = false;
            for (int row = 0; row < Board.ROWS; ++row) {
               for (int col = 0; col < Board.COLS; ++col) {
                  if (board.cells[row][col].content != Seed.NO_SEED) {
                     board.cells[row][col].animateDrop();
                     if (board.cells[row][col].animationY < row * Cell.SIZE) {
                        animationInProgress = true;
                     }
                  }
               }
            }
            repaint();
            if (!animationInProgress) {
               animationTimer.stop();
            }
         }
      });
      animationTimer.start();
   }


   private void getPlayerNames() {
      UIManager.put("OptionPane.background", Color.BLACK);
      UIManager.put("Panel.background", Color.BLACK);
      UIManager.put("OptionPane.messageForeground", new Color(0, 255, 255)); // Neon blue color
      UIManager.put("OptionPane.messageFont", new Font("OCR A Extended", Font.PLAIN, 14));

      UIManager.put("TextField.background", Color.BLACK); // Text box background
      UIManager.put("TextField.foreground", Color.WHITE); // Text box text color

      UIManager.put("Button.background", new Color(0, 255, 255)); // Pastel blue background
      UIManager.put("Button.foreground", Color.BLACK); // Button text color
      UIManager.put("Button.font", new Font("OCR A Extended", Font.PLAIN, 14)); // Button font

      player1Name = JOptionPane.showInputDialog(this, "Enter Player 1 name:", "Player 1", JOptionPane.PLAIN_MESSAGE);
      player2Name = JOptionPane.showInputDialog(this, "Enter Player 2 name:", "Player 2", JOptionPane.PLAIN_MESSAGE);

      if (player1Name == null || player1Name.trim().isEmpty()) {
         player1Name = "Player 1";
      }
      if (player2Name == null || player2Name.trim().isEmpty()) {
         player2Name = "Player 2";
      }
   }


   private void createMenuBar(JFrame frame) {
      JMenuBar menuBar = new JMenuBar();
      menuBar.setBackground(Color.BLACK); // Set menu bar background to black
      menuBar.setForeground(new Color(0, 255, 255)); // Set menu bar text to neon blue

      JMenu fileMenu = new JMenu("File");
      fileMenu.setForeground(new Color(0, 255, 255)); // Set menu text to neon blue

      JMenuItem newGameItem = new JMenuItem("New Game");
      newGameItem.setBackground(Color.BLACK); // Set menu item background to black
      newGameItem.setForeground(new Color(0, 255, 255)); // Set menu item text to neon blue
      newGameItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            newGame(); // Call the newGame method to reset the game
            revalidate(); // Ensure layout is refreshed
            repaint();
         }
      });

      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.setBackground(Color.BLACK); // Set menu item background to black
      exitItem.setForeground(new Color(0, 255, 255)); // Set menu item text to neon blue
      exitItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            musicPlayer.stop(); // Stop the music before exiting
            frame.dispose(); // Close the current frame
            new HomeScreen(false).setVisible(true); // Show the home screen without starting music
         }
      });

      fileMenu.add(newGameItem);
      fileMenu.add(exitItem);

      JMenu helpMenu = new JMenu("Help");
      helpMenu.setForeground(new Color(0, 255, 255)); // Set menu text to neon blue

      JMenuItem aboutItem = new JMenuItem("About");
      aboutItem.setBackground(Color.BLACK); // Set menu item background to black
      aboutItem.setForeground(new Color(0, 255, 255)); // Set menu item text to neon blue
      aboutItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Connect four final Project!");
         }
      });

      helpMenu.add(aboutItem);

      menuBar.add(fileMenu);
      menuBar.add(helpMenu);

      frame.setJMenuBar(menuBar);
   }

public void play() {
   javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
         JFrame frame = new JFrame(TITLE);
         frame.setContentPane(ConnectFour.this); // Use the existing instance
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setLocationRelativeTo(null);
         createMenuBar(frame); // Call createMenuBar on the existing instance
         frame.setVisible(true);

      }
   });
}}