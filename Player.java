package solitaire;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Player {
	// create an object of Scanner
	Scanner input = new Scanner(System.in);

// take input from the user
	
	public Player() {
		boolean running = true;
		String s;
		int wins = 0;
		//while (running) {
		for (int i=0; i< 1000; i++) {
			Handler handler = new Handler();
			ArrayList<LegalMove> possibleMoves = new ArrayList<LegalMove>();
			//handler.printGameState();
			//possibleMoves = handler.calcMoves();
			//possibleMoves.sort((m1,m2) -> m2.getEvalAsInt()-m1.getEvalAsInt());
			//printMoves(possibleMoves);
			/*
			while (!input.hasNextInt() && running) {
				s = input.next();
				if (s.contains("q")) {
					running = false;
				}
				
			}
			int number = (input.nextInt() - 1);
			if (number >= 0 && number < possibleMoves.size()) {
				handler.executeMove(possibleMoves.get(number));
			}else
			if (number == -1) {*/
				for (int x=0; x < 300; x++) {
					//handler.printGameState();
					possibleMoves = handler.calcMoves();
					possibleMoves.sort((m1,m2) -> m2.getEvalAsInt()-m1.getEvalAsInt());
					handler.executeMove(possibleMoves.get(0));
					try {
					//TimeUnit.MILLISECONDS.sleep(10);
					} catch(Exception e) {

					}
				if (handler.checkIfWin()) {
					wins += 1;
					break;
				}
				}
			//}			
			//handler.executeMove(possibleMoves.get(0));
			//handler.faceControl();
		}
		System.out.println(wins);
		
	}

	public void printMoves(ArrayList<LegalMove> possibleMoves) {
		for (int i = 0; i < possibleMoves.size(); i++) {
			System.out.println(String.valueOf(i+1)+": "+possibleMoves.get(i).getDescription()+"  Value: "+possibleMoves.get(i).getEval());
		}
	}
}
