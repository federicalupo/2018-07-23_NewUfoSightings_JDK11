package it.polito.tdp.newufosightings.model;

import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.newufosightings.model.Evento.Tipo;

public class Simulatore {
	
	//mondo
	private Graph<Stato, DefaultWeightedEdge> grafo;
	private Map<String, Stato> idMap;
	
	//input
	private  Long  t1; //in giorni < 365 controllo
	private Double alfa; //0-100
	
	//coda
	private PriorityQueue<Evento> coda;
	
	//output => uso il metodo che è nel model che stampa idMap.values
	//=> oppure creo mappa<Stato, Integer> stato-defcon
	
	public void init(Graph<Stato, DefaultWeightedEdge> grafo,   Map<String, Stato> idMap, PriorityQueue<Evento> coda, Long t1, Double alfa) {
		this.grafo = grafo;
		this.coda = coda;
		this.t1 = t1;
		this.alfa = alfa/100; 
		this.idMap = idMap;
		
		//devo settare a 5 i livelli
		for(Stato s : idMap.values()) {
			s.setDefcon(5.0);
		}
		
	}
	
	public void run() {
		while(!coda.isEmpty()) {
			processEvent(coda.poll());
		}
	}

	private void processEvent(Evento e) {
		switch(e.getTipo()) {
		case AVVISTAMENTO:
			e.getS().riduciDefcon(1.0);
			
			if(Math.random()<=alfa) {
				for(Stato s : Graphs.neighborListOf(this.grafo, e.getS())) {
					s.riduciDefcon(0.5); //ma se la riduzione non va a compimento, devo incrementare lo stesso????
					Evento evento = new Evento(Tipo.CESSATA_ALLERTA, e.getS(), e.getData().plusDays(t1), 0.5);
					coda.add(evento);
				}
				
			}
			
			Evento evento = new Evento(Tipo.CESSATA_ALLERTA, e.getS(), e.getData().plusDays(t1), 1.0);
			coda.add(evento);
			
			
			break;
		case CESSATA_ALLERTA:
			e.getS().aggiungiDefcon(e.getIncremento());
			
			break;
		}
		
	}

}
