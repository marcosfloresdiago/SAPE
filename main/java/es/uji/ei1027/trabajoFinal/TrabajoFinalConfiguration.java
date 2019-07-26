package es.uji.ei1027.trabajoFinal;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.uji.ei1027.trabajoFinal.dao.AsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.EstadosOfertaDao;
import es.uji.ei1027.trabajoFinal.dao.EstanciaDao;
import es.uji.ei1027.trabajoFinal.dao.ProfesorTutorDao;
import es.uji.ei1027.trabajoFinal.dao.UserDao;
import es.uji.ei1027.trabajoFinal.dao.UserProvider;
import es.uji.ei1027.trabajoFinal.model.Empresa;
import es.uji.ei1027.trabajoFinal.model.EstadoOferta;
import es.uji.ei1027.trabajoFinal.model.Estudiante;
import es.uji.ei1027.trabajoFinal.model.Itinerario;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;
import es.uji.ei1027.trabajoFinal.services.EmpresaService;
import es.uji.ei1027.trabajoFinal.services.EmpresaSvc;
import es.uji.ei1027.trabajoFinal.services.EstudianteService;
import es.uji.ei1027.trabajoFinal.services.EstudianteSvc;
import es.uji.ei1027.trabajoFinal.services.OfertaProyectoSvc;
import es.uji.ei1027.trabajoFinal.services.PreferenciaAlumnoService;
import es.uji.ei1027.trabajoFinal.services.PreferenciaAlumnoSvc;



@Configuration
public class TrabajoFinalConfiguration {
	
	@Bean
	public UserDao userDao(){
		
		return new UserProvider();
	}
	
	@Bean
	public EstanciaDao estanciaDao(){
		
		return new EstanciaDao();
	}
	
	@Bean
	public EstadosOfertaDao estadosOfertaDao(){
		return new EstadosOfertaDao();
	}
	
	@Bean
	public AsignacionDao asignacionDao(){
		
		return new AsignacionDao();
	}
	
	@Bean
	public ProfesorTutorDao profesorTutorDao(){
		
		return new ProfesorTutorDao();
	}
	
	
	
	
	@Bean
	public Estudiante estudiante(){
		
		return new Estudiante();
	}
	
	@Bean
	public Itinerario itineraio(){
		
		return new Itinerario();
	}
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource(){
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	public EstudianteService estudianteService(){
		
		return new EstudianteSvc();
		
	}
	
	//----------------------------------------
	
	@Bean
	public Empresa empresa(){
		
		return new Empresa();
	}

	@Bean
	public EmpresaService empresaService(){
		
		return new EmpresaSvc();
		
	}
	
	@Bean
	public EstadoOferta estadoOferta(){
		return new EstadoOferta();
	}
	
	
	//----------------------------------------
	
	@Bean
	public OfertaProyecto ofertaproyecto(){
		
		return new OfertaProyecto();
	}
	
	@Bean
	public OfertaProyectoSvc ofertaService(){
		
		return new OfertaProyectoSvc();
		
	}
	
	@Bean
	public PreferenciaAlumno preferenciaAlumno(){
		
		return new PreferenciaAlumno();
	}

	@Bean
	public PreferenciaAlumnoService preferenciaAlumnoService(){
		
		return new PreferenciaAlumnoSvc();
		
	}
	
}
