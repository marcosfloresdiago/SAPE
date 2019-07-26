package es.uji.ei1027.trabajoFinal.model;

public class Estancia {
	
	public int idEstancia;
	public String cifEmpresa; 
	public String personaContacto; 
	public String emailPersonaContacto; 
	public String descripcion;
	
	public int getIdEstancia() {
		return idEstancia;
	}
	public void setIdEstancia(int idEstancia) {
		this.idEstancia = idEstancia;
	}
	public String getCifEmpresa() {
		return cifEmpresa;
	}
	public void setCifEmpresa(String cifEmpresa) {
		this.cifEmpresa = cifEmpresa;
	}
	public String getPersonaContacto() {
		return personaContacto;
	}
	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}
	public String getEmailPersonaContacto() {
		return emailPersonaContacto;
	}
	public void setEmailPersonaContacto(String emailPersonaContacto) {
		this.emailPersonaContacto = emailPersonaContacto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	

}
