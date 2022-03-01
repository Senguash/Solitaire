package solitaire;

public class Card {
	int id;
	int value;
	int suit;
	String name;
	boolean faceup;

	public Card() {
		this.faceup = false;
	}

	public int getId() {
		if (faceup)
		return id;
		else
		return -1;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValue() {
		if (faceup)
		return value;
		else
		return -1;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getSuit() {
		if (faceup)
		return suit;
		else
		return -1;
	}
	public void setSuit(int suit) {
		this.suit = suit;
	}
	public String getName() {
		if (faceup)
		return name;
		else
		return "    Face down    ";
		//return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getFaceup() {
		return faceup;
	}
	public void setFaceup(boolean faceup) {
		this.faceup = faceup;
	}
}
