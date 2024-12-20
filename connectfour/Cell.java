package connectfour;

import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */

public class Cell {
   public static final int SIZE = 90; // cell width/height (square)
   public static final int PADDING = SIZE / 5;
   public static final int SEED_SIZE = SIZE - PADDING * 2;

   Seed content;
   int row, col;
   int animationY; // Y position for animation

   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      content = Seed.NO_SEED;
      animationY = -SIZE; // Start above the board
   }

   public void newGame() {
      content = Seed.NO_SEED; // Clear the cell content
      animationY = -SIZE; // Reset animation position
   }

   public void paint(Graphics g) {
      int x1 = col * SIZE + PADDING;
      int y1 = row * SIZE + PADDING;
      if (content == Seed.CROSS || content == Seed.NOUGHT) {
         g.drawImage(content.getImage(), x1, animationY + PADDING, SEED_SIZE, SEED_SIZE, null);
      }
   }

   public void animateDrop() {
      if (animationY < row * SIZE) {
         animationY += 10; // Adjust speed as needed
      }
   }
}