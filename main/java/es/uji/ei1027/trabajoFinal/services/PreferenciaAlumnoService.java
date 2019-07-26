package es.uji.ei1027.trabajoFinal.services;

import java.util.List;


import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;

public interface PreferenciaAlumnoService {
	
	public List <PreferenciaAlumno>getPreferenciaAlumnos();
	public PreferenciaAlumno getPreferenciaAlumno(String dniEstudiante);
	public void updatePreferenciaAlumno(PreferenciaAlumno preferenciaAlumno);
	public void deletePreferenciaAlumno(String dniEstudiante);
	public void addPreferenciaAlumno(PreferenciaAlumno preferenciaAlumno);

}