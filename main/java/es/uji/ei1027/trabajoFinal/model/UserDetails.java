package es.uji.ei1027.trabajoFinal.model;

public class UserDetails {
	
	String username;
	String password;
	String dni_cif; //ESTO ES UNA CHAPUZA, ME CAGOEN TU FIDEL
	String tipo; //HACER ENUMERACION CON LOS TIPOS DE USUARIOS POSIBLES
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDni_cif() {
		return dni_cif;
	}
	public void setDni_cif(String dni_cif) {
		this.dni_cif = dni_cif;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	

}
