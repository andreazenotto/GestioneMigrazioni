package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	private int numeroPersone;
	private int tempo;
	private Country stato;
	
	public Event(int numeroPersone, int tempo, Country stato) {
		super();
		this.numeroPersone = numeroPersone;
		this.tempo = tempo;
		this.stato = stato;
	}

	public int getNumeroPersone() {
		return numeroPersone;
	}



	public int getTempo() {
		return tempo;
	}



	public Country getStato() {
		return stato;
	}



	@Override
	public int compareTo(Event o) {
		return this.tempo-o.getTempo();
	}
	
}
