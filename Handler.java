package solitaire;
import java.util.*;

import javax.lang.model.util.ElementScanner6;

public class Handler {
	Deck deck = new Deck();
	ArrayList<Card>[] table = new ArrayList[13];
	/* 
	table[0-6] = tableau
	table[7-10] = foundation
	table[11-12] = stock
	*/
	//ArrayList<Card> stock = new ArrayList<Card>();
	//ArrayList<Card>[] foundation = new ArrayList[4];
	public Handler() {
		//	deck.print_decklist(); //comment out to not spam terminal with decklist
		int drawCounter = 0;
		for (int i = 0; i < 13; i++) {
			table[i] = new ArrayList<Card>();
		}
		
		for (int i = 0; i < 7; i++) {
			for (int z = 0; z < i+1; z++) {
				table[i].add(deck.decklist.get(drawCounter));
				drawCounter++;
				if (i==z) {
					table[i].get(z).setFaceup(true);
				}
			}
		}
		for (int i = drawCounter; i < 52; i++) { // add rest of deck to stock
			table[11].add(deck.decklist.get(i));
		}

		//deck.print_decklist();
		//table[0].get(0).setFaceup(true);
		//System.out.println(table[0].get(0).getName());
		/*
		for (int i = 0; i < 7; i++) {
			for (int z = 0; z < i+1; z++) {
				System.out.print(table[i].get(z).getName());
				
			}
			System.out.println("");	
			 // add object to that ArrayList
		}*/
	}
	public double eval(LegalMove move) {
		double sum = 0;
		for (int i=0; i < 12; i++) {
			for (int z=0;  z < table[i].size(); z++) {
				sum += f(i,z,move.getSrc(),table[move.getSrc()].size()-move.getOffset()-1,
				move.getDest(),table[move.getDest()].size()-1);
			} 
		}
		return sum;
	}
	private double f(int x,int y,int x1,int y1, int x2, int y2) {
		double result = 0;
		try {
			// source tableau
			if (0 <= x1 && x1 <= 6) {
				// dest tableau
				if (0 <= x2 && x2 <= 6) {
					if (y1 == 0) {
						result += 2;
					} else
					if (table[x1].size() == 1 && table[x].get(y).getValue() == 13 && y != 0) {
						result += 1000;
					} else
					if (table[x1].get(y1-1).getFaceup() == false) {
						result += 10;
					} else {
						result -= 5;
					}
				}	
				// dest foundation
				if (7 <= x2 && x2 <= 10) {
					result += 100;
				}
			} else
			// source foundation
			if (7 <= x1 && x1 <= 10) {
				result += -5;
			} else
			// cycle stock
			if (x1 == 11) {
				result += 1;
			} else
			// source stock
			if (x1 == 12){
				// dest tableau
				if (0 <= x2 && x2 <= 6) {
					result += 5;
				} else
				if (7 <= x2 && x2 <= 10) {
					result += 15;
				}
			}
		}
		catch(Exception e) {
			return 0;
		}
		return result;
	}
	public void evalMoves(ArrayList<LegalMove> moves) {
		for (int i=0; i < moves.size(); i++ ) {
			moves.get(i).setEval(eval(moves.get(i)));
		}
	}
	public ArrayList<LegalMove> calcMoves() {
		ArrayList<LegalMove> moves = new ArrayList<LegalMove>();
		faceControl();
		tableauToTableau(moves);
		toFoundationMove(moves);
		fromStock(moves);
		flipStock(moves);
		evalMoves(moves);
		//System.out.println(moves.size());
		return moves;
	}
	public void faceControl() {
		for (int i=0; i<7; i++) {
			if (table[i].size() > 0) {
				if (table[i].get(table[i].size()-1).getFaceup() == false) {
					table[i].get(table[i].size()-1).setFaceup(true);
				}
			}
		}
		if (table[12].size() > 0) {
			table[12].get(table[12].size()-1).setFaceup(true);
		}
	}

	public void printGameState() {
		// Stock
		if (table[11].isEmpty() == false) {	
			System.out.print(table[11].get(table[11].size()-1).getName());
		} else {
			System.out.print("      Empty      ");
		}
		if (table[12].isEmpty() == false) {
			System.out.print(table[12].get(table[12].size()-1).getName());
		} else { 
			System.out.print("      Empty      "); 
		}
		System.out.print("                 ");
		// Foundation
		for (int i = 0; i < 4; i++) {
			if (table[7+i].size() > 0) {
				System.out.print(table[7+i].get(table[7+i].size()-1).getName());
			} else {
				System.out.print("      Empty      ");
			}
		}
		System.out.println("");
		System.out.println("");
		// table
		int maxSize = 0;
		for (int i = 0; i < 7; i++) {
			if (maxSize < table[i].size()) {
				maxSize = table[i].size();
			}
		}
		for (int i = 0; i < maxSize; i++) {
			for (int z = 0; z < 7; z++) {
				if (i < table[z].size()) {
					System.out.print(table[z].get(i).getName());
				} else {
					System.out.print("                 ");
				}
			}
			System.out.println("");	
		}	
	}
	public void executeMove(LegalMove choice) {
		if (choice.getSrc() == 11 && table[11].size() == 0) {
			for (int i=table[12].size()-1; i >= 0; i--) {
				table[11].add(table[12].get(table[12].size()-1));
				table[11].get(table[11].size()-1).setFaceup(false); // comment out to remember stockpile
				table[12].remove(table[12].size()-1);
			}
		} else {
		for (int i = 0; i <= choice.getOffset(); i++) {
		table[choice.getDest()].add(table[choice.getSrc()].get(table[choice.getSrc()].size()-1-(choice.getOffset()-i)));
		//table[choice.getDest()].get(table[choice.getDest()].size()-1).setFaceup(true);
		table[choice.getSrc()].remove(table[choice.getSrc()].size()-1-(choice.getOffset()-i));
		}
		}
		
	}
	private void tableauToTableau(ArrayList<LegalMove> moves) {
		for (int i = 0; i < 7; i++) { // i and z is card to be moved
			for (int z = 0; z < table[i].size(); z++) {
				
				for (int x = 0; x < 7; x++) { // x and y is target
					if (table[i].get(z).getFaceup() 
					&& (table[i].get(z).getValue() == 13) 
					&& (table[x].size() == 0) 
					&& (z != 0)) {
						moves.add(new LegalMove(table[i].get(z).getName()+" to empty", i, x, table[i].size()-(z+1)));	
					}
					for (int y = 0; y < table[x].size(); y++) {
						if (table[i].get(z).getFaceup() && table[x].get(y).getFaceup()) {
							if ( (table[i].get(z).getValue()+1 == table[x].get(y).getValue())
							&&(table[i].get(z).getSuit()%2 != table[x].get(y).getSuit()%2) 
							&&(table[x].get(y).getId() == table[x].get(table[x].size()-1).getId())) {
								moves.add(new LegalMove(table[i].get(z).getName()+" to "+table[x].get(y).getName(), i, x, table[i].size()-(z+1)));
							}
						}
						
					}
				}
			}
		}
	}
	private void toFoundationMove(ArrayList<LegalMove> moves) {
		for (int x = 0; x < 7; x++) {
			if (table[x].size() > 0) {
				for (int i = 0; i < 4; i++) {
					if (table[7+i].size() == 0) {
						if (table[x].get(table[x].size()-1).getValue() == 1) {
							moves.add(new LegalMove(table[x].get(table[x].size()-1).getName()+" to foundation", x, 7+i, 0));
							break;
							}
					} else {
						if (table[7+i].size()+1 == table[x].get(table[x].size()-1).getValue()
						&&((table[7+i].get(table[7+i].size()-1).getSuit() == table[x].get(table[x].size()-1).getSuit()))) {
							moves.add(new LegalMove(table[x].get(table[x].size()-1).getName()+" to foundation", x, 7+i, 0));
						}
					}
				}
			}
		}
	}
	private void fromStock(ArrayList<LegalMove> moves) {
		// from stock to tableau
		for (int x = 0; x < 7; x++) {
			if (table[12].size() > 0) {
			if ((table[x].size() == 0) && (table[12].get(table[12].size()-1).getValue() == 13) ) {
				moves.add(new LegalMove(table[12].get(table[12].size()-1).getName()+" to empty from stock", 12, x, 0));	
			}
			else if ((table[x].size() > 0)) {
			if ( (table[12].get(table[12].size()-1).getValue()+1 == table[x].get(table[x].size()-1).getValue())
				&&(table[12].get(table[12].size()-1).getSuit()%2 != table[x].get(table[x].size()-1).getSuit()%2) ) {
				moves.add(new LegalMove(table[12].get(table[12].size()-1).getName()+" to "+table[x].get(table[x].size()-1).getName() + " from stock", 12, x, 0));
			}
		}	
		}
		}
		// from stock to foundation
		for (int x = 7; x < 11; x++) { 
			if ((table[12].size() > 0) && (table[x].size() > 0)) {
			if ( (table[12].get(table[12].size()-1).getValue()-1 == table[x].get(table[x].size()-1).getValue())
				&&(table[12].get(table[12].size()-1).getSuit() == table[x].get(table[x].size()-1).getSuit()) ) {
				moves.add(new LegalMove(table[12].get(table[12].size()-1).getName()+" to "+table[x].get(table[x].size()-1).getName() + " from stock", 12, x, 0));
				}
			} else {
				if (table[12].size() > 0) {
				if (table[12].get(table[12].size()-1).getValue() == 1) {
					moves.add(new LegalMove(table[12].get(table[12].size()-1).getName()+" to foundation from stock", 12, x, 0));
					break;
				}
			}
			}
		}	
	}

	public void flipStock(ArrayList<LegalMove> moves) {
			moves.add(new LegalMove("Cycle stock", 11, 12, 0));
		}
	public boolean checkIfWin() {
		if ((table[7].size() == 13) && (table[8].size() == 13)
		&& (table[9].size() == 13) && (table[10].size() == 13)) {
			return true;
		}
		return false;
	}
}