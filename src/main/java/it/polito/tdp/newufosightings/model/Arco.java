package it.polito.tdp.newufosightings.model;

public class Arco {

	private Stato stato1;
	private Stato stato2;
	private Integer peso;
	
	public Arco(Stato stato1, Stato stato2, int peso) {
		this.stato1 = stato1;
		this.stato2 = stato2;
		this.peso = peso;
	}

	public Stato getStato1() {
		return stato1;
	}

	public Stato getStato2() {
		return stato2;
	}

	public Integer getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return  stato1 + " " + stato2 + " " + peso ;
	}

	
}
