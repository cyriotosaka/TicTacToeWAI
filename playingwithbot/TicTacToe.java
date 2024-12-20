
/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026221177 - Muhammad Ariq Alwin
 * 2 - 5026231065 - Beh Siu Li
 * 3 - 5026231168 - Okky Priscila Putri
 */
package playingwithbot;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class TicTacToe implements ActionListener {

    public static final String TITLE = "TicTacToe BOT";
    private JFrame window = new JFrame(TITLE);
    private ArrayList<ArrayList<JButton>> buttonsMatrix = new ArrayList<>();
    private Board board;
    private int movesCount;
    private Integer gameSize;
    private Integer gameWinSize;
    public static String LETTER_PLAYER_X = "X";
    public static String LETTER_PLAYER_O = "O";
    private Boolean win;
    private Boolean visible = false;
    private Player computerPlayer;

    public TicTacToe() {
        this(3, 3); // Default to 3x3 board
    }

    public TicTacToe(Integer size, Integer winSize) {
        this.gameSize = size;
        this.gameWinSize = winSize;
        board = new Board(size);
        window.setSize(50 * size, 50 * size);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            ArrayList<JButton> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                JButton button = new JButton("");
                window.add(button);
                button.addActionListener(this);
                row.add(button);
            }
            buttonsMatrix.add(row);
        }
    }

    public void show() {
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
        // User's turn
        renderMove(a, LETTER_PLAYER_X);
        win = checkWins(a, Board.PLAYER_X);
        renderCheckWins(a);
        movesCount++;
        if (win) {
            return;
        }

        // Computer's turn
        computerPlayer.setMatrix(board.getMatrix());
        int[] move = computerPlayer.move();
        if (move[0] >= 0 && move[1] >= 0) {
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
        if (win) {
            renderEndGame(a);
            if (visible) {
                JOptionPane.showMessageDialog(null, letter + " WINS!");
            }
        } else if (movesCount + 1 == gameSize * gameSize && !win) {
            if (visible) {
                JOptionPane.showMessageDialog(null, "Tie Game!");
            }
        }
        movesCount++;
    }

    private void renderCheckWins(ActionEvent a) {
        String letter = movesCount % 2 == 0 ? LETTER_PLAYER_X : LETTER_PLAYER_O;
        if (win) {
            renderEndGame(a);
            if (visible) {
                JOptionPane.showMessageDialog(null, letter + " WINS!");
            }
        } else if (movesCount + 1 == gameSize * gameSize && !win) {
            if (visible) {
                JOptionPane.showMessageDialog(null, "Tie Game!");
            }
        }
    }

    private void renderMove(ActionEvent a, String letter) {
        for (int i = 0; i < buttonsMatrix.size(); i++) {
            for (int j = 0; j < buttonsMatrix.get(i).size(); j++) {
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
            if (pj + winSize <= gameSize && player == board.getMatrix()[pi][pj + k]) {
                countH++;
            }
            if (pi + winSize <= gameSize && player == board.getMatrix()[pi + k][pj]) {
                countV++;
            }
            if (pi + winSize <= gameSize && pj + winSize <= gameSize && player == board.getMatrix()[pi + k][pj + k]) {
                countDR++;
            }
            if (pi + winSize <= gameSize && pj + 1 - winSize >= 0 && player == board.getMatrix()[pi + k][pj - k]) {
                countDL++;
            }
        }
        if (countH == winSize || countV == winSize || countDR == winSize || countDL == winSize) {
            win = true;
            if (renderWin) {
                for (int k = 0; k < winSize; k++) {
                    if (countH == winSize) {
                        buttonsMatrix.get(pi).get(pj + k).setBackground(Color.RED);
                    }
                    if (countV == winSize) {
                        buttonsMatrix.get(pi + k).get(pj).setBackground(Color.RED);
                    }
                    if (countDR == winSize) {
                        buttonsMatrix.get(pi + k).get(pj + k).setBackground(Color.RED);
                    }
                    if (countDL == winSize) {
                        buttonsMatrix.get(pi + k).get(pj - k).setBackground(Color.RED);
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
//                frame.setContentPane(new ConnectFour());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                int gameSize = 3; // Default to 3x3 board
                int gameWinSize = 3; // Default win condition

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