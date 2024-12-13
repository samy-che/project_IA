import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Class used to model the set of belief states already visited and to keep track of their values (in order to avoid visiting multiple times the same states)
 */
class ExploredSet{
	TreeMap<BeliefState, Float> exploredSet;
	
	/**
	 * construct an empty set
	 */
	public ExploredSet() {
		this.exploredSet = new TreeMap<BeliefState, Float>();
	}
	
	/**
	 * Search if a given state belongs to the explored set and returns its values if that is the case
	 * @param state the state for which the search takes place
	 * @return the value of the state if it belongs to the set, and null otherwise
	 */
	public Float get(BeliefState state) {
		Entry<BeliefState, Float> entry = this.exploredSet.ceilingEntry(state);
		if(entry == null || state.compareTo(entry.getKey()) != 0) {
			return null;
		}
		return entry.getValue() * state.probaSum() / entry.getKey().probaSum();
	}
	
	/**
	 * Put a belief state and its corresponding value into the set
	 * @param beliefState the belief state to be added
	 * @param value the
	 */
	public void put(BeliefState beliefState, float value) {
		this.exploredSet.put(beliefState, value);
	}
}

/**
 * Class used to store all possible results of performing an action at a given belief state
 */
class Results implements Iterable<BeliefState>{
	TreeMap<String, BeliefState> results;
	
	public Results(){
		this.results = new TreeMap<String, BeliefState>();
	}
	
	/**
	 * Return the belief state of the result that correspond to a given percept
	 * @param percept String that describe what is visible on the board for player 2
	 * @return belief state corresponding percept, or null if such a percept is not possible
	 */
	public BeliefState get(String percept) {
		return this.results.get(percept);
	}
	
	public void put(String s, BeliefState state) {
		this.results.put(s, state);
	}
	
	public Iterator<BeliefState> iterator(){
		return this.results.values().iterator();
	}
}

/**
 * Class used to represent a belief state i.e., a set of possible states the agent may be in
 */
class BeliefState implements Comparable<BeliefState>, Iterable<GameState>{
	private byte[] isVisible;
	
	private TreeSet<GameState> beliefState;
	
	private int played;
	
	public BeliefState() {
		this.beliefState = new TreeSet<GameState>();
		this.isVisible = new byte[6];
		for(int i = 0; i < 6; i++) {
			this.isVisible[i] = Byte.MIN_VALUE;
		}
		this.played = 0;
	}
	
	public BeliefState(byte[] isVisible, int played) {
		this();
		for(int i = 0; i < 6; i++) {
			this.isVisible[i] = isVisible[i];
		}
		this.played = played;
	}
	
	public void setStates(BeliefState beliefState) {
		this.beliefState = beliefState.beliefState;
		for(int i = 0; i < 6; i++) {
			this.isVisible[i] = beliefState.isVisible[i];
		}
		this.played = beliefState.played;
	}
	
	public boolean contains(GameState state) {
		return this.beliefState.contains(state);
	}

	/**
	 * returns the number of states in the belief state
	 * @return number of state
	 */
	public int size() {
		return this.beliefState.size();
	}
	
	public void add(GameState state) {
		if(!this.beliefState.contains(state)) {
			this.beliefState.add(state);
		}
		else {
			GameState copy = this.beliefState.floor(state);
			copy.addProba(state.proba());
		}
	}
	
	/**
	 * Compute the possible results from a given believe state, after the opponent perform an action. This function souhd be used only when this is the turn of the opponent.
	 * @return an objet of class result containing all possible result of an action performed by the opponent if this is the turn of the opponent, and null otherwise.
	 */
	public Results predict(){
		if(this.turn()) {
			Results tmstates = new Results();
			for(GameState state: this.beliefState) {
				RandomSelector rs = new RandomSelector();
				ArrayList<Integer> listColumn = new ArrayList<Integer>();
				ArrayList<Integer> listGameOver = new ArrayList<Integer>();
				int minGameOver = Integer.MAX_VALUE;
				for(int column = 0; column < 7; column++) {
					if(!state.isFull(column)) {
						GameState copy = state.copy();
						copy.putPiece(column);
						if(copy.isGameOver()) {
							listColumn.clear();
							listColumn.add(column);
							rs = new RandomSelector();
							rs.add(1);
							break;
						}
						int nbrGameOver = 0;
						for(int i = 0; i < 7; i++) {
							if(!copy.isFull(i)) {
								GameState copycopy = copy.copy();
								copycopy.putPiece(i);
								if(copycopy.isGameOver()) {
									nbrGameOver++;
								}
							}
						}
						if(nbrGameOver == 0) {
							rs.add(ProbabilisticOpponentAI.heuristicValue(state, column));
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
				int index = 0;
				if(listColumn.isEmpty()) {
					for(int column: listGameOver) {
						listColumn.add(column);
						rs.add(1);
					}
				}
				for(int column: listColumn) {
					GameState copy = state.copy();
					if(!copy.isFull(column)) {
						byte[] tab = new byte[6];
						for(int i = 0; i < 6; i++) {
							tab[i] = this.isVisible[i];
						}
						copy.putPiece(column);
						if(copy.isGameOver()) {
							for(int i = 0; i < 6; i++) {
								for(int j = 0; j < 7; j++) {
									BeliefState.setVisible(i, j, true, tab);
								}
							}
						}
						else {
							boolean isVisible = copy.isGameOver() || copy.isFull(column);
							BeliefState.setVisible(5, column, isVisible, tab);
							for(int row = 4; row > -1; row--) {
								isVisible = isVisible || copy.content(row, column) == 2;
								BeliefState.setVisible(row, column, isVisible, tab);
							}
						}
						String s = "";
						char c = 0;
						for(int i = 0; i < 6; i++) {
							int val = tab[i] + 128;
							s += ((char)(val % 128));
							c += (val / 128) << i;
						}
						s += c;
						copy.multProba(rs.probability(index++));
						BeliefState bs = tmstates.get(s);
						if(bs!= null) {
							bs.add(copy);
						}
						else {
							bs = new BeliefState(tab, this.played + 1);
							bs.add(copy);
							tmstates.put(s, bs);
						}
					}
				}
			}
			return tmstates;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Perform the action corresponding for the player to play a given column, and return the result of this action for each state of the belief state as a Results
	 * @param column index of the column played
	 * @return object of type Results representing all states resulting from playing the column if this is the turn of the player, and null otherwise
	 */
	public Results putPiecePlayer(int column){
		if(!this.turn()) {
			Results tmstates = new Results();
			for(GameState state: this.beliefState) {
				GameState copy = state.copy();
				byte[] tab = new byte[6];
				for(int i = 0; i < 6; i++) {
					tab[i] = this.isVisible[i];
				}
				copy.putPiece(column);
				if(copy.isGameOver()) {
					for(int i = 0; i < 6; i++) {
						for(int j = 0; j < 7; j++) {
							BeliefState.setVisible(i, j, true, tab);
						}
					}
				}
				else {
					boolean isVisible = copy.isFull(column);
					BeliefState.setVisible(5, column, isVisible, tab);
					for(int row = 4; row > -1; row--) {
						isVisible = isVisible || copy.content(row, column) == 2;
						BeliefState.setVisible(row, column, isVisible, tab);
					}
				}
				String s = "";
				char c = 0;
				for(int i = 0; i < 6; i++) {
					int val = tab[i] + 128;
					s += ((char)(val % 128));
					c += (val / 128) << i;
				}
				s += c;
				BeliefState bs = tmstates.get(s);
				if(bs!= null) {
					bs.add(copy);
				}
				else {
					bs = new BeliefState(tab, this.played + 1);
					bs.add(copy);
					tmstates.put(s, bs);
				}
			}
			return tmstates;
		}
		else {
			return null;
		}
		
	}
	
	public static BeliefState filter(Results beliefStates, GameState state) {
		byte tab[] = new byte[6];
		for(int i = 0; i < 6; i++) {
			tab[i] = Byte.MIN_VALUE;
		}
		for(int column = 0; column < 7; column++) {
			boolean isVisible = state.isGameOver() || state.isFull(column);
			BeliefState.setVisible(5, column, isVisible, tab);
			for(int row = 4; row > -1; row--) {
				isVisible = isVisible || (state.content(row, column) == 2);
				BeliefState.setVisible(row, column, isVisible, tab);
			}
		}
		String s = "";
		char c = 0;
		for(int i = 0; i < 6; i++) {
			int val = tab[i] + 128;
			s += ((char)(val % 128));
			c += (val / 128) << i;
		}
		s += c;
		BeliefState beliefState = beliefStates.get(s);
		RandomSelector rs = new RandomSelector();
		for(GameState st: beliefState.beliefState) {
			rs.add(st.proba());
		}
		int i = 0;
		for(GameState st: beliefState.beliefState) {
			st.setProba(rs.probability(i++));
		}
		return beliefState;
	}
	
	/**
	 * Make a copy of the belief state containing the same states
	 * @return copy of the belief state
	 */
	public BeliefState copy() {
		BeliefState bs = new BeliefState();
		for(GameState state: this.beliefState) {
			bs.add(state.copy());
		}
		for(int i = 0; i < 6; i++) {
			bs.isVisible[i] = this.isVisible[i];
		}
		bs.played = this.played;
		return bs;
	}
	
	public Iterator<GameState> iterator(){
		return this.beliefState.iterator();
	}
	
	/**
	 * Return the list of the column where a piece can be played (columns which are not full)
	 * @return
	 */
	public ArrayList<Integer> getMoves(){
		if(!this.isGameOver()) {
			ArrayList<Integer> moves = new ArrayList<Integer>();
			GameState state = this.beliefState.first();
			for(int i = 0; i < 7; i++) {
				if(!state.isFull(i))
					moves.add(i);
			}
			return moves;
		}
		else {
			return new ArrayList<Integer>();
		}
	}
	
	/**
	 * Provide information about the next player to play
	 * @return true if the next to play is the opponent, and false otherwise
	 */
	public boolean turn() {
		return this.beliefState.first().turn();
	}
	
	public boolean isVisible(int row, int column) {
		int pos = row * 7 + column;
		int index = pos / 8;
		pos = pos % 8;
		return ((this.isVisible[index] + 128) >> pos) % 2 == 1;
	}
	
	public void setVisible(int row, int column, boolean val) {
		int pos = row * 7 + column;
		int index = pos / 8;
		pos = pos % 8;
		int delta = ((val? 1: 0) - (this.isVisible(row, column)? 1: 0)) << pos;
		this.isVisible[index] = (byte) (this.isVisible[index] + delta);
	}
	
	public static void setVisible(int row, int column, boolean val, byte[] tab) {
		int pos = row * 7 + column;
		int index = pos / 8;
		pos = pos % 8;
		int posValue = tab[index] + 128;
		int delta = ((val? 1: 0) - ((posValue >> pos) % 2)) << pos;
		tab[index] = (byte) (posValue + delta - 128);
	}
	
	/**
	 * Check if the game is over in all state of the belief state. Note that when the game is over, the board is revealed and the environment becomes observable.
	 * @return true if the game is over, and false otherwise
	 */
	public boolean isGameOver() {
		for(GameState state: this.beliefState) {
			if(!state.isGameOver()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if all the games in the belief state are full
	 * @return
	 */
	public boolean isFull() {
		return this.beliefState.first().isFull();
	}

	
	public void restart() {
		this.beliefState = new TreeSet<GameState>();
		this.isVisible = new byte[6];
		for(int i = 0; i < 6; i++) {
			this.isVisible[i] = Byte.MIN_VALUE;
		}
		this.played = 0;
	}
	
	public String toString() {
		String s = "BeliefState: size = " + this.beliefState.size() + " played = " + this.played + "\n";
		for(int row = 5; row > -1; row--) {
			for(int column = 0; column < 7; column++) {
				s += this.isVisible(row, column)? "1": "0";
			}
			s += "\n";
		}
		for(GameState state:this.beliefState) {
			s += state.toString() + "\n";
		}
		return s;
	}
	
	public int compareTo(BeliefState bs) {
		if(this.played != bs.played)
			return this.played > bs.played? 1: -1;
		for(int i = 0; i < 6; i++) {
			if(this.isVisible[i] != bs.isVisible[i])
				return this.isVisible[i] > bs.isVisible[i]? 1: -1;
		}
		if(this.beliefState.size() != bs.beliefState.size()) {
			return this.beliefState.size() > bs.beliefState.size()? 1: -1;
		}
		Iterator<GameState> iter = bs.beliefState.iterator();
		for(GameState next: this.beliefState) {
			GameState otherNext = iter.next();
			int comp = next.compareTo(otherNext);
			if(comp != 0)
				return comp;
		}
		iter = bs.beliefState.iterator();
		float sum1 = this.probaSum(), sum2 = bs.probaSum();
		for(GameState next: this.beliefState) {
			GameState otherNext = iter.next();
			if(Math.abs(next.proba() * sum1 - otherNext.proba() * sum2) > 0.001) {
				return next.proba() > otherNext.proba()? 1: -1;
			}
		}
		return 0;
	}
	
	public float probaSum() {
		float sum = 0;
		for(GameState state: this.beliefState) {
			sum += state.proba();
		}
		return sum;
	}
}

public class AI{
	
	public AI() {
	}
	
	public static int findNextMove(BeliefState game) {
		return 0;
	}
}
