package playingwithbot;

public abstract class Player {

    protected Integer[][] matrix;
    protected Integer gameSize;
    protected Integer gameWinSize;

    public Player(Integer gameSize, Integer gameWinSize, Integer[][] matrix) {
        this.gameSize = gameSize;
        this.gameWinSize = gameWinSize;
        this.matrix = matrix;
    }

    public Player(Integer gameSize, Integer gameWinSize) {
        this.gameSize = gameSize;
        this.gameWinSize = gameWinSize;
    }

    public Player(TicTacToe ticTacToe) {
        System.out.println("AI initialized");
        this.gameSize = ticTacToe.getGameSize();
        this.gameWinSize = ticTacToe.getGameWinSize();
        this.matrix = new Integer[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                matrix[i][j] = ticTacToe.getBoard().getMatrix()[i][j];
            }
        }
    }

    public abstract int[] move();

    public Integer[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Integer[][] matrix) {
        this.matrix = matrix;
    }

    public Integer getGameSize() {
        return gameSize;
    }

    public void setGameSize(Integer gameSize) {
        this.gameSize = gameSize;
    }

    public Integer getGameWinSize() {
        return gameWinSize;
    }

    public void setGameWinSize(Integer gameWinSize) {
        this.gameWinSize = gameWinSize;
    }

}