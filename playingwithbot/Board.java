package playingwithbot;

public class Board {

    private Integer[][] matrix;

    public static final int EMPTY = 0;

    public static final int PLAYER_X = 1;

    public static final int PLAYER_O = 2;

    public Board(Integer size) {
        matrix = new Integer[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                matrix[i][j] = Board.EMPTY;
            }
        }
    }

    public Integer[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Integer[][] matrix) {
        this.matrix = matrix;
    }

    public static int getOtherPlayer(int player) {
        int otherPlayer = 0;
        if (player == Board.PLAYER_O) {
            otherPlayer = Board.PLAYER_X;
        } else if (player == Board.PLAYER_X) {
            otherPlayer = Board.PLAYER_O;
        }
        return otherPlayer;
    }

    public static String getLetter(int player) {
        String letter = "";
        if (player == Board.PLAYER_O) {
            letter = TicTacToe.LETTER_PLAYER_O;
        } else if (player == Board.PLAYER_X) {
            letter = TicTacToe.LETTER_PLAYER_X;
        }
        return letter;
    }

}