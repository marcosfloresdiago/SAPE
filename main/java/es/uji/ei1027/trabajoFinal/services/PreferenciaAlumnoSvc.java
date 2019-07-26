package es.uji.ei1027.trabajoFinal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.uji.ei1027.trabajoFinal.dao.PreferenciaAlumnoDao;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;

public class PreferenciaAlumnoSvc implements PreferenciaAlumnoService{
	
	@Autowired
	PreferenciaAlumnoDao preferenciaAlumnoDao;
	

	//Aqui faltan poner los demas servicos que damos, los de borrar y tal
	
	
	@Override
	public List<PreferenciaAlumno>getPreferenciaAlumnos() {

		return preferenciaAlumnoDao.getPreferenciaAlumnos();
	}


	@Override
	public PreferenciaAlumno getPreferenciaAlumno(String dniEstudiante) {       	
		return (PreferenciaAlumno) preferenciaAlumnoDao.getPreferenciasAlumno(dniEstudiante);
	}


	@Override
	public void updatePreferenciaAlumno(PreferenciaAlumno preferenciaAlumno) {
		preferenciaAlumnoDao.updatePreferenciaAlumno(preferenciaAlumno);
		
	}


	@Override
	public void deletePreferenciaAlumno(String dniEstudiante) {
		preferenciaAlumnoDao.deletePreferenciaAlumno(dniEstudiante);
		
	}


	@Override
	public void addPreferenciaAlumno(PreferenciaAlumno preferenciaAlumno) {
		preferenciaAlumnoDao.addPreferenciaAlumno(preferenciaAlumno);
		
	}

}