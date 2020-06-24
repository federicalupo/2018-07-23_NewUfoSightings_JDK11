package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum Tipo{
		AVVISTAMENTO,
		CESSATA_ALLERTA
	}
	
	private Tipo tipo;
	private Stato s;
	private LocalDateTime data;
	private Double incremento;
	
	
	public Evento(Tipo tipo, Stato s, LocalDateTime data) {
		super();
		this.tipo = tipo;
		this.s = s;
		this.data = data;
	}


	public Evento(Tipo tipo, Stato s, LocalDateTime data, Double incremento) {
		super();
		this.tipo = tipo;
		this.s = s;
		this.data = data;
		this.incremento = incremento;
	}
	
	public Tipo getTipo() {
		return tipo;
	}


	public Stato getS() {
		return s;
	}


	public LocalDateTime getData() {
		return data;
	}


	public Double getIncremento() {
		return incremento;
	}


	@Override
	public String toString() {
		return  tipo + " " + s + " " + data ;
	}


	@Override
	public int compareTo(Evento o) {
		return this.data.compareTo(o.getData());
	}
	
	

}
