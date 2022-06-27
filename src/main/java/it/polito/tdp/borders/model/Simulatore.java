package it.polito.tdp.borders.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	private int numeroMigranti = 1000;
	private Graph<Country, DefaultEdge> grafo;
	private PriorityQueue<Event> queue;
	private int numeroPassi = 0;
	
	private void creaEventi(Country country) {
		List<Country> statiVicini = Graphs.neighborListOf(this.grafo, country);
		int migrantiPerStato = (int) Math.floor(((double) this.numeroMigranti/2)/statiVicini.size());
		country.editMigrantiPresenti(-(int) Math.ceil((double)this.numeroMigranti/2));
		for(Country c: statiVicini) {
			Event evento = new Event(migrantiPerStato, 2, c);
			this.queue.add(evento);
			c.editMigrantiPresenti(migrantiPerStato);
		}
	}
	
	public void init(Graph<Country, DefaultEdge> grafo, Country partenza) {
		this.grafo = grafo;
		this.queue = new PriorityQueue<>();
		this.numeroPassi = 0;
		for(Country c: this.grafo.vertexSet()) {
			c.editMigrantiPresenti(-c.getMigrantiPresenti());
		}
		partenza.editMigrantiPresenti(this.numeroMigranti);
		this.creaEventi(partenza);
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}

	private void processEvent(Event e) {
		List<Country> statiVicini = Graphs.neighborListOf(this.grafo, e.getStato());
		if(statiVicini.size()<=(int) Math.floor((double) e.getNumeroPersone()/2)) {
			int migrantiPerStato = (int) Math.floor(((double) e.getNumeroPersone()/2)/statiVicini.size());
			e.getStato().editMigrantiPresenti(-(int) Math.ceil((double)e.getNumeroPersone()/2));
			for(Country c: statiVicini) {
				Event evento = new Event(migrantiPerStato, e.getTempo()+1, c);
				this.queue.add(evento);
				c.editMigrantiPresenti(migrantiPerStato);
			}
			if(e.getTempo()>this.numeroPassi) {
				this.numeroPassi = e.getTempo();
			}
		}
	}
	
	public int getNumeroPassi() {
		return this.numeroPassi;
	}

	public List<Country> getElencoStatiConStanzianti() {
		List<Country> tempList = new LinkedList<>();
		for(Country c: this.grafo.vertexSet()) {
			if(c.getMigrantiPresenti()>0) {
				tempList.add(c);
			}
		}
		Collections.sort(tempList);
		Collections.reverse(tempList);
		return tempList;
	}
	
	
}
