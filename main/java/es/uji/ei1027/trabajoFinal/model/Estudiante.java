package es.uji.ei1027.trabajoFinal.model;

import java.util.Date;

public class Estudiante {
	
	
	public String dni;
	public String nombre;
	public int orden;
	public String itinerario;
	public int numasignaturaspendientes4t;
	public int semestreinicioestancia;
	public String estadoPreferencia;
	public Date fechaUltimoCambioPreferencia;
	
	
	public Date getFechaUltimoCambioPreferencia() {
		return fechaUltimoCambioPreferencia;
	}
	public void setFechaUltimoCambioPreferencia(Date fechaUltimoCambioPreferencia) {
		this.fechaUltimoCambioPreferencia = fechaUltimoCambioPreferencia;
	}
	public String getEstadoPreferencia() {
		return estadoPreferencia;
	}
	public void setEstadoPreferencia(String estadoPreferencia) {
		this.estadoPreferencia = estadoPreferencia;
	}
	
	public String getDni() {
		return dni;
	}
	public String getNombre() {
		return nombre;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public String getItinerario() {
		return itinerario;
	}
	public int getNumAsigPend() {
		return numasignaturaspendientes4t;
	}
	public int getSemestreInicio() {
		return semestreinicioestancia;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setItinerario(String itinerario) {
		this.itinerario = itinerario;
	}
	public void setNumAsigPend(int numAsigPend) {
		this.numasignaturaspendientes4t = numAsigPend;
	}
	public void setSemestreInicio(int semestreInicio) {
		this.semestreinicioestancia = semestreInicio;
	}
	
	

}
