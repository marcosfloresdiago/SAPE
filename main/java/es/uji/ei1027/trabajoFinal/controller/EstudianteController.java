package es.uji.ei1027.trabajoFinal.controller;



import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import es.uji.ei1027.trabajoFinal.dao.AsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.EmpresaDao;
import es.uji.ei1027.trabajoFinal.dao.EstanciaDao;
import es.uji.ei1027.trabajoFinal.dao.EstudianteDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaNominativaDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.dao.PreferenciaAlumnoDao;
import es.uji.ei1027.trabajoFinal.dao.ProfesorTutorDao;
import es.uji.ei1027.trabajoFinal.model.Asignacion;
import es.uji.ei1027.trabajoFinal.model.Empresa;
import es.uji.ei1027.trabajoFinal.model.Estancia;
import es.uji.ei1027.trabajoFinal.model.Estudiante;
import es.uji.ei1027.trabajoFinal.model.OfertaNominativa;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;
import es.uji.ei1027.trabajoFinal.model.ProfesorTutor;
import es.uji.ei1027.trabajoFinal.model.UserDetails;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {
	
	final String TIPO_USUARIO = "Estudiante";

	
	private EstudianteDao estudianteDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private PreferenciaAlumnoDao preferenciaDao;
	private AsignacionDao asignacionDao;
	private EmpresaDao empresaDao;
	private EstanciaDao estanciaDao;
	private ProfesorTutorDao profesorTutorDao;
	private OfertaNominativaDao ofertaNominativaDao;
	
	
	@Autowired
	public void setofertaNominativaDao(OfertaNominativaDao ofertaNominativaDao){
        
        this.ofertaNominativaDao=ofertaNominativaDao;
    }

	@Autowired
	public void setProfesorTutorDao(ProfesorTutorDao profesorTutorDao){
		
		this.profesorTutorDao=profesorTutorDao;
	}
	@Autowired
	public void setEstanciaDao(EstanciaDao estanciaDao){
		
		this.estanciaDao=estanciaDao;
	}
	@Autowired
	public void setEstudianteDao(EstudianteDao estudianteDao){
		
		this.estudianteDao=estudianteDao;
	}
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao){
		
		this.empresaDao=empresaDao;
		
	}
	@Autowired
	public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao){
		
		this.ofertaProyectoDao=ofertaProyectoDao;
	}
	@Autowired
	public void setPreferenciaAlumnoDao(PreferenciaAlumnoDao preferenciaDao){
		
		this.preferenciaDao=preferenciaDao;
	}
	@Autowired
	public void setAsignacionDao(AsignacionDao asignacionDao){
		
		this.asignacionDao=asignacionDao;
	}
	
	@RequestMapping("/indexEstudiante")
	public String login(Model model, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("user", new UserDetails());
		return "/estudiante/indexEstudiante";
	}
	
	
	@RequestMapping(value="/getEstudiante", method=RequestMethod.GET)
	public String getAlumno(Model model,HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();

		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
		
		model.addAttribute("estudiante",estudiante);
		
		return "/estudiante/getEstudiante";
		
	}
	
	@RequestMapping("/listOfertas")
	public String listOfertas(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		String estado = (String) session.getAttribute("estado");
		
		if (estado!=null){
			String mensaje = "";
			
			switch(estado){
				
				case "correcto": mensaje="Tus preferencias se han guardado correctamente";
								 model.addAttribute("correcto",true);
								 break;
				
				case "error": 	mensaje = "Has de seleccionar como mínimo 5 ofertas";
								model.addAttribute("error",true);
								break;
				case "semestre":mensaje = "Has seleccionado tu semestre de preferencia";
								model.addAttribute("semestre",true);
								break;
				case "preferencias": mensaje="Tus preferencias se han guardado correctamente";
				 				model.addAttribute("correcto",true);
				 				break;
						
			}
			
		
			model.addAttribute("mensaje",mensaje);
			session.removeAttribute("estado");
			
		}
		
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		
		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
			
		List<OfertaProyecto> ofertas = ofertaProyectoDao.getOfertasProyectoAceptadasItinerario(estudiante.itinerario);
		Map<Integer,Empresa> mapa = new HashMap <>();
		Map<Integer,Boolean> seleccionados = new HashMap <>();
		List<OfertaNominativa> nominativas = ofertaNominativaDao.getOfertasNominativas();
		List<PreferenciaAlumno> listPreferencias = preferenciaDao.getPreferenciasAlumno(dniEstudiante);
		List<Integer> listTemporalSeleccionadas = (List<Integer>) session.getAttribute("listTemporalSeleccionadas");
		
		if(listPreferencias!= null || listPreferencias.size()!= 0){
		
			for(int i = 0; i < ofertas.size();i++){
				boolean encontrado = false;
				for (int j=0;j<listPreferencias.size();j++){
					
					if (listPreferencias.get(j).getIdOfertaProyecto()==ofertas.get(i).getIdOferta()){
						encontrado=true;
						break;
					}
					
				}
				seleccionados.put(ofertas.get(i).getIdOferta(), encontrado);
			}
		
		}

		List<OfertaProyecto> definitivas = new ArrayList<>();
		boolean encontrado=false;
		for (int i=0;i<ofertas.size();i++){
			OfertaProyecto oferta = ofertas.get(i);
			int id=oferta.idOferta;
			for (int j=0;j<nominativas.size();j++){
				
				if (id == nominativas.get(j).idOferta)
					encontrado=true;
				
			}
			if (encontrado==false)
				definitivas.add(oferta);
			encontrado=false;
				
			
		}
		
		for (int i=0;i<ofertas.size();i++)
		
			mapa.put(ofertas.get(i).getIdEstancia(),empresaDao.getEmpresa(estanciaDao.getEstancia(ofertas.get(i).getIdEstancia()).getCifEmpresa()));
		

		model.addAttribute("empresas",mapa);
		model.addAttribute("ofertasProyecto",definitivas);
		model.addAttribute("seleccionados",seleccionados);
		
		return "estudiante/listOfertas";
	}
		
	
	@RequestMapping(value="/addPreferencia") 
	public String addPreferencia(Model model, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		//Hay que comprobar si el estudiante tiene abierto el plazo de seleccion e preferencias
		
			
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		
		List<OfertaProyecto> ofertas = new ArrayList<>();
		
		//Primero miramos si hay ofertas seleccionadas en la sesion
		
		List<Integer> listIdOfertasTemporal = (List<Integer>) session.getAttribute("ofertasTemporal");
		
		if(listIdOfertasTemporal != null) {
			
			for(int i = 0; i < listIdOfertasTemporal.size();i++) {
				
				ofertas.add(ofertaProyectoDao.getOfertaProyecto(listIdOfertasTemporal.get(i)));
				
			}
			
		//Si no hay ofertas en la sesion cojemos las de la bbdd	
		}else {
			
		
			List<PreferenciaAlumno> listPreferencias = preferenciaDao.getPreferenciasAlumno(dniEstudiante);
			
			for(int i = 0; i < listPreferencias.size();i++) {
				
				ofertas.add(ofertaProyectoDao.getOfertaProyecto(listPreferencias.get(i).getIdOfertaProyecto()));
				
			}
			
		}
		Map<Integer,Empresa> empresas = new HashMap<>();
		
		for(int i = 0; i < ofertas.size();i++) {
			
			Empresa empresa = empresaDao.getEmpresaOferta(ofertas.get(i).getIdEstancia());
			
			empresas.put(ofertas.get(i).getIdEstancia(),empresa);
			
		}
		
		List<Integer> preferenciaSemestre = new ArrayList<>();
		
		preferenciaSemestre.add(1);
		preferenciaSemestre.add(2);
		
		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
		
		
		model.addAttribute("estudiante",estudiante);
		model.addAttribute("semestre",preferenciaSemestre);
		model.addAttribute("ofertasTemporal", ofertas);
		model.addAttribute("empresa", empresas);
		
		//Guardamos en la sesion la id en lugar de la oferta para mantener la consistencia
		
		List<Integer> listOfertasTemporalId = new ArrayList<>();
		
		for(int i = 0 ; i < ofertas.size(); i++) {
			listOfertasTemporalId.add(ofertas.get(i).getIdOferta());
		}
		
		session.setAttribute("ofertasTemporal", listOfertasTemporalId);
		
		return "estudiante/addPreferencia";
	}
	
	
	@RequestMapping(value="/addPreferenciaSubir/{posicion}") 
	public String addPreferenciaSubir(Model model, HttpSession session, @PathVariable int posicion) {
		
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		

		List<Integer> listOfertasTemporalId = (List<Integer>) session.getAttribute("ofertasTemporal");

		if(posicion > 0) {
			Collections.swap(listOfertasTemporalId, posicion, posicion-1);
			
		}
		
		session.setAttribute("ofertasTemporal", listOfertasTemporalId);
		
		
		return "redirect:../addPreferencia";
		
	}
	
	@RequestMapping(value="/addPreferenciaBajar/{posicion}") 
	public String addPreferenciaBajar(Model model, HttpSession session, @PathVariable int posicion) {
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		

		List<Integer> listOfertasTemporalId = (List<Integer>) session.getAttribute("ofertasTemporal");


		if(posicion < listOfertasTemporalId.size()-1) {
			Collections.swap(listOfertasTemporalId, posicion, posicion+1);
		}
		
		session.setAttribute("ofertasTemporal", listOfertasTemporalId);

		return "redirect:../addPreferencia";
				
	}
	
	@RequestMapping(value="/setSemestre", method = RequestMethod.POST)
	public String setSemestre(Model model, HttpSession session,@ModelAttribute("semestreinicioestancia") int semestre) {
		
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();

		Estudiante estudiante = estudianteDao.getEstudiante(dniEstudiante);
		
		estudiante.setSemestreInicio(semestre);
		
		estudianteDao.updateEstudiante(estudiante);
		
		session.setAttribute("estado", "semestre");
		
		return "redirect:../estudiante/listOfertas";		
	}
	
	@RequestMapping(value="/addPreferencia/confirmar", method = RequestMethod.POST)
	public String addPreferenciaConfirmar(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		preferenciaDao.deletePreferenciaAlumno(dniEstudiante);
		List<Integer> lista = (List<Integer>) session.getAttribute("ofertasTemporal");
		
		for (int i=0;i<lista.size();i++){
			
			PreferenciaAlumno preferencia = new PreferenciaAlumno();
			
			preferencia.setIdOfertaProyecto(lista.get(i));
			preferencia.setOrden(i);
			preferencia.setDniEstudiante(dniEstudiante);
			
			preferenciaDao.addPreferenciaAlumno(preferencia);
		}
		
		
		session.setAttribute("estado", "preferencias");
		return "redirect:../listOfertas";
	}
	

	@RequestMapping(value="/addPreferenciaPost", method = RequestMethod.POST)
	public String addPreferenciaPost(Model model, HttpSession session,
			@RequestParam(value="checkbox",required=false)int [] checkbox){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		
		if (checkbox==null || checkbox.length<5){
			session.setAttribute("estado","error");
			session.removeAttribute("ofertasTemporal");
			
			return "redirect:../estudiante/listOfertas";
			
		}
		preferenciaDao.deletePreferenciaAlumno(dniEstudiante);
		
		List<Integer> listIdOfertas = new ArrayList<>();
		
		for (int i=0;i<checkbox.length;i++){
			PreferenciaAlumno preferencia = new PreferenciaAlumno ();
			UserDetails user = (UserDetails) session.getAttribute("user");
			preferencia.setDniEstudiante(user.getDni_cif());
			preferencia.setIdOfertaProyecto(checkbox[i]);
			listIdOfertas.add(checkbox[i]);
			preferencia.setOrden(i);
			preferenciaDao.addPreferenciaAlumno(preferencia);
		}
		
		
		
		session.setAttribute("estado", "correcto");
		
		session.setAttribute("ofertasTemporal", listIdOfertas);
		
		return "redirect:../estudiante/listOfertas";
	}
	
	
	
	@RequestMapping(value="/getOferta/{idOferta}", method=RequestMethod.GET)
	public String getOferta(Model model,HttpSession session,
			@PathVariable int idOferta, @RequestParam(value="checkbox",required=false)int [] checkbox){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		

		
		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(idOferta);
		
		Estancia estancia = estanciaDao.getEstancia(oferta.getIdEstancia());
		
		Empresa empresa = empresaDao.getEmpresa(estancia.getCifEmpresa());
		
		model.addAttribute("oferta",oferta);
		
		model.addAttribute("estancia",estancia);
		
		model.addAttribute("empresa",empresa);
		
		return "estudiante/getOferta";
		
	}
	
	@RequestMapping(value="/getAsignacion", method = RequestMethod.GET)
	public String getAsignacion(Model model,  HttpSession session){
				
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String dni = datosUsuario.getDni_cif();
		
		List <Asignacion> asignaciones = asignacionDao.getAsignacionEstudiante(dni);
		
		String estado = (String) session.getAttribute("estado");
		
		String mensaje = "";
		
		if (estado!=null){
			
			switch(estado){
				
				case "aceptada": mensaje="Has aceptado esta asignacion";
								 model.addAttribute("correcto",true);
								break;
				
				case "rechazada": mensaje = "Has rechazado esta asignacion";
								model.addAttribute("error",true);
								break;
				case "asignada": mensaje = "Esta asignacion ya ha sido aceptada o rechazada";
								model.addAttribute("informacion",true);
								break;
			}
		}
			
		Map <Integer,ProfesorTutor> profesores = new HashMap<>();
		
		Map <Integer,OfertaProyecto> ofertas = new HashMap<>();
		
		Map <Integer,Empresa> empresas = new HashMap<>();
		
		for (int i=0;i<asignaciones.size();i++){
			
			String dniProfesor = asignaciones.get(i).getDniProfesorAsignado();
			
			profesores.put(asignaciones.get(i).getIdAsignacion(),profesorTutorDao.getProfesorTutor(dniProfesor));
			
			int idOferta = asignaciones.get(i).getIdOfertaProyecto();
			
			ofertas.put(asignaciones.get(i).getIdAsignacion(),ofertaProyectoDao.getOfertaProyecto(idOferta));
			
			OfertaProyecto oferta = ofertas.get(asignaciones.get(i).getIdAsignacion());
		
			empresas.put(asignaciones.get(i).getIdAsignacion(),empresaDao.getEmpresaOferta(oferta.getIdEstancia()));
			
		}
		
		model.addAttribute("ofertas",ofertas);
		model.addAttribute("profesores",profesores);
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("asignaciones",asignaciones);
		model.addAttribute("empresas",empresas);
		
		session.removeAttribute("estado");
			
		model.addAttribute("asignaciones",asignaciones);
		
		return "estudiante/getAsignacion";
	}
//	@RequestMapping(value="/updateSemestre/{dni}", method = RequestMethod.GET)
//	public String UpdateSemestre(Model model, @PathVariable String dni, HttpSession session){
//		
//		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
//			return "redirect:/";
//		}
//		
//		
//		model.addAttribute("estudiante",estudianteDao.getEstudiante(dni));
//		return "estudiante/updateSemestre";
//	}
//	
//	@RequestMapping(value="/updateSemestre/{dni}", method = RequestMethod.POST) 
//	public String processUpdateSubmit(@PathVariable String dni, 
//                            @ModelAttribute("estudiante") Estudiante estudiante, 
//                            BindingResult bindingResult, HttpSession session) {
//		
//		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
//			return "redirect:/";
//		}
//	
//		 if (bindingResult.hasErrors()) 
//			 return "estudiante/updateSemestre";
//		 estudianteDao.updateEstudiante(estudiante);
//		 return "redirect:../list"; 
//	  }

	
	
	@RequestMapping(value="/aceptarAsignacion/{idasignacion}", method = RequestMethod.GET) 
	public String processUpdateSubmit(@PathVariable int idasignacion,  
			HttpSession session,Model model) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String dni = datosUsuario.getDni_cif();
		
		Asignacion asignacion = asignacionDao.getAsignacion(idasignacion);
		
		if(asignacion.getDniEstudianteAsignado().equals(dni) && asignacion.estado.equals("Propuesta")){
			session.setAttribute("estado","aceptada");
			asignacion.setEstado("Aceptada");
			Calendar currentTime = Calendar.getInstance();
			Date sqlDate = new Date((currentTime.getTime()).getTime());
			asignacion.setFechaAceptacion(sqlDate);
			asignacionDao.updateAsignacion(asignacion);
		}
		else{
			session.setAttribute("estado","asignada");
			
		}
		
		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(asignacion.getIdOfertaProyecto());
		
		oferta.setEstado("Asignada");
		
		ofertaProyectoDao.updateOfertaProyecto(oferta);
		
		return "redirect:../getAsignacion"; 
			
	}
	@RequestMapping(value="/rechazarAsignacion/{idasignacion}", method = RequestMethod.GET) 
	public String processRejectSubmit(@PathVariable int idasignacion,  
			HttpSession session,Model model) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String dni = datosUsuario.getDni_cif();
		
		Asignacion asignacion = asignacionDao.getAsignacion(idasignacion);
		
		if(asignacion.getDniEstudianteAsignado().equals(dni) && asignacion.estado.equals("Propuesta")){
			session.setAttribute("estado","rechazada");
			asignacion.setEstado("Rechazada");
			Calendar currentTime = Calendar.getInstance();
			Date sqlDate = new Date((currentTime.getTime()).getTime());
			asignacion.setFechaRechazo(sqlDate);
			asignacionDao.updateAsignacion(asignacion);
		}
		
		else{
			session.setAttribute("estado","asignada");	
		}
		
		OfertaProyecto oferta = ofertaProyectoDao.getOfertaProyecto(asignacion.getIdOfertaProyecto());
		
		oferta.setEstado("Visible para Alumnos");
		
		ofertaProyectoDao.updateOfertaProyecto(oferta);
		
		
		return "redirect:../getAsignacion"; 
		
	}
}
