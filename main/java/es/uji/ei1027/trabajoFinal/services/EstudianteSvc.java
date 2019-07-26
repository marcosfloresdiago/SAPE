package es.uji.ei1027.trabajoFinal.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import es.uji.ei1027.trabajoFinal.dao.EstudianteDao;
import es.uji.ei1027.trabajoFinal.model.Estudiante;

public class EstudianteSvc implements EstudianteService{
	
	@Autowired
	EstudianteDao estudianteDao;
	
	

	@Override
	public List<Estudiante>getEstudiantesPorDNI() {
		List<Estudiante> classProva = 
                	estudianteDao.getEstudiantes();
		
		return classProva;
	}


}
