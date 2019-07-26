package es.uji.ei1027.trabajoFinal.model;

import java.sql.Date;

public class PeticionRevision {
	
	public int idPeticion;
	public int idOfertaProyecto;
	public Date fecha;
	public String textoPeticion;
	
	
	public int getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(int idPeticion) {
		this.idPeticion = idPeticion;
	}
	public int getIdOfertaProyecto() {
		return idOfertaProyecto;
	}
	public void setIdOfertaProyecto(int idOfertaProyecto) {
		this.idOfertaProyecto = idOfertaProyecto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTextoPeticion() {
		return textoPeticion;
	}
	public void setTextoPeticion(String textoPeticion) {
		this.textoPeticion = textoPeticion;
	}
	
	

}
