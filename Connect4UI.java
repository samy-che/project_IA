import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Connect4UI {
	private static boolean isAI = true;
	public static void main(String[] args)
	   {
	      JFrame frame = new JFrame("CONNECT 4");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      final GameDisplay state = new GameDisplay();
	      final BeliefState beliefState = new BeliefState();
	      beliefState.add(state.getState().copy());
	      final BoardDrawing board = new BoardDrawing(state);
	      final ProbabilisticOpponentAI iap = new ProbabilisticOpponentAI();
	      
	      JButton button1 = new JButton("1");
	      button1.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(1);
		            	Results beliefStates = beliefState.putPiecePlayer(0);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      JButton button2 = new JButton("2");
	      button2.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(2);
		            	Results beliefStates = beliefState.putPiecePlayer(1);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      JButton button3 = new JButton("3");
	      button3.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(3);
		            	Results beliefStates = beliefState.putPiecePlayer(2);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      JButton button4 = new JButton("4");
	      button4.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(4);
		            	Results beliefStates = beliefState.putPiecePlayer(3);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      JButton button5 = new JButton("5");
	      button5.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(5);
		            	Results beliefStates = beliefState.putPiecePlayer(4);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      JButton button6 = new JButton("6");
	      button6.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(6);
		            	Results beliefStates = beliefState.putPiecePlayer(5);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      JButton button7 = new JButton("7");
	      button7.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.move(7);
		            	Results beliefStates = beliefState.putPiecePlayer(6);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		int aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });
	      
	      /*JButton buttonAI = new JButton("AI play");
	      buttonAI.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!state.getGameOver()) {
	            		int aiPlay = 1 + AI.findNextMove(beliefState);
		            	state.move(aiPlay);
		            	Results beliefStates = beliefState.putPiecePlayer(aiPlay - 1);
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState);
		            	board.repaint();
		            	if(!state.getGameOver()) {
		            		aiPlay = 1 + iap.decision(state.getState());
			            	state.move(aiPlay);
			            	beliefStates = beliefState.predict();
			            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
			            	//System.out.println(beliefState.toString());
			            	board.repaint();
		            	}
	            	}
	            }
	         });*/
	      
	      
	      JButton buttonRestart = new JButton("Restart");
	      buttonRestart.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	            	if(!isAI) {
		            	state.restart();
		            	beliefState.restart();
		            	beliefState.add(state.getState().copy());
		            	board.repaint();
	            	}
	            }
	         });
	      
	      JMenuBar jmb = new JMenuBar();
	      JMenu menu = new JMenu("Control");
	      JMenuItem manual  = new JMenuItem("Manual")/*, ai = new JMenuItem("AI")*/;
	      manual.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event)
	      								{isAI = false;} });
	      //ai.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event){}});
	      menu.add(manual);
	      //menu.add(ai);
	      jmb.add(menu);
	      frame.setJMenuBar(jmb);
	      
	      JPanel buttons = new JPanel();
	      buttons.add(button1);
	      buttons.add(button2);
	      buttons.add(button3);
	      buttons.add(button4);
	      buttons.add(button5);
	      buttons.add(button6);
	      buttons.add(button7);
	      //buttons.add(buttonAI);
	      buttons.add(buttonRestart);

	      frame.add(board, BorderLayout.CENTER);
	      frame.add(buttons, BorderLayout.SOUTH);

	      frame.setSize(750, 550);
	      
	      frame.setVisible(true);
	      
	      int nbrSamples = 0;
	      double meanTimeResolution = 0, meanNbrOfTurn = 0;
	      
	      int win = 0, ties = 0;
			for(int i = 0; i < 100 && isAI; i++) {
				state.restart();
            	beliefState.restart();
            	beliefState.add(state.getState().copy());
            	board.repaint();
            	int turnNbr = 0;
            	while(!state.getGameOver() && isAI) {
            		long elapsedTime = System.currentTimeMillis();
            		int aiPlay = 1 + AI.findNextMove(beliefState);
            		if(!beliefState.getMoves().contains(aiPlay - 1)) {
            			System.out.println("Votre IA a joue la colonne " + aiPlay + " alors que cette colonne est pleine.");
            		}
            		elapsedTime = System.currentTimeMillis() - elapsedTime;
	            	nbrSamples++;
	            	meanTimeResolution = ((double)elapsedTime) / nbrSamples + (((double)(nbrSamples - 1)) / nbrSamples) * meanTimeResolution;
            		state.move(aiPlay);
	            	Results beliefStates = beliefState.putPiecePlayer(aiPlay - 1);
	            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
	            	//System.out.println(beliefState);
	            	board.repaint();
	            	if(!state.getGameOver() && state.getRedsTurn() == state.getState().turn())
	            		System.out.println("problem turn");
	            	if(!state.getGameOver()) {
	            		aiPlay = 1 + iap.decision(state.getState());
		            	state.move(aiPlay);
		            	beliefStates = beliefState.predict();
		            	beliefState.setStates(BeliefState.filter(beliefStates, state.getState()));
		            	//System.out.println(beliefState.toString());
		            	board.repaint();
		            	if(!state.getGameOver() && state.getRedsTurn() == state.getState().turn())
		            		System.out.println("problem turn");
	            	}
	            	turnNbr++;
            	}
            	if(state.getRedWins()) {
            		win++;
            		}
            	else{
            		if(!state.getYellowWins()) {
            			ties++;
            			}
            		}
            	meanNbrOfTurn = ((double)turnNbr) / (i + 1) + (((double)(i)) / (i + 1)) * meanNbrOfTurn;
            	System.out.println("Win: " + win + " Ties: " + ties + " Loose: " + (i + 1 - win - ties) + " Average time resolution: " + meanTimeResolution + " Average number of turns: " + meanNbrOfTurn);
            	}
				state.restart();
				beliefState.restart();
				beliefState.add(state.getState().copy());
				board.repaint();
	   }
}
