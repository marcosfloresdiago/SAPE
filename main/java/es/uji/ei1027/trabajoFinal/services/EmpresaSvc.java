package es.uji.ei1027.trabajoFinal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.uji.ei1027.trabajoFinal.dao.EmpresaDao;
import es.uji.ei1027.trabajoFinal.model.Empresa;

public class EmpresaSvc implements EmpresaService{
	
	@Autowired
	EmpresaDao empresaDao;
	

	//Aqui faltan poner los demas servicos que damos, los de borrar y tal
	
	
	@Override
	public List<Empresa>getEmpresas() {

		return empresaDao.getEmpresas();
	}


	@Override
	public Empresa getEmpresa(String cif) {       	
		return empresaDao.getEmpresa(cif);
	}


	@Override
	public void updateEmpresa(Empresa empresa) {
		empresaDao.updateEmpresa(empresa);
		
	}


	@Override
	public void deleteEmpresa(String cif) {
		empresaDao.deleteEmpresa(cif);
		
	}


	@Override
	public void addEmpresa(Empresa empresa) {
		empresaDao.addEmpresa(empresa);
		
	}

}
