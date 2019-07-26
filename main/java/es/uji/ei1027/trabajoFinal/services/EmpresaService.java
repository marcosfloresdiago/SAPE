package es.uji.ei1027.trabajoFinal.services;

import java.util.List;


import es.uji.ei1027.trabajoFinal.model.Empresa;

public interface EmpresaService {
	
	public List <Empresa>getEmpresas();
	public Empresa getEmpresa(String cif);
	public void updateEmpresa(Empresa empresa);
	public void deleteEmpresa(String cif);
	public void addEmpresa(Empresa empresa);

}
