package solitaire;
import java.util.*;

public class Deck {
	//Card cardlist[] = new Card[52];
	public ArrayList<Card> decklist = new ArrayList<Card>();

	public Deck() {		
		for (int i=0; i < 52; i++) {
			decklist.add(new Card());
			decklist.get(i).setFaceup(true);
			decklist.get(i).setId(i);
			decklist.get(i).setValue((i)%13+1);
			decklist.get(i).setSuit((i/13));
			String newName = "";
			switch(decklist.get(i).getValue()) {
				case 1:
				newName = newName.concat("  Ace");
				break;
				case 10:
				newName = newName.concat("   ");
				newName = newName.concat(String.valueOf(decklist.get(i).getValue()));
				break;
				case 11:
				newName = newName.concat(" Jack");
				break;
				case 12:
				newName = newName.concat("Queen");
				break;
				case 13:
				newName = newName.concat(" King");
				break;
				default:
				newName = newName.concat("    ");
				newName = newName.concat(String.valueOf(decklist.get(i).getValue()));
				break;
			}
			
			switch(decklist.get(i).getSuit()) {
				case 0:
				newName = newName.concat(" of hearts  ");
				break;
				case 1:
				newName = newName.concat(" of spades  ");
				break;
				case 2:
				newName = newName.concat(" of diamonds");
				break;
				case 3:
				newName = newName.concat(" of clubs   ");
				break;
				default:
				break;
			}
			decklist.get(i).setName(newName);
			decklist.get(i).setFaceup(false);
		}
	Collections.shuffle(decklist);
	}
	public void print_decklist() {
		for (int i=0; i < 52; i++) {
			System.out.print("ID: " + String.valueOf(decklist.get(i).getId()));
			System.out.print(" Value: " + String.valueOf(decklist.get(i).getValue()));
			System.out.print(" Suit: " + String.valueOf(decklist.get(i).getSuit()));
			System.out.println(" Name: " + decklist.get(i).getName());
			System.out.println(decklist.get(i).getName());
		}	
	}
}