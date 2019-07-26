package es.uji.ei1027.trabajoFinal.model;

import java.sql.Date;

public class Asignacion {
	
	public int idAsignacion;
	public Date fechaPropuesta;
	public Date fechaAceptacion;
	public Date fechaRechazo;
	public Date fechaTraspasoIGLU;
	public String estado;
	public int idOfertaProyecto;
	public String dniProfesorAsignado;
	public String dniEstudianteAsignado;
	
	
	//Para poder hacer relaciones sin consultar en la base
	//GUAPO QUE ASI TE CARGAS LA CONSISTENCIA DE LA BASE DE DATOS, SI QUIERES HACER ESO, HAZLO BIEN
	private String nombreEstudiante;
	
	public String getNombreEstudiante() {
		return nombreEstudiante;
	}
	public void setNombreEstudiante(String nombreEstudiante) {
		this.nombreEstudiante = nombreEstudiante;
	}
	
	
	
	public int getIdAsignacion() {
		return idAsignacion;
	}
	public void setIdAsignacion(int idAsignacion) {
		this.idAsignacion = idAsignacion;
	}
	public Date getFechaPropuesta() {
		return fechaPropuesta;
	}
	public void setFechaPropuesta(Date fechaPropuesta) {
		this.fechaPropuesta = fechaPropuesta;
	}
	public Date getFechaAceptacion() {
		return fechaAceptacion;
	}
	public void setFechaAceptacion(Date fechaAceptacon) {
		this.fechaAceptacion = fechaAceptacon;
	}
	public Date getFechaRechazo() {
		return fechaRechazo;
	}
	public void setFechaRechazo(Date fechaRechazo) {
		this.fechaRechazo = fechaRechazo;
	}
	public Date getFechaTraspasoIGLU() {
		return fechaTraspasoIGLU;
	}
	public void setFechaTraspasoIGLU(Date fechaTraspasoIGLU) {
		this.fechaTraspasoIGLU = fechaTraspasoIGLU;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getIdOfertaProyecto() {
		return idOfertaProyecto;
	}
	public void setIdOfertaProyecto(int idOfertaProyecto) {
		this.idOfertaProyecto = idOfertaProyecto;
	}
	public String getDniProfesorAsignado() {
		return dniProfesorAsignado;
	}
	public void setDniProfesorAsignado(String dniProfesorAsignado) {
		this.dniProfesorAsignado = dniProfesorAsignado;
	}
	public String getDniEstudianteAsignado() {
		return dniEstudianteAsignado;
	}
	public void setDniEstudianteAsignado(String dniEstudianteAsignado) {
		this.dniEstudianteAsignado = dniEstudianteAsignado;
	}



}
