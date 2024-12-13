import java.util.ArrayList;

/**
 * Class which keep tracks of the state of the game (content of the board). In this class, all elements are visible (as in the standard version of connected 4).
 */

public class GameState implements Comparable<GameState> {
	private byte[] board;
	private float proba;
	
	/**
	 * Constructor which construct a state corresponding to an empty game (no pieces on the board)
	 */
	public GameState() {
		this.board = new byte[11];
		this.proba = 1;
		for(int i = 0; i < 11; i++) {
			this.board[i] = Byte.MIN_VALUE;
		}
	}
	
	/**
	 * Construct a copy of a given state. The pieces are placed in the same way.
	 * @return a state corresponding to a copy of the game
	 */
	public GameState copy() {
		GameState copy = new GameState();
		for(int i = 0; i < 11; i++) {
			copy.board[i] = this.board[i];
		}
		copy.proba = this.proba;
		return copy;
	}
	
	/**
	 * Returns the content of a given square of the game
	 * @param row index of the row (from 0 to 5)
	 * @param column index of the column (from 0 to 6)
	 * @return 0 if the square is empty, 1 if it contains a yellow piece and 2 if it contains a red piece.
	 */
	public int content(int row, int column) {
		int pos = row * 14 + column * 2;
		int index = pos / 8;
		pos = pos % 8;
		return (((this.board[index] + 128) >> pos) % 4);
	}
	
	/**
	 * return the probability of the state. This probability is meaningful only if the state is part of a belief state.
	 * @return the probability that a given state occurs in a given belief state
	 */
	
	public float proba() {
		return this.proba;
	}
	
	public void addProba(float addi) {
		this.proba += addi;
	}
	
	public void multProba(float multi) {
		this.proba *= multi;
	}
	
	public void setProba(float proba) {
		this.proba = proba;
	}
	
	/**
	 * check if a given column is full (contains 6 pieces of any kind)
	 * @param column the index of the column
	 * @return true if the column is full, and false otherwise
	 */
	public boolean isFull(int column) {
		return this.content(5, column) != 0;
	}
	
	/**
	 * check if all columns are full (meaning that the game is over)
	 * @return true if all columns are full
	 */
	public boolean isFull() {
		for(int column = 0; column < 7; column++) {
			if(!this.isFull(column))
				return false;
		}
		return true;
	}
	
	public void set(int row, int column, int val) {
		int pos = row * 14 + column * 2;
		int index = pos / 8;
		pos = pos % 8;
		int delta = ((val % 4) - this.content(row, column)) << pos;
		this.board[index] = (byte) (this.board[index] + delta);
	}
	
	/**
	 * check if the game is over i.e., either the game is full or one of the player won
	 * @return
	 */
	public boolean isGameOver() {
		return ((this.board[10] + 128) >> 4) % 2 > 0? true: false;
	}
	
	public boolean putPiece(int column) {
		if(this.isGameOver())
			return false;
		if(this.content(5, column) != 0)
			return false;
		int row = 0;
		while(this.content(row, column) != 0)
			row++;
		this.set(row, column, this.turn()? 1: 2);
		if(this.checkWin(row, column)) {
			this.board[10] += 16;
		}
		else
			this.changeTurn();
		return true;
	}
	
	
	public boolean turn() {
		return ((this.board[10] + 128) >> 5) % 2 > 0? true: false;
	}
	
	public void changeTurn() {
		if(this.turn()) {
			this.board[10] = (byte) (this.board[10] - 32);
		}
		else {
			this.board[10] = (byte) (this.board[10] + 32);
		}
	}
	
	public boolean checkWin(int row, int column) {
		int turn = this.turn()? 1: 2;
		
		int south = 0;
		while(row - south > 0 && this.content(row - south - 1, column) == turn) {
			south++;
		}
		if(south > 2) {
			return true;
		}
		
		int west = 0;
		while(west < column && this.content(row, column - west - 1) == turn) {
			west++;
		}
		int est = 0;
		while(column + est < 6 && this.content(row, column + est + 1) == turn) {
			est++;
		}
		if(est + west > 2) {
			return true;
		}
		int southWest = 0;
		while(southWest < row && southWest < column  && this.content(row - southWest - 1, column - southWest - 1) == turn) {
			southWest++;
		}
		int northEst = 0;
		while(row + northEst < 5 && column + northEst < 6 && this.content(row + northEst + 1, column + northEst + 1) == turn) {
			northEst++;
		}
		if(southWest + northEst > 2) {
			return true;
		}
		int southEst = 0;
		while(southEst < row && column + southEst < 6 && this.content(row - southEst - 1, column + southEst + 1) == turn) {
			southEst++;
		}
		int northWest = 0;
		while(row + northWest < 5 && northWest < column  && this.content(row + northWest + 1, column - northWest - 1) == turn) {
			northWest++;
		}
		if(northWest + southEst > 2) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String s = "";
		for(int i = 5; i >= 0; i--) {
			for(int j = 0; j < 7; j++) {
				switch(this.content(i, j)) {
				case 1: s += "o"; break;
				case 2: s += "*"; break;
				case 0: s += "."; break;
				default: s += "F"; break;
				}
			}
			s += "\n";
		}
		s += "Game Over: " + (this.isGameOver()? "Yes": "No") + "\nNext: " + (this.turn()? "Yellow": "Red") + " Proba: " + this.proba;
		return s;
	}
	
	public int compareTo(GameState toCompare) {
		for(int i = 0; i < 11; i++) {
			if(this.board[i] > toCompare.board[i]) {
				return 1;
			}
			if(this.board[i] < toCompare.board[i]) {
				return -1;
			}
		}
		return 0;
	}
}
