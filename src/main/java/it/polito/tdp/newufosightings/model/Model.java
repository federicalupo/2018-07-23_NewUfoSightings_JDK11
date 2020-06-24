package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private NewUfoSightingsDAO dao;
	private Graph<Stato, DefaultWeightedEdge> grafo;
	private Map<String, Stato> idMap;
	
	public Model() {
		dao = new NewUfoSightingsDAO();
	}

	public List<String> forme(Integer anno){
		return dao.forme(anno);
	}
	
	public void creaGrafo(Integer anno, String forma) {
		this.grafo = new SimpleWeightedGraph<Stato, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap));
		
		for(Arco a : dao.archi(idMap)) {
			Graphs.addEdge(this.grafo, a.getStato1(), a.getStato2(), a.getPeso());
		}
		
		for(Arco a : dao.archiPeso(idMap, anno, forma)) {
			if(this.grafo.containsEdge(a.getStato1(), a.getStato2())) {
				this.grafo.setEdgeWeight(a.getStato1(), a.getStato2(), a.getPeso());
			}
		}
	}
	
	public Integer nVertici() {
		return this.grafo.vertexSet().size();
	}
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Stato> stati(){ //ordine alfabetico
		List<Stato> stati = new ArrayList<>(this.idMap.values());
		return stati;
	}
	
	public Integer sommaPesi(Stato s) {
		Integer pesoTot = 0;
		
		for(Stato stato : Graphs.neighborListOf(this.grafo, s)) {
			pesoTot+= (int)this.grafo.getEdgeWeight(this.grafo.getEdge(s, stato));
		}
		return pesoTot;
	}
	
	public void simula(Long t1, Double alfa, Integer anno, String forma) {
		Simulatore simulatore = new Simulatore();
		simulatore.init(grafo, idMap, dao.coda(anno, forma, idMap),  t1, alfa);
		simulatore.run();
	}
	
	
}