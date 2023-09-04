package hadron.heuristic;

import hadron.board.Board;
import hadron.research.Node;

import java.util.ArrayList;
import java.util.Random;

public class GenericHeuristic implements Heuristic {
	private static final long serialVersionUID = 5783084660998719046L;

	/**
	 * Costruttore euristica
	 */
	public GenericHeuristic() {
	}


	@Override
	public double evaluate(Board b, int col) {
		// Se la hadron.board da una vittoria vuol dire che il giocatore attuale è rimasto senza mosse
		// col è il vincitore
		if(b.isFinal())
			return -1000000D;

		int t = getNumPedine(b); 	// passo
		double lambda = 0.075;		// velocità di decrescita del peso relativo alla distribuzione esponenziale
		double alpha = 70;			// peso distribuzione esponenziale all'inizio

		double pesoRandom = alpha * Math.exp(-lambda * t);	// peso distribuzione esponenziale
		double pesoPariDispari = alpha - pesoRandom;		// il peso che ad ogni passo perde l'euristica random viene assegnato all'euristica pari-dispari
		double pesoMobilita = 30;							// peso euristica mobilita

		double value1 = euristicaMobilita(b,col)*pesoMobilita;
		double value2 = euristicaRandom()*pesoRandom;
		double value3 = euristicaPariDispari(b,col)*pesoPariDispari;

		//restituiamo una media pesata fra le varie euristiche
		return (value1 + value2 + value3) / (pesoRandom + pesoMobilita + pesoPariDispari);
	}

	private double euristicaMobilita(Board b, int col){
		ArrayList<Node> mosseDisponibili = b.getSons((byte) col);
		return mosseDisponibili.size(); // si preferiscono le mosse che portano a più mosse disponibili (mobilita positiva)
	}

	private double euristicaRandom(){
		double rand = new Random().nextDouble();
		return rand * 2 - 1;	// si restituisce semplicemente un valore random
	}

	private double euristicaPariDispari(Board b, int col){
		ArrayList<Node> mosseDisponibili = b.getSons((byte) col);
		double dispari = 0;
		double pari = 0;

		for(Node mossa : mosseDisponibili) {
			if(mossa.getBoard().isFinal())
				return 1000000D;
			if(mossa.getBoard().getSons((byte)(1-col)).size() % 2 == 0)
				pari++;
			else
				dispari++;
		}

		return (pari / (dispari + pari)) * 2 - 1;	// si preferisce una board che porta a più mosse disponibili pari dopo che l'avversario muove
	}

	private int getNumPedine(Board board) {
		int numPedine = 0;
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
				if(board.getCol(i, j) != (byte) -1)
					numPedine++;
		return numPedine;
	}


	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof GenericHeuristic) ) return false;
		GenericHeuristic other = (GenericHeuristic) obj;
		return other.toString().equals(this.toString());
	}

}
