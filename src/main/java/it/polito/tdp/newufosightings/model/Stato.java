package it.polito.tdp.newufosightings.model;

public class Stato {
	
	private String id;
	private String name;
	private Double defcon;
	
	public Stato(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.defcon = 5.0;
		
	}

	public Double getDefcon() {
		return defcon;
	}

	public void aggiungiDefcon(Double defcon) {
		if((this.defcon+defcon)<=5) {
			this.defcon += defcon;
		}
		
		//=> non va bene controllo this.defcon<5 => perchè se è 4.5 entra ma +1 diventa 5.5
		//=> devo controllare che la somma sia <=5, se è cosi somma
	}
	public void riduciDefcon(Double defcon) {
		if((this.defcon-defcon)>=1) {
			this.defcon-=defcon;
		}
	}
	
	public void setDefcon(Double defcon) {
		this.defcon = defcon;
	}

	public String getId() {
		return id;
	}

	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stato other = (Stato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  id + " " + name  ;
	}

	

}
