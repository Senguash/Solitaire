package solitaire;
//import java.util.*;

public class LegalMove {
	String description;
	int src;
	int dest;
	int offset;
	double eval;
	public LegalMove(String descripton, int src, int dest, int offset) {
		this.description = descripton;
		this.src = src;
		this.dest = dest;
		this.offset = offset;
	}
	public void setEval(double eval) {
		this.eval = eval;
	}
	public double getEval() {
		return eval;
	}
	public int getEvalAsInt() {
		return (int) eval;
	}
	public String getDescription() {
		return description;
	}
	public int getSrc() {
		return src;
	}
	public int getDest() {
		return dest;
	}
	public int getOffset() {
		return offset;
	}
}