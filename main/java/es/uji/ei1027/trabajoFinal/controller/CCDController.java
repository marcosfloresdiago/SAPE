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

import es.uji.ei1027.trabajoFinal.dao.AsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.EmpresaDao;
import es.uji.ei1027.trabajoFinal.dao.EstadosOfertaDao;
import es.uji.ei1027.trabajoFinal.dao.EstanciaDao;
import es.uji.ei1027.trabajoFinal.dao.EstudianteDao;
import es.uji.ei1027.trabajoFinal.dao.ItinerarioDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.dao.PreferenciaAlumnoDao;
import es.uji.ei1027.trabajoFinal.model.Asignacion;
import es.uji.ei1027.trabajoFinal.model.Empresa;
import es.uji.ei1027.trabajoFinal.model.EstadoOferta;
import es.uji.ei1027.trabajoFinal.model.Estancia;
import es.uji.ei1027.trabajoFinal.model.Estudiante;
import es.uji.ei1027.trabajoFinal.model.Itinerario;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;

@Controller
@RequestMapping("/ccd")
public class CCDController {
	
	
	final String TIPO_USUARIO = "CCD";

	
	private EmpresaDao empresaDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PreferenciaAlumnoDao preferenciaAlumnoDao;
	private AsignacionDao asignacionDao;
	private EstanciaDao estanciaDao;
	private EstadosOfertaDao estadosOfertaDao;
	private EstudianteDao estudianteDao;
	private ItinerarioDao itinerarioDao;
	
	
	@Autowired
	public void setItinerarioDao(ItinerarioDao itinerarioDao){
		
		this.itinerarioDao=itinerarioDao;
	}
	
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao){
		
		this.empresaDao=empresaDao;
	}
	
	@Autowired
	public void setAsignacionDao(AsignacionDao asignacionDao){
		
		this.asignacionDao=asignacionDao;
	}
	public AsignacionDao getAsignacionDao(){
		
		return asignacionDao;
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
	public void setEstudianteDao(EstudianteDao estudianteDao){
		
		this.estudianteDao=estudianteDao;
	}
	
	
	
	
	@RequestMapping(value="/listEmpresasCCD", method = RequestMethod.GET)
	public String getEmpresas(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("empresas",empresaDao.getEmpresas());
		return "ccd/listEmpresasCCD";
	}
	
	@RequestMapping(value="/listOfertasCCD", method = RequestMethod.GET)
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
		
		model.addAttribute("ofertasProyecto", listaOfertas);
		model.addAttribute("empresas", mapEmpresa);

		
		return "ccd/listOfertasCCD";
	}
	
	
	
	@RequestMapping(value="/listPreferenciasAlumno", method = RequestMethod.GET)
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
		
		
		model.addAttribute("estudiantes", estudiantes);
		model.addAttribute("preferencias",preferencias);
		
		return "ccd/listPreferenciasAlumno";

	}
	
	@RequestMapping(value="/preferenciasEstudiante/{dniEstudiante}", method=RequestMethod.GET)
	public String getPreferenciasEstudiante(Model model,HttpSession session,
			@PathVariable String dniEstudiante){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		Map<Integer, Boolean> ofertasAsignadas = new HashMap<Integer, Boolean>();
		
		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
		List<PreferenciaAlumno> listPrefDao = preferenciaAlumnoDao.getPreferenciasAlumno(dniEstudiante);
		List<OfertaProyecto> listPreferencias = new ArrayList<OfertaProyecto>();
		
		for(PreferenciaAlumno preferencia:listPrefDao){
			List<Asignacion> asig = asignacionDao.getOfertaAsignada(preferencia.getIdOfertaProyecto());
			OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(preferencia.getIdOfertaProyecto());
			if(asig.isEmpty()){
				ofertasAsignadas.put(oferta.getIdOferta(), false);
			}else
				ofertasAsignadas.put(oferta.getIdOferta(), true);
			listPreferencias.add(oferta);
		}
		
				
		
		model.addAttribute("estudiante",estudiante);
		
		model.addAttribute("ofertasPreferencia",listPreferencias);
		model.addAttribute("ofertasAsignadas",ofertasAsignadas);
		
		return "ccd/listPreferenciasUnEstudiante.html";
		
	}
	
	@RequestMapping(value="/listAsignacionesAlumnos", method = RequestMethod.GET)
	public String listAsignacionesAlumno(Model model, HttpSession session){
		
		//Hago esto por que hay estudiante sin asignacion y salta error 
		//si quereis cambiar, tambien hay que cAMBIAR DE ASIGNACION EL ATRIBUTO
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
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
		return "ccd/listAsignacionesAlumnos";
		
		
	}
	
	
	
	
	@RequestMapping(value="/getAsignacionEstudiante/{dni}", method = RequestMethod.GET)
	public String listAsignacionesEstudiante(Model model, @PathVariable String dni, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("asignaciones",asignacionDao.getAsignacionEstudiante(dni));
		return "ccd/getAsignacionEstudiante";
	}
	
	@RequestMapping(value="/updateEstadoOferta/{idoferta}", method = RequestMethod.GET)
	public String UpdateEstadoOferta(Model model, @PathVariable int idoferta, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("oferta",ofertaProyectoDao.getOfertaProyecto(idoferta));
		
		List<EstadoOferta>listaEstados = estadosOfertaDao.getEstados();
		
		List<Itinerario>itinerarios = itinerarioDao.getItinerarios();
		
		for (int i=0;i<listaEstados.size();i++){
			
			if (listaEstados.get(i).estado.equals("Visible para Alumnos") || listaEstados.get(i).estado.equals("Asignada"))
				listaEstados.remove(i);
			
		}
		
		model.addAttribute("itinerarios",itinerarios);
		model.addAttribute("estados",listaEstados);
		
		
		return "ccd/updateEstadoOferta";
	}

	@RequestMapping(value="/updateEstadoOferta", method = RequestMethod.POST) 
	public String processUpdateSubmit(@ModelAttribute("oferta") OfertaProyecto ofertaProyecto, 
                            BindingResult bindingResult, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		 
		if (bindingResult.hasErrors()) 
			 return "ccd/updateEstadoOferta";
		 
		Calendar currentTime = Calendar.getInstance();
		Date sqlDate = new Date((currentTime.getTime()).getTime());
		
		ofertaProyecto.setFechaUltimoCambio(sqlDate);
		 
		ofertaProyectoDao.updateOfertaProyecto(ofertaProyecto);
		 
		return "redirect:listOfertasCCD"; 
	  }
	
	@RequestMapping(value="/getOfertaCCD/{idOferta}", method=RequestMethod.GET)
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
		
		return "ccd/getOfertaCCD";
		
	}
}
