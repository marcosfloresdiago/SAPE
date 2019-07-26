package es.uji.ei1027.trabajoFinal.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.uji.ei1027.trabajoFinal.dao.AsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.EmpresaDao;
import es.uji.ei1027.trabajoFinal.dao.EstadosAsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.EstadosOfertaDao;
import es.uji.ei1027.trabajoFinal.dao.EstanciaDao;
import es.uji.ei1027.trabajoFinal.dao.EstudianteDao;
import es.uji.ei1027.trabajoFinal.dao.ItinerarioDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaNominativaDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.dao.PeticionDeRevisionDao;
import es.uji.ei1027.trabajoFinal.dao.PreferenciaAlumnoDao;
import es.uji.ei1027.trabajoFinal.dao.ProfesorTutorDao;
import es.uji.ei1027.trabajoFinal.model.Asignacion;
import es.uji.ei1027.trabajoFinal.model.Empresa;
import es.uji.ei1027.trabajoFinal.model.EstadoOferta;
import es.uji.ei1027.trabajoFinal.model.EstadosAsignacion;
import es.uji.ei1027.trabajoFinal.model.Estancia;
import es.uji.ei1027.trabajoFinal.model.Estudiante;
import es.uji.ei1027.trabajoFinal.model.Itinerario;
import es.uji.ei1027.trabajoFinal.model.OfertaNominativa;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;
import es.uji.ei1027.trabajoFinal.model.PeticionRevision;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;
import es.uji.ei1027.trabajoFinal.model.ProfesorTutor;
import es.uji.ei1027.trabajoFinal.model.UserDetails;


@Controller
@RequestMapping("/btc")
public class BTCController{
	
	final String TIPO_USUARIO = "BTC";
	
	private EmpresaDao empresaDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PreferenciaAlumnoDao preferenciaAlumnoDao;
	private AsignacionDao asignacionDao;
	private EstanciaDao estanciaDao;
	private EstadosOfertaDao estadosOfertaDao;
	private ItinerarioDao itinerario;
	private EstadosAsignacionDao estadosAsignacionDao;
	private EstudianteDao estudianteDao;
	private PeticionDeRevisionDao peticionDeRevisionDao;
	private ProfesorTutorDao profesorTutorDao;
	private OfertaNominativaDao ofertaNominativaDao;
	
	
	@Autowired
	public void setofertaNominativaDao(OfertaNominativaDao ofertaNominativaDao){
        
        this.ofertaNominativaDao=ofertaNominativaDao;
    }

	
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao){
		
		this.empresaDao=empresaDao;
	}
	
	@Autowired
	public void setProfesorTutorDao(ProfesorTutorDao profesorTutorDao){
		
		this.profesorTutorDao=profesorTutorDao;
	}
	
	@Autowired
	public void setEstadosAsignacionDao(EstadosAsignacionDao estadosAsignacionDao){
		
		this.estadosAsignacionDao=estadosAsignacionDao;
	}
	
	@Autowired
	public void setPeticionDeRevisionDao(PeticionDeRevisionDao peticionDeRevisionDao){
		
		this.peticionDeRevisionDao=peticionDeRevisionDao;
	}
	
	@Autowired
	public void setAsignacionDao(AsignacionDao asignacionDao){
		
		this.asignacionDao=asignacionDao;
	}

	@Autowired
	public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao){
		
		this.ofertaProyectoDao=ofertaProyectoDao;
	}
	
	@Autowired
	public void setPreferenciaAlumnoDao(PreferenciaAlumnoDao preferenciaAlumnoDao){
		
		this.preferenciaAlumnoDao=preferenciaAlumnoDao;
	}
	
	@Autowired
	public void setEstanciaDao(EstanciaDao estanciaDao){
		
		this.estanciaDao=estanciaDao;
	}
	
	@Autowired
	public void setEstadosOfertasDao(EstadosOfertaDao estadosOfertaDao){
		
		this.estadosOfertaDao=estadosOfertaDao;
	}
	@Autowired
	public void setItinerarioDao(ItinerarioDao itinerario){
		
		this.itinerario=itinerario;
	}

	@Autowired
	public void setEstudianteDao(EstudianteDao estudianteDao){
		
		this.estudianteDao=estudianteDao;
	}
	
	
	
	@RequestMapping(value="/listAsignacionesAlumnosBTC", method = RequestMethod.GET)
	public String getAsignaciones(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
	
		model.addAttribute("asignaciones",asignacionDao.getAsignaciones());
		return "btc/listAsignacionesAlumnosBTC";
	}
	
	@RequestMapping(value="/addAsignacion") 
	public String addAsignacion(Model model, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("asignacion", new Asignacion());
		return "btc/addAsignacion";
	}
	
	@RequestMapping(value="/addAsignacion", method=RequestMethod.GET)
	public String processAddGet(Model model, HttpSession session) { 
		model.addAttribute("asignacion", new Asignacion());
		return "btc/seleccionarTutorParaAsignacion";
	}
	
	@RequestMapping(value="/addAsignacion", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("asignacion") Asignacion asignacion,
	                                BindingResult bindingResult, HttpSession session) { 
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		 if (bindingResult.hasErrors())
				return "btc/addAsignacion";
		 
		 asignacionDao.addAsignacion(asignacion);
		 
		 //HAY QUE CAMBIARLO HACÍA ADONDE QUEREMOS QUE SE REDIRIJA
		 return "redirect:/list"; 
	 }
	
	@RequestMapping(value="/updateAsignacion/{idasignacion}", method = RequestMethod.GET)
	public String UpdateAsignacion(Model model, @PathVariable int idasignacion, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("asignacion",asignacionDao.getAsignacion(idasignacion));

		List<EstadosAsignacion>listaEstados = estadosAsignacionDao.getEstados();
		
		model.addAttribute("estados",listaEstados);
		
		return "btc/updateAsignacion";
	}
	
	@RequestMapping(value="/updateAsignacion", method = RequestMethod.POST) 
	public String processUpdateSubmit(@ModelAttribute("asignacion") Asignacion asignacion,
                            BindingResult bindingResult, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		
		if (bindingResult.hasErrors()) {
			
			
			 return "redirect:listAsignacionesAlumnosBTC";

		 }
		 asignacionDao.updateAsignacion(asignacion);
		 return "redirect:listAsignacionesAlumnosBTC"; 
	  }
	
	@RequestMapping(value="/updatePeticionDeRevision/{idpeticion}", method = RequestMethod.GET)
	public String UpdatePeticion(Model model, @PathVariable int idpeticion, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("peticion",peticionDeRevisionDao.getPeticionRevision(idpeticion));
		return "btc/updateAsignacion";
	}
	
	@RequestMapping(value="/updatePeticionDeRevision/{idpeticion}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable int idpeticion, 
                            @ModelAttribute("peticion") PeticionRevision peticion, 
                            BindingResult bindingResult, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		 if (bindingResult.hasErrors()) 
			 return "btc/updatePeticionDeRevision";
		 peticionDeRevisionDao.updatePeticionRevision(peticion);
			 
		 //CAMBIAR HACÍA ADONDE QUEREMOS QUE SE DIRIJA
		 return "redirect:../list"; 
	  }
	
	@RequestMapping(value="/deleteAsignacion/{idasignacion}")
	public String processDelete(Model model,@PathVariable int idasignacion, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
		
			return "redirect:listAsignacionesAlumnosBTC";
		}
        asignacionDao.deleteAsignacion(idasignacion);
        //return "redirect:listAsignacionesAlumnosBTC";
        return "redirect:../listAsignacionesAlumnosBTC";
	}
	@RequestMapping(value="/listEmpresasBTC", method = RequestMethod.GET)
	public String getEmpresas(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("empresas",empresaDao.getEmpresas());
		return "btc/listEmpresasBTC";
	}
	
	@RequestMapping(value="/listOfertasBTC", method = RequestMethod.GET)
	public String listOfertasEmpresa(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
			
		Map<Integer,Empresa> mapEmpresa = new HashMap<>();
		List<OfertaProyecto> listaOfertas = ofertaProyectoDao.getOfertasProyecto();
		
		for(int i = 0; i < listaOfertas.size();i++){
			
			
			Empresa empresa = empresaDao.getEmpresaOferta(listaOfertas.get(i).getIdEstancia());
			mapEmpresa.put(listaOfertas.get(i).getIdEstancia(), empresa);
		}
		
		String estado = (String) session.getAttribute("estado");
		
		if (estado!=null){
			String mensaje = "";
			
			switch(estado){
				
				case "visibles": mensaje="Las ofertas aceptadas han pasado a ser visibles";
								 model.addAttribute("visibles",true);
								 break;
				
						
			}
			
		
			model.addAttribute("mensaje",mensaje);
			session.removeAttribute("estado");
			
		}
	
		model.addAttribute("ofertasProyecto", listaOfertas);
		model.addAttribute("empresas", mapEmpresa);

		
		return "btc/listOfertasBTC";
	}
	
	@RequestMapping(value="/setVisibles", method = RequestMethod.GET)
	public String setVisibles(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
			
		List<OfertaProyecto> noVisibles = ofertaProyectoDao.getOfertasProyectoNoVisibles();
		
		for (int i=0;i<noVisibles.size();i++){
			
			OfertaProyecto oferta = noVisibles.get(i);
			
			oferta.setVisible(true);
			
			oferta.setEstado("Visible para Alumnos");
			
			ofertaProyectoDao.updateOfertaProyecto(oferta);
				
		}
		
		session.setAttribute("estado","visibles");
		return "redirect:listOfertasBTC";
	}
	
	
	
	@RequestMapping(value="/listPreferenciasAlumnoBTC", method = RequestMethod.GET)
	public String listPreferenciasAlumno(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		
		List<Estudiante> estudiantes = estudianteDao.getEstudiantes();
		
		Map<String,List<PreferenciaAlumno>> preferencias = new HashMap<>();
		
		
		List<OfertaProyecto> ofertas = ofertaProyectoDao.getOfertasProyecto();
		Map<Integer, OfertaProyecto> nombreOfertas = new HashMap<Integer, OfertaProyecto>();
		
		OfertaProyecto ofer;
		for(int i=0;i<ofertas.size();i++) {
			
			ofer=ofertas.get(i);
			nombreOfertas.put(ofer.getIdOferta(),ofer);
		}
		
		
		for(int i = 0; i < estudiantes.size();i++){
			
			
			Estudiante estudiante = estudiantes.get(i);
			String dniEstudiante = estudiante.getDni();
			
			List<PreferenciaAlumno> listPref = preferenciaAlumnoDao.getPreferenciasAlumno(dniEstudiante);
		
			preferencias.put(dniEstudiante, listPref);
			
		}
		
		String estado=(String)session.getAttribute("asignacion");
		
		if (estado!=null){
			String mensaje = "";
			
			switch(estado){
				
				case "asignacionCorrecta": mensaje="La asignacion se ha realizado correctamente";
								 model.addAttribute("visibles",true);
								 break;
						
			}
			model.addAttribute("mensaje",mensaje);
			session.removeAttribute("asignacion");
			
		}
		
		
		model.addAttribute("estudiantes", estudiantes);
		model.addAttribute("preferencias",preferencias);
		return "btc/listEstudiantesParaAsignarBTC.html";
			
	}
	
	@RequestMapping(value="/listAsignacionesAlumnos", method = RequestMethod.GET)
	public String listAsignacionesAlumno(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		//Hago esto por que hay estudiante sin asignacion y salta error 
		//si quereis cambiar, tambien hay que cAMBIAR DE ASIGNACION EL ATRIBUTO
		
		List<Estudiante> estudiantes = estudianteDao.getEstudiantes();
		
		Map<String, Estudiante> nombreEstudiantes = new HashMap<String, Estudiante>();
		
		Estudiante est;
		for(int i=0;i<estudiantes.size();i++) {
			
			est=estudiantes.get(i);
			nombreEstudiantes.put(est.getDni(),est);
		}
		
		
		
		List<Asignacion> asignaciones = asignacionDao.getAsignaciones();
		
		
		Asignacion asig;
		for(int i = 0; i < asignaciones.size();i++){
			
			asig = asignaciones.get(i);
			//Obtiene el nombre del mapa
			asig.setNombreEstudiante(nombreEstudiantes.get(asig.getDniEstudianteAsignado()).getNombre());
			
		}
		
		
		model.addAttribute("asignaciones",asignaciones);
		return "btc/listAsignacionesAlumnos";
		
		
	}
	
	@RequestMapping(value="/getAsignacionEstudiante/{asignacion}", method = RequestMethod.GET)
	public String listAsignacionesEstudiante(Model model, @PathVariable int asignacion, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("asignacion",asignacionDao.getAsignacion(asignacion));
		return "btc/getAsignacionEstudiante";
	}
	
	@RequestMapping(value="/updateEstadoOfertaBTC/{idoferta}", method = RequestMethod.GET)
	public String UpdateEstadoOferta(Model model, @PathVariable int idoferta, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		model.addAttribute("oferta",ofertaProyectoDao.getOfertaProyecto(idoferta));
		
		List<EstadoOferta>listaEstados = estadosOfertaDao.getEstados();
		List<Itinerario>listaItinerarios = itinerario.getItinerarios();
		
		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(idoferta);
		
		Estancia estancia = estanciaDao.getEstancia(oferta.getIdEstancia());
		
		Empresa empresa = empresaDao.getEmpresa(estancia.getCifEmpresa());
		
		model.addAttribute("estados",listaEstados);
		model.addAttribute("itinerarios",listaItinerarios);
		model.addAttribute("empresa",empresa);

		
		
		return "btc/updateEstadoOfertaBTC";
	}

	@RequestMapping(value="/updateEstadoOfertaBTC", method = RequestMethod.POST) 
	public String processUpdateSubmit(@ModelAttribute("oferta") OfertaProyecto ofertaProyecto, 
                            BindingResult bindingResult, HttpSession session) {
		
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		 
		if (bindingResult.hasErrors()) {
			return "redirect:listOfertasBTC";
			 
		}
		 
		Calendar currentTime = Calendar.getInstance();
		Date sqlDate = new Date((currentTime.getTime()).getTime());
		
		ofertaProyecto.setFechaUltimoCambio(sqlDate);
		 
		if (ofertaProyecto.estado.equals("Visible para Alumnos")) ofertaProyecto.visible=true;
		 
		ofertaProyectoDao.updateOfertaProyecto(ofertaProyecto);
		 
		return "redirect:listOfertasBTC"; 
	  }
	
	@RequestMapping(value="/getOfertaBTC/{idOferta}", method=RequestMethod.GET)
	public String getOferta(Model model,HttpSession session,
			@PathVariable int idOferta){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		

		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(idOferta);
		
		Estancia estancia = estanciaDao.getEstancia(oferta.getIdEstancia());
		
		Empresa empresa = empresaDao.getEmpresa(estancia.getCifEmpresa());
		
		model.addAttribute("oferta",oferta);
		
		model.addAttribute("estancia",estancia);
		
		model.addAttribute("empresa",empresa);
		
		return "btc/getOfertaBTC";
		
	}
	
	@RequestMapping(value="/preferenciasEstudiante/{dniEstudiante}", method=RequestMethod.GET)
	public String getPreferenciasEstudiante(Model model,HttpSession session,
			@PathVariable String dniEstudiante){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		Map<Integer, Boolean> ofertasAsignadas = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> ofertasAsignadasAlEstudiante = new HashMap<Integer,Boolean>();
		boolean deshabilitar = false;
		
		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
		List<PreferenciaAlumno> listPrefDao = preferenciaAlumnoDao.getPreferenciasAlumno(dniEstudiante);
		List<OfertaProyecto> listPreferencias = new ArrayList<OfertaProyecto>();
		List<OfertaNominativa>listOfertasNominativas = ofertaNominativaDao.getOfertaNominativasEstudiante(dniEstudiante);
		
		for(PreferenciaAlumno preferencia:listPrefDao){
			
			List<Asignacion> asig = asignacionDao.getOfertaAsignadaQuitandoEstudiante(preferencia.getIdOfertaProyecto(),dniEstudiante);
			List<Asignacion> asigEstudiante = asignacionDao.getAsignacionEstudianteOferta(preferencia.getIdOfertaProyecto(),dniEstudiante);
			OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(preferencia.getIdOfertaProyecto());
			
			
			if(asig.isEmpty()){
				ofertasAsignadas.put(oferta.getIdOferta(), false);
			}else{
				ofertasAsignadas.put(oferta.getIdOferta(), true);
			}
			if(asigEstudiante.isEmpty()){
				ofertasAsignadasAlEstudiante.put(oferta.getIdOferta(), false);
			}else{
				ofertasAsignadasAlEstudiante.put(oferta.getIdOferta(), true);
				deshabilitar=true;
			}
			
			listPreferencias.add(oferta);
		}
		
		session.setAttribute("dniEstudiante",estudiante.getDni());
		
		model.addAttribute("estudiante",estudiante);
		model.addAttribute("deshabilitado",deshabilitar);
		model.addAttribute("ofertasPreferencia",listPreferencias);
		model.addAttribute("ofertasAsignadas",ofertasAsignadas);
		model.addAttribute("ofertasAsignadasAlEstudiante",ofertasAsignadasAlEstudiante);
		
	    List<OfertaProyecto>ofertasNominativas = new ArrayList<>();
        for (OfertaNominativa oferta:listOfertasNominativas){
            OfertaProyecto nominativa = ofertaProyectoDao.getOfertaProyecto(oferta.idOferta);
            ofertasNominativas.add(nominativa);
        }    
        model.addAttribute("ofertasNominativas",ofertasNominativas);
		
		return "btc/listPreferenciasUnEstudianteYAsignar.html";
		
	}
	
	@RequestMapping(value="/addAsignacion/{idOferta}", method=RequestMethod.GET)
	public String addAsignacion(Model model,HttpSession session,@PathVariable int idOferta){
	
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		String dniEstudiante = (String) session.getAttribute("dniEstudiante");
		

		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(idOferta);
		
		List<ProfesorTutor> tutores = profesorTutorDao.getProfesoresTutores();
		
		
		model.addAttribute("estudiante", estudiante);
		model.addAttribute("oferta", oferta);
		model.addAttribute("asignacion",new Asignacion());
		model.addAttribute("tutores", tutores);
		
		return "/btc/addAsignacion";
	}
	
	@RequestMapping(value="/addAsignacionPost", method=RequestMethod.POST)
	public String addAsignacionPost(Model model,HttpSession session,
			@ModelAttribute("asignacion") Asignacion asignacion){
	
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		Calendar currentTime = Calendar.getInstance();
		Date sqlDate = new Date((currentTime.getTime()).getTime());
		
		asignacion.setEstado("Propuesta");
		asignacion.setFechaPropuesta(sqlDate);
		
		asignacionDao.addAsignacion(asignacion);
		
		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(asignacion.getIdOfertaProyecto());
		
		oferta.setEstado("Asignada");
		
		ofertaProyectoDao.updateOfertaProyecto(oferta);
		
		session.setAttribute("asignacion", "asignacionCorrecta");
		
		return "redirect:/btc/listPreferenciasAlumnoBTC";
		
		
	}

}
