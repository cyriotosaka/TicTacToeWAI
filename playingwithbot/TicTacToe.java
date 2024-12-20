package playingwithbot;

import connectfour.ConnectFour;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe implements ActionListener {

    /* Instance Variables */
    public static final String TITLE = "TicTacToe BOT";
    private JFrame window = new JFrame("Tic-Tac-Toe");
    private ArrayList<ArrayList<JButton>> buttonsMatrix = new ArrayList<ArrayList<JButton>>();
    private Board board;
    private int movesCount;
    private Integer gameSize;
    private Integer gameWinSize;
    public static String LETTER_PLAYER_X = "X";
    public static String LETTER_PLAYER_O = "O";
    private Boolean win;
    private Boolean visible = false;
    private Player computerPlayer;

    public TicTacToe(Integer size, Integer winSize) {
        this.gameSize = size;
        this.gameWinSize = winSize;
        board = new Board(size);
        /* Create Window */
        window.setSize(50*size, 50*size);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(size, size));

        for (int i=0; i<size; i++) {
            ArrayList<JButton> row = new ArrayList<JButton>();
            for (int j=0; j<size; j++) {
                JButton button = new JButton("");
                window.add(button);
                button.addActionListener(this);
                row.add(button);
            }
            buttonsMatrix.add(row);
        }

    }

    /* Make The Window Visible */
    public void show() {
        window.setVisible(true);
        visible = true;
    }

    /* Make The Window Visible */
    public void initAIPlayer() {
        window.setVisible(true);
        visible = true;
    }

    public void actionPerformed(ActionEvent a) {
        if (computerPlayer != null) {
            computerGameActionPerformed(a);
        } else {
            toggledGameActionPerformed(a);
        }
    }

    public void computerGameActionPerformed(ActionEvent a) {

        // user's turn
        renderMove(a, LETTER_PLAYER_X);
        win = checkWins(a, Board.PLAYER_X);
        renderCheckWins(a);
        movesCount++;
        if (win) {
            return;
        }

        // computer's turn
        computerPlayer.setMatrix(board.getMatrix());
        int[] move = computerPlayer.move();
        if (move[0] >= 0 && move[0] >= 0) {
            JButton button = buttonsMatrix.get(move[0]).get(move[1]);
            button.setText(LETTER_PLAYER_O);
            button.setEnabled(false);
            board.getMatrix()[move[0]][move[1]] = Board.PLAYER_O;
        }
        win = checkWins(a, Board.PLAYER_O);
        renderCheckWins(a);
        movesCount++;
    }

    public void toggledGameActionPerformed(ActionEvent a) {
        String letter = movesCount % 2 == 0 ? LETTER_PLAYER_X : LETTER_PLAYER_O;
        renderMove(a, letter);
        int player = movesCount % 2 == 0 ? Board.PLAYER_X : Board.PLAYER_O;
        win = checkWins(a, player);
        if (win == true) {
            renderEndGame(a);
            if (visible) {
                JOptionPane.showMessageDialog(null, letter + " WINS!");
            }
        } else if (movesCount+1 == gameSize*gameSize && win == false) {
            if (visible) {
                JOptionPane.showMessageDialog(null, "Tie Game!");
            }
        }
        movesCount++;
    }

    private void renderCheckWins(ActionEvent a) {
        String letter = movesCount % 2 == 0 ? LETTER_PLAYER_X : LETTER_PLAYER_O;
        if (win == true) {
            renderEndGame(a);
            if (visible) {
                JOptionPane.showMessageDialog(null, letter + " WINS!");
            }
        } else if (movesCount+1 == gameSize*gameSize && win == false) {
            if (visible) {
                JOptionPane.showMessageDialog(null, "Tie Game!");
            }
        }
    }
    private void renderMove(ActionEvent a, String letter) {
        /* Display X's or O's on the buttons */
        for (int i=0; i < buttonsMatrix.size(); i++) {
            for (int j=0; j < buttonsMatrix.get(i).size(); j++) {
                JButton button = buttonsMatrix.get(i).get(j);
                if (a.getSource() == button) {
                    button.setText(letter);
                    button.setEnabled(false);
                    if (LETTER_PLAYER_X.equals(letter)) {
                        board.getMatrix()[i][j] = Board.PLAYER_X;
                    } else {
                        board.getMatrix()[i][j] = Board.PLAYER_O;
                    }
                }
            }
        }
    }

    private void renderEndGame(ActionEvent a) {
        /* Display X's or O's on the buttons */
        for (ArrayList<JButton> cols : buttonsMatrix) {
            for (JButton button : cols) {
                button.setEnabled(false);
            }
        }
    }

    private boolean checkWinsFromPosition(ActionEvent a, int player, int pi, int pj) {
        return checkWinsFromPosition(a, player, pi, pj, gameWinSize, true, 0);
    }

    private boolean checkWinsFromPosition(ActionEvent a, int player, int pi, int pj, int winSize, boolean renderWin, int withFreeSize) {
        boolean win = false;
        int countH = 0;
        int countV = 0;
        int countDR = 0;
        int countDL = 0;
        for (int k = 0; k < winSize; k++) {
            /* horizontal to right */
            if (pj + winSize <= gameSize && player == board.getMatrix()[pi][pj+k]) {
                countH++;
            }
            /* vertical to bottom */
            if (pi + winSize <= gameSize && player == board.getMatrix()[pi+k][pj]) {
                countV++;
            }
            /* diagonal to right */
            if (pi + winSize <= gameSize && pj + winSize <= gameSize && player == board.getMatrix()[pi+k][pj+k]) {
                countDR++;
            }
            /* diagonal to left */
            if (pi + winSize <=gameSize && pj + 1 - winSize >= 0 && player == board.getMatrix()[pi+k][pj-k]) {
                countDL++;
            }
        }
        if (countH == winSize || countV == winSize || countDR == winSize || countDL == winSize) {
            win = true;
            if (renderWin) {
                for (int k = 0; k < winSize; k++) {
                    if (countH == winSize) {
                        buttonsMatrix.get(pi).get(pj+k).setBackground(Color.RED);
                    }
                    if (countV == winSize) {
                        buttonsMatrix.get(pi+k).get(pj).setBackground(Color.RED);
                    }
                    if (countDR == winSize) {
                        buttonsMatrix.get(pi+k).get(pj+k).setBackground(Color.RED);
                    }
                    if (countDL == winSize) {
                        buttonsMatrix.get(pi+k).get(pj-k).setBackground(Color.RED);
                    }
                }
            }
        }
        return win;
    }

    public boolean checkWins(ActionEvent a, int player) {
        boolean isWin = false;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (checkWinsFromPosition(a, player, i, j)) {
                    isWin = true;
                    break;
                }
            }
        }
        return isWin;
    }

    public ArrayList<ArrayList<JButton>> getButtonsMatrix() {
        return buttonsMatrix;
    }

    public Board getBoard() {
        return board;
    }

    public Integer getGameSize() {
        return gameSize;
    }

    public Integer getGameWinSize() {
        return gameWinSize;
    }

    public Boolean isWin() {
        return win;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }

    public void setComputerPlayer(Player computerPlayer) {
        this.computerPlayer = computerPlayer;
    }

    public void play(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new ConnectFour());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                //createMenuBar(frame); // Set the menu bar here
                frame.setVisible(true);

                int gameSize = 12; 		/* the size of the matrix */
                int gameWinSize = 5; 	/* the size for x-in-a-line to win */

                if (args != null && args.length >= 2) {
                    try {
                        gameSize = Integer.parseInt(args[0]);
                        gameWinSize = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid arguments. Using default values.");
                    }
                }

                TicTacToe ttt = new TicTacToe(gameSize, gameWinSize);
                MinimaxPlayer mmPlayer = new MinimaxPlayer(gameSize, gameWinSize);
                ttt.setComputerPlayer(mmPlayer);
                ttt.show();
            }
        });
    }

}