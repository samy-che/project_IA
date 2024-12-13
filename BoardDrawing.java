import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class BoardDrawing extends JComponent{
	private GameDisplay state;
	private Rectangle board;
	private Rectangle textBack;
	private String stateMessage;
	private ArrayList<Ellipse2D.Double> holes;
	final int BOARD_START_X = 182;
	final int BOARD_START_Y = 75;
	final int BOARD_WIDTH = 386;
	final int BOARD_HEIGHT = 340;
	final int HOLE_DIAMETER = 36;
	final int HOLE_DISTANCE = 50;
	final int HOLE_OFFSET = 25;
	final int HOLE_START_X = BOARD_START_X + HOLE_OFFSET;
	final int HOLE_START_Y = BOARD_START_Y + BOARD_HEIGHT - HOLE_OFFSET - HOLE_DIAMETER;
	
	
	public BoardDrawing(GameDisplay gs) {
		state = gs;
		//initializes the board
		board = new Rectangle(BOARD_START_X, BOARD_START_Y, BOARD_WIDTH, BOARD_HEIGHT);
		//initializes the gamestate message text back
		textBack = new Rectangle(BOARD_START_X + (BOARD_WIDTH / 2) - 100, BOARD_START_Y - 50, 200, 40);
		//initializes the holes in the board
		holes = new ArrayList<>();
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				Ellipse2D.Double hole = new Ellipse2D.Double(HOLE_START_X + i * HOLE_DISTANCE, HOLE_START_Y - j * HOLE_DISTANCE, HOLE_DIAMETER, HOLE_DIAMETER);
				holes.add(hole);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		//draws the board
		g2.setColor(Color.blue);
		g2.fill(board);
		
		//draws the holes
		g2.setColor(Color.white);
		for(Ellipse2D.Double hole : holes) g2.fill(hole);
		
		//draws the row labeling
		g2.setColor(Color.black);
		for(int i = 0; i < 7; i ++) {
			int w = (int) g2.getFontMetrics().stringWidth(Integer.toString(i+1));
			g2.drawString(Integer.toString(i+1), HOLE_START_X + HOLE_DIAMETER / 2 - w / 2 + i * HOLE_DISTANCE, BOARD_START_Y + BOARD_HEIGHT + 25);
		}
		
		//draws the proper game state message
		if(state.getRedWins()) {
			g2.setColor(Color.red);
			g2.fill(textBack);
			g.setColor(Color.black);
			stateMessage = "RED WINS!";
		}
		else if(state.getYellowWins()) {
			g2.setColor(Color.yellow);
			g2.fill(textBack);
			g.setColor(Color.black);
			stateMessage = "YELLOW WINS!";
		}
		else if(state.getGameOver()) {
			g2.setColor(Color.black);
			g2.fill(textBack);
			g.setColor(Color.white);
			stateMessage = "IT'S A TIE!";
		}
		else if(state.getRedsTurn()) {
			g2.setColor(Color.lightGray);
			g2.fill(textBack);
			g.setColor(Color.red);
			stateMessage = "Red's Turn";
		}
		else{
			g2.setColor(Color.lightGray);
			g2.fill(textBack);
			g.setColor(Color.yellow);
			stateMessage = "Yellow's Turn";
		}
		int stringWidth = (int) g2.getFontMetrics().stringWidth(stateMessage);
		int strX = BOARD_START_X + (BOARD_WIDTH  - stringWidth) / 2;
		g2.drawString(stateMessage, strX, BOARD_START_Y - 22);
		
		//draws the error message (if there is any)
		if(state.getError() != null) {
			g2.setColor(Color.black);
			g2.drawString("Oops!", 15, 220);
			g2.drawString(state.getError(), 15, 250);
		}
		
		//draws the pieces
		for(int i = 0; i < state.getPieces().length; i++) {
			boolean isVisible = state.getGameOver() || state.getPieces()[i][state.getPieces()[0].length - 1] != null;
			for(int j = state.getPieces()[0].length - 1; j > -1 ; j--) {
				if(state.getPieces()[i][j] == null);
				else if (state.getPieces()[i][j] == true) {
					isVisible = true;
					g2.setColor(Color.red);
					g2.fill(new Ellipse2D.Double(HOLE_START_X + 2 + i * HOLE_DISTANCE, HOLE_START_Y + 2 - j * HOLE_DISTANCE, HOLE_DIAMETER - 4, HOLE_DIAMETER - 4));
				}
				else {
					if(isVisible) {
						g2.setColor(Color.yellow);
						g2.fill(new Ellipse2D.Double(HOLE_START_X + 2 + i * HOLE_DISTANCE, HOLE_START_Y + 2 - j * HOLE_DISTANCE, HOLE_DIAMETER - 4, HOLE_DIAMETER - 4));
					}
				}
			}
		}
	}
}
	
	
