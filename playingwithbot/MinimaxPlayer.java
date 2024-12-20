package playingwithbot;

import java.util.ArrayList;
import java.util.List;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(Integer gameSize, Integer gameWinSize, Integer[][] matrix) {
        super(gameSize, gameWinSize, matrix);
    }

    public MinimaxPlayer(Integer gameSize, Integer gameWinSize) {
        super(gameSize, gameWinSize);
    }

    public MinimaxPlayer(TicTacToe ticTacToe) {
        super(ticTacToe);
    }

    public int[] move() {
        long[] result = minimax(2, Board.PLAYER_O, Long.MIN_VALUE, Long.MAX_VALUE, matrix);
        System.out.println("\nCalculated move: [" + result[1] + ", " + result[2] + "]");
        return new int[] { (int) result[1], (int) result[2] };
    }

    private String repeatStr(String str, int count) {
        String val = "";
        for (int i=0; i<count; i++) {
            val += str;
        }
        return val;
    }

    private String matrixToString(Integer[][] matrix) {
        StringBuffer val = new StringBuffer("{");
        for (int i = 0; i < gameSize; i++) {
            val.append("{ ");
            if (i!=0)
                val.append(", ");
            for (int j = 0; j < gameSize; j++) {
                if (j!=0)
                    val.append(", ");
                val.append(matrix[i][j]);
            }
            val.append(" }");
        }
        val.append("}");
        return val.toString();
    }


    private long[] minimax(int depth, int player, long alpha, long beta, Integer[][] matrix) {
        // Generate possible next moves in a list of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // mySeed is maximizing; while oppSeed is minimizing
        long score;
        int bestRow = -1;
        int bestCol = -1;


        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            score = computeScore(player, bestRow, bestCol);
            return new long[] { score, bestRow, bestCol };
        } else {
            for (int[] move : nextMoves) {
                // try this move for the current "player"
                matrix[move[0]][move[1]] = player;
                if (depth % 2 != 0) {
                    // O (computer) is maximizing player
                    score = computeScore(Board.PLAYER_X, move[0], move[1]);
                    System.out.println(repeatStr(" ", 2-depth)+"Maximizing " + Board.PLAYER_X + "["+move[0]+","+move[1]+"]..." + "                        " + matrixToString(matrix));
                    score = minimax(depth - 1, Board.PLAYER_X, alpha, beta, matrix)[0];
                    System.out.println(repeatStr(" ", 2-depth)+"Maximizing " + Board.PLAYER_X + "["+move[0]+","+move[1]+"]" + " returns score: " + score + (score > alpha ? " *" : "") + "        " + matrixToString(matrix));
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    // X (opponent) is minimizing player
                    score = computeScore(Board.PLAYER_O, move[0], move[1]);
                    System.out.println(repeatStr(" ", 2-depth)+"Minimizing " + Board.PLAYER_O + "["+move[0]+","+move[1]+"]..." + "                        " + matrixToString(matrix));
                    score = minimax(depth - 1, Board.PLAYER_O, alpha, beta, matrix)[0];
                    System.out.println(repeatStr(" ", 2-depth)+"Minimizing " + Board.PLAYER_O + "["+move[0]+","+move[1]+"]" + " returns score: " + score + (score > beta ? " *" : "") + "        " + matrixToString(matrix));
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // undo move
                matrix[move[0]][move[1]] = Board.EMPTY;
                // cut-off
                if (alpha >= beta)
                    break;
            }
            return new long[] { (player == Board.PLAYER_O) ? alpha : beta, bestRow, bestCol };
        }
    }

    public int countWinsFromPosition(int player, int pi, int pj, int winSize, int withFreeSize) {
        int winsCount = 0;
        boolean otherFoundHR = false;
        boolean otherFoundHL = false;
        boolean otherFoundVD = false;
        boolean otherFoundVU = false;
        boolean otherFoundDRU = false;
        boolean otherFoundDRD = false;
        boolean otherFoundDLU = false;
        boolean otherFoundDLD = false;
        int countOccupiedH = -1;
        int countOccupiedV = -1;
        int countOccupiedDR = -1;
        int countOccupiedDL = -1;
        int countEmptyH = 0;
        int countEmptyV = 0;
        int countEmptyDR = 0;
        int countEmptyDL = 0;
        int otherPlayer = Board.getOtherPlayer(player);
        for (int k = 0; k < winSize + withFreeSize; k++) {
            /* horizontal to right */
            if (pj + k < gameSize && !otherFoundHR) {
                if (otherPlayer == matrix[pi][pj + k]) {
                    otherFoundHR = true;
                }
                if (player == matrix[pi][pj + k]) {
                    countOccupiedH++;
                }
                if (Board.EMPTY == matrix[pi][pj + k]) {
                    countEmptyH++;
                }
            }
            /* horizontal to left */
            if (pj - k >= 0 && !otherFoundHL) {
                if (otherPlayer == matrix[pi][pj - k]) {
                    otherFoundHL = true;
                }
                if (player == matrix[pi][pj - k]) {
                    countOccupiedH++;
                }
                if (Board.EMPTY == matrix[pi][pj - k]) {
                    countEmptyH++;
                }
            }
            /* vertical to down */
            if (pi + k < gameSize && !otherFoundVD) {
                if (otherPlayer == matrix[pi + k][pj]) {
                    otherFoundVD = true;
                }
                if (player == matrix[pi + k][pj]) {
                    countOccupiedV++;
                }
                if (Board.EMPTY == matrix[pi + k][pj]) {
                    countEmptyV++;
                }
            }
            /* vertical to up */
            if (pi - k >= 0 && !otherFoundVU) {
                if (otherPlayer == matrix[pi - k][pj]) {
                    otherFoundVU = true;
                }
                if (player == matrix[pi - k][pj]) {
                    countOccupiedV++;
                }
                if (Board.EMPTY == matrix[pi - k][pj]) {
                    countEmptyV++;
                }
            }
            /* diagonal to right to down */
            if (pi + k < gameSize && pj + k < gameSize && !otherFoundDRD) {
                if (otherPlayer == matrix[pi + k][pj + k]) {
                    otherFoundDRD = true;
                }
                if (player == matrix[pi + k][pj + k]) {
                    countOccupiedDR++;
                }
                if (Board.EMPTY == matrix[pi + k][pj + k]) {
                    countEmptyDR++;
                }
            }
            /* diagonal to right to up */
            if (pi - k >= 0 && pj - k >= 0 && !otherFoundDRU) {
                if (otherPlayer == matrix[pi - k][pj - k]) {
                    otherFoundDRU = true;
                }
                if (player == matrix[pi - k][pj - k]) {
                    countOccupiedDR++;
                }
                if (Board.EMPTY == matrix[pi - k][pj - k]) {
                    countEmptyDR++;
                }
            }
            /* diagonal to left to down */
            if (pi + k < gameSize && pj - k >= 0 && !otherFoundDLD) {
                if (otherPlayer == matrix[pi + k][pj - k]) {
                    otherFoundDLD = true;
                }
                if (player == matrix[pi + k][pj - k]) {
                    countOccupiedDL++;
                }
                if (Board.EMPTY == matrix[pi + k][pj - k]) {
                    countEmptyDL++;
                }
            }
            /* diagonal to left to up */
            if (pi - k >= 0 && pj + k < gameSize && !otherFoundDLU) {
                if (otherPlayer == matrix[pi - k][pj + k]) {
                    otherFoundDLU = true;
                }
                if (player == matrix[pi - k][pj + k]) {
                    countOccupiedDL++;
                }
                if (Board.EMPTY == matrix[pi - k][pj + k]) {
                    countEmptyDL++;
                }
            }
        }
        if (countOccupiedH >= winSize && countEmptyH >= withFreeSize) {
            winsCount++;
        }
        if (countOccupiedV >= winSize && countEmptyV >= withFreeSize) {
            winsCount++;
        }
        if (countOccupiedDR >= winSize && countEmptyDR >= withFreeSize) {
            winsCount++;
        }
        if (countOccupiedDL >= winSize && countEmptyDL >= withFreeSize) {
            winsCount++;
        }
        return winsCount;
    }

    public int countWins(int player, int winSize, int withFreeSize) {
        int winsCount = 0;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                winsCount += countWinsFromPosition(player, i, j, winSize, withFreeSize);
            }
        }
        return winsCount;
    }

	/*
	public long computeScore(int player, int row, int col) {
		long score = 0;
		double tmpScore = 0;
		int otherPlayer = Board.getOtherPlayer(player);
		//Rule 1: If I have a winning move, take it.
		tmpScore = countWins(player, gameWinSize, 0) * Math.pow(10, gameWinSize);
		score += tmpScore;
		//System.out.println("  " + player+"("+gameWinSize+",0)[" + row + ", " + col + "]: " + tmpScore);
		//System.out.println("[" + row + ", " + col + "]: " + score);
		for (int i = gameWinSize - 1; i > 0; i--) {
			score += countWins(player, i, gameWinSize - i) * Math.pow(10, i);
			//System.out.println("   {" + i + ", " + (gameWinSize - i) + "} ");
		}
		//Rule 2: If the opponent has a winning move, block it.
		if (row > 0 && col > 0) {
			matrix[row][col] = otherPlayer;
			tmpScore = countWins(otherPlayer, gameWinSize, 0) * Math.pow(10, gameWinSize - 1) + 1;
			score += tmpScore;
			//System.out.println("  " + otherPlayer+"("+gameWinSize+",0)[" + row + ", " + col + "]: " + tmpScore);
			for (int i = gameWinSize - 1; i > 0; i--) {
				score += countWins(otherPlayer, i, gameWinSize - i) * Math.pow(10, i - 1);
			}
			matrix[row][col] = player;
		}
		//System.out.println("  " + otherPlayer+"("+gameWinSize+",0)[" + row + ", " + col + "]: " + score);
		//System.out.println("[" + row + ", " + col + "]: " + score);
		//Rule 3: If I can create a fork (two winning ways) after this move, do it.
		//Rule 4: Do not let the opponent creating a fork after my move. (Opponent may block your winning move and create a fork.)
		//Rule 5: Place in the position such as I may win in the most number of possible ways.
		return score;
	}
	 */

    public long computeScore(int player, int row, int col) {
        long score = 0;
        score += countWins(player, gameWinSize, 0) * Math.pow(10, gameWinSize);
        for (int i = gameWinSize - 1; i > 0; i--) {
            score += countWins(player, i, gameWinSize - i) * Math.pow(10, i - 1);
        }
        //System.out.println("> " + Board.getLetter(player) + "[" + row + ", " + col + "]=" + score + " ");
        return score;
    }
    /*
     */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>();

        // Search for empty cells and add to the List
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == Board.EMPTY) {
                    nextMoves.add(new int[] { i, j });
                }
            }
        }
        return nextMoves;
    }

}
