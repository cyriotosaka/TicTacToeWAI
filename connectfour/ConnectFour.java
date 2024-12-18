package connectfour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */

// added animation

public class ConnectFour extends JPanel {
   private static final long serialUID = 1L;

   public static final String TITLE = "Connect Four";
   public static final Color COLOR_BG = Color.WHITE;
   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
   public static final Color COLOR_CROSS = new Color(239, 105, 80);
   public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
   public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

   private Board board;
   private State currentState;
   private Seed currentPlayer;
   private JLabel statusBar;
   private Timer animationTimer;

   public ConnectFour() {
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
      statusBar.setBackground(COLOR_BG_STATUS);
      statusBar.setOpaque(true);
      statusBar.setPreferredSize(new Dimension(300, 30));
      statusBar.setHorizontalAlignment(JLabel.LEFT);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

      JPanel bottomPanel = new JPanel(new BorderLayout());
      bottomPanel.add(statusBar, BorderLayout.CENTER);
//      bottomPanel.add(resetButton, BorderLayout.EAST);

      super.setLayout(new BorderLayout());
      super.add(bottomPanel, BorderLayout.PAGE_END);
      super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
      super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

      initGame();
      newGame();
   }

   public void initGame() {
      board = new Board();
   }

//   public void newGame() {
//      for (int row = 0; row < Board.ROWS; ++row) {
//         for (int col = 0; col < Board.COLS; ++col) {
//            board.cells[row][col].newGame();
//         }
//      }
//      currentPlayer = Seed.CROSS;
//      currentState = State.PLAYING;
//      statusBar.setText("X's Turn"); // Reset status bar
//
//   }
public void newGame() {
   for (int row = 0; row < Board.ROWS; ++row) {
      for (int col = 0; col < Board.COLS; ++col) {
         board.cells[row][col].content = Seed.NO_SEED; // all cells empty
      }
   }
   currentPlayer = Seed.CROSS;    // cross plays first
   currentState = State.PLAYING;  // ready to play
   statusBar.setText("X's Turn"); // Reset status bar

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

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      setBackground(COLOR_BG);

      board.paint(g);

      if (currentState == State.PLAYING) {
         statusBar.setForeground(Color.BLACK);
         statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
      } else if (currentState == State.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
      } else if (currentState == State.CROSS_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'X' Won! Click to play again.");
      } else if (currentState == State.NOUGHT_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("'O' Won! Click to play again.");
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

private void createMenuBar(JFrame frame) {
   JMenuBar menuBar = new JMenuBar();

   JMenu fileMenu = new JMenu("File");
   JMenuItem newGameItem = new JMenuItem("New Game");
   newGameItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         System.out.println("New Game button clicked"); // Debug print
         newGame();
         revalidate(); // Ensure layout is refreshed
         repaint();
      }
   });

   JMenuItem exitItem = new JMenuItem("Exit");
   exitItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         System.exit(0);
      }
   });
   fileMenu.add(newGameItem);
   fileMenu.add(exitItem);

   JMenu helpMenu = new JMenu("Help");
   JMenuItem aboutItem = new JMenuItem("About");
   aboutItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(frame, "Connect Four Game\nVersion 1.0\nDeveloped by [Your Name]");
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
            frame.setContentPane(new ConnectFour());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            createMenuBar(frame); // Set the menu bar here
            frame.setVisible(true);
         }
      });
   }
}