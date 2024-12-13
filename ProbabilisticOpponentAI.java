import java.util.ArrayList;

public class ProbabilisticOpponentAI {
	/**
	 * Compute the next move of the opponent. The process is random, and the probability at use are based on the current state of the game. Note that the opponent has full access to the board.
	 * @param game current state of the game (which is fully visible)
	 * @return an index corresponding the column played by the opponent. If the game is full (no move available) then it return -1.
	 */
	public int decision(GameState game) {
		RandomSelector rs = new RandomSelector();
		ArrayList<Integer> listColumn = new ArrayList<Integer>();
		ArrayList<Integer> listGameOver = new ArrayList<Integer>();
		int minGameOver = Integer.MAX_VALUE;
		for(int column = 0; column < 7; column++) {
			if(!game.isFull(column)) {
				GameState copy = game.copy();
				copy.putPiece(column);
				if(copy.isGameOver())
					return column;
				int nbrGameOver = 0;
				for(int i = 0; i < 7; i++) {
					GameState copycopy = copy.copy();
					copycopy.putPiece(i);
					if(copycopy.isGameOver()) {
						nbrGameOver++;
					}
				}
				if(nbrGameOver == 0) {
					rs.add(ProbabilisticOpponentAI.heuristicValue(game, column));
					listColumn.add(column);
				}
				else {
					if(minGameOver > nbrGameOver) {
						minGameOver = nbrGameOver;
						listGameOver.clear();
						listGameOver.add(column);
					}
					else {
						if(minGameOver == nbrGameOver) {
							listGameOver.add(column);
						}
					}
				}
			}
		}
		if(listColumn.size() > 0) {
			//System.out.println(game.toString() + "\n" + rs.toString());
			return listColumn.get(rs.randomChoice());
		}
		else {
			for(int i = 0; i < listGameOver.size(); i++) {
				rs.add(1);
			}
			return listGameOver.get(rs.randomChoice());
		}
	}
	
	/**
	 * Compute an heuristic value for a given move which will be used to assess the probability to choose this move
	 * @param game the current state of the game
	 * @param column the index of the column to be played
	 * @return a value which assess the quality of the move (larger is better)
	 */
	public static float heuristicValue(GameState game, int column) {
		float hValue = Float.MIN_NORMAL;
		int row = 0;
		while(game.content(row, column) != 0) {
			row++;
		}
		int southPieces = 0, south = 0;
		while(south < row && game.content(row - south - 1, column) != 2) {
			south++;
			if(game.content(row - south, column) == 1) {
				southPieces++;
			}
		}
		if(southPieces + 6 - row > 3) {
			hValue += (southPieces + 1.) * (southPieces + 1.) / (southPieces + 6. - row);
		}
		
		int westPieces = 0, west = 0;
		while(west < column && game.content(row, column - west - 1) != 2) {
			west++;
			if(game.content(row, column - west) == 1) {
				westPieces++;
			}
		}
		int estPieces = 0, est = 0;
		while(column + est < 6 && game.content(row, column + est + 1) != 2) {
			est++;
			if(game.content(row, column + est) == 1) {
				estPieces++;
			}
		}
		if(est + west > 2) {
			hValue += (estPieces + westPieces + 1.) * (estPieces + westPieces + 1.) / (est + west + 1.);
		}
		int southWestPieces = 0, southWest = 0;
		while(southWest < row && southWest < column  && game.content(row - southWest - 1, column - southWest - 1) != 2) {
			southWest++;
			if(game.content(row - southWest, column - southWest) == 1) {
				southWestPieces++;
			}
		}
		int northEstPieces = 0, northEst = 0;
		while(row + northEst < 5 && column + northEst < 6 && game.content(row + northEst + 1, column + northEst + 1) != 2) {
			northEst++;
			if(game.content(row + northEst, column + northEst) == 1) {
				northEstPieces++;
			}
		}
		if(southWest + northEst > 2) {
			hValue += (southWestPieces + northEstPieces + 1.) * (southWestPieces + northEstPieces + 1.) / (southWest + northEst + 1.);
		}
		int southEstPieces = 0, southEst = 0;
		while(southEst < row && column + southEst < 6 && game.content(row - southEst - 1, column + southEst + 1) != 2) {
			southEst++;
			if(game.content(row - southEst, column + southEst) == 1) {
				southEstPieces++;
			}
		}
		int northWestPieces = 0, northWest = 0;
		while(row + northWest < 5 && northWest < column  && game.content(row + northWest + 1, column - northWest - 1) != 2) {
			northWest++;
			if(game.content(row + northWest, column - northWest) == 1) {
				northWestPieces++;
			}
		}
		if(northWest + southEst > 2) {
			hValue += (northWestPieces + southEstPieces + 1.) * (northWestPieces + southEstPieces + 1.) / (northWest + southEst + 1.);
		}
		return hValue;
	}
}
