package es.uji.ei1027.trabajoFinal.model;

import java.sql.Date;

public class OfertaProyecto {
	
	public int idOferta;
	public String tarea;
	public String objetivo;
	public String estado;
	public Date fechaAlta;
	public Date fechaUltimoCambio;
	public String itinerario;
	public int idEstancia;
	public boolean visible;
	
	
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getIdOferta() {
		return idOferta;
	}
	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}
	public String getTarea() {
		return tarea;
	}
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}
	public void setFechaUltimoCambio(Date fechaUltimoCambio) {
		this.fechaUltimoCambio = fechaUltimoCambio;
	}
	public String getItinerario() {
		return itinerario;
	}
	public void setItinerario(String itinerario) {
		this.itinerario = itinerario;
	}
	public int getIdEstancia() {
		return idEstancia;
	}
	public void setIdEstancia(int idEstancia) {
		this.idEstancia = idEstancia;
	}
	public boolean compareTo(OfertaProyecto oferta){
		return this.idOferta == oferta.getIdOferta();
	}
	
	
	

}
