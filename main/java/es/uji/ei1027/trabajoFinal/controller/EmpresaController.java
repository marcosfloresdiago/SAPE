package es.uji.ei1027.trabajoFinal.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import es.uji.ei1027.trabajoFinal.dao.EmpresaDao;
import es.uji.ei1027.trabajoFinal.dao.EstanciaDao;
import es.uji.ei1027.trabajoFinal.dao.EstudianteDao;
import es.uji.ei1027.trabajoFinal.dao.ItinerarioDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaNominativaDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.dao.PeticionDeRevisionDao;
import es.uji.ei1027.trabajoFinal.model.Empresa;
import es.uji.ei1027.trabajoFinal.model.Estancia;
import es.uji.ei1027.trabajoFinal.model.Estudiante;
import es.uji.ei1027.trabajoFinal.model.Itinerario;
import es.uji.ei1027.trabajoFinal.model.OfertaNominativa;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;
import es.uji.ei1027.trabajoFinal.model.PeticionRevision;
import es.uji.ei1027.trabajoFinal.model.UserDetails;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	
	final String TIPO_USUARIO = "Empresa";

	
	private EmpresaDao empresaDao;
	private OfertaProyectoDao ofertaProyectoDao;
	private OfertaNominativaDao ofertaNominativaDao;
	private EstanciaDao estanciaDao;
	private ItinerarioDao itinerarioDao;
	private EstudianteDao estudianteDao;
	

	@Autowired
	public void setOfertaNominativaDao(OfertaNominativaDao ofertaNominativaDao){
		
		this.ofertaNominativaDao=ofertaNominativaDao;
	}
	@Autowired
	public void setEstudianteDao(EstudianteDao estudianteDao){
		
		this.estudianteDao=estudianteDao;
	}

	@Autowired
	public void setItinerarioDao(ItinerarioDao itinerarioDao){
		
		this.itinerarioDao=itinerarioDao;
	}
	
	@Autowired
	public void setEmpresaDao(EmpresaDao empresaDao){
		
		this.empresaDao=empresaDao;
	}
	@Autowired
	public void setOfertaProyectoDao(OfertaProyectoDao ofertaproyectoDao){
		
		this.ofertaProyectoDao=ofertaproyectoDao;
	}
	
	
	
	@Autowired
	public void setEstanciaDao(EstanciaDao estanciaDao){
		
		this.estanciaDao=estanciaDao;
	}
	
	
	//------------Empieza el codigo de la funcionalidad----------------///
	@RequestMapping(value="/listOfertasEmpresa", method = RequestMethod.GET)
	public String listOfertasEmpresa(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String cif = datosUsuario.getDni_cif();
		Empresa empresa = empresaDao.getEmpresa(cif);
		
		List<OfertaProyecto> sinNominativas = ofertaProyectoDao.getOfertaProyectoSinNominativaEmpresa(cif);
		List<OfertaProyecto> soloNominativas = ofertaProyectoDao.getOfertaProyectoSoloNominativaEmpresa(cif);
		List<OfertaNominativa> estudiante = ofertaNominativaDao.getOfertaNominativasEmpresa(cif);
		
		model.addAttribute("empresa",empresa);
		model.addAttribute("sinNominativas",sinNominativas);
		model.addAttribute("soloNominativas",soloNominativas);
		model.addAttribute("estudiantes",estudiante);
		
		
		return "empresa/listOfertasEmpresa";
	}

	
	@RequestMapping(value="/listEstancias", method = RequestMethod.GET)
	public String listEstanciasEmpresa(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String cif = datosUsuario.getDni_cif();
		
		model.addAttribute("empresa",datosUsuario);
		model.addAttribute("estancias",estanciaDao.getEstanciasEmpresa(cif));

		
		
		return "empresa/listEstancias";
	}
	
	/* -----------------------Editar la estancia de una empresa---------------- */
	@RequestMapping(value="/editarEstancia/{idEstancia}", method = RequestMethod.GET)
	public String editarEstancia(Model model,HttpSession session,
			@PathVariable int idEstancia){
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("estanciaEditada",new Estancia());
		
		model.addAttribute("estancia",estanciaDao.getEstancia(idEstancia));
		
		return "empresa/editarEstancia";
	}
	
	@RequestMapping(value="/editarEstancia/{idEstancia}", method = RequestMethod.POST) 
	public String processeditarEstancia(@ModelAttribute("estanciaEditada") Estancia estanciaEditada,
			@PathVariable int idEstancia,BindingResult bindingResult, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		 if (bindingResult.hasErrors()) 
			 return "empresa/editarEstancia";
		 
		 UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		 String cif = datosUsuario.getDni_cif();
		 
		 estanciaEditada.setCifEmpresa(cif);
		 estanciaEditada.setIdEstancia(idEstancia);
		 
		 estanciaDao.updateEstancia(estanciaEditada);
		 
		 return  "redirect:../listEstancias"; 
	  }
	

	
	@RequestMapping(value="/addOferta") 
	public String addOfertaProyecto(Model model, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		OfertaProyecto oferta = new OfertaProyecto();
		Estancia estancia = new Estancia();
		
		List<Itinerario> listaItinerarios= itinerarioDao.getItinerarios();
		
		model.addAttribute("itinerarios",listaItinerarios);
		
		model.addAttribute("oferta",oferta);
		model.addAttribute("estancia",estancia);
		
		return "empresa/addOferta";
	}
	

	@RequestMapping(value="/addOferta", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("oferta") OfertaProyecto oferta,@ModelAttribute("estancia") Estancia estancia,
	                                BindingResult bindingResult, HttpSession session) { 
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		if (bindingResult.hasErrors())
				return "empresa/addOferta";
		

		// Se crea la estancia....
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String cif = datosUsuario.getDni_cif();

		estancia.setCifEmpresa(cif);
		estanciaDao.addEstancia(estancia);
		
		// Se crea la oferta....
	
		List<Estancia> lista = estanciaDao.getEstanciasEmpresa(cif);
		Estancia idEstanciaNueva = lista.get(lista.size()-1);
		Calendar currentTime = Calendar.getInstance();
		Date sqlDate = new Date((currentTime.getTime()).getTime());
		oferta.setFechaAlta(sqlDate);
		oferta.setFechaUltimoCambio(sqlDate);
		oferta.setEstado("Introducida");
		oferta.setVisible(false);
		oferta.setIdEstancia(idEstanciaNueva.getIdEstancia());
		ofertaProyectoDao.addOfertaProyecto(oferta);
		
		
		return "redirect:../empresa/listOfertasEmpresa";	 
	}
	
	
	
	/* Añadir una oferta nominativa para los alumnos */
	
	
	
	@RequestMapping(value="/addOfertaNominativa") 
	public String addOfertaNominativa(Model model, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		OfertaProyecto oferta = new OfertaProyecto();
		Estancia estancia = new Estancia();
		OfertaNominativa nominativa = new OfertaNominativa();
		
		List<Itinerario> listaItinerarios= itinerarioDao.getItinerarios();
		
		model.addAttribute("itinerarios",listaItinerarios);
		
		model.addAttribute("oferta",oferta);
		model.addAttribute("estancia",estancia);
		model.addAttribute("nominativa",nominativa);
		
		return "empresa/addOfertaNominativa";
	}
	
	@RequestMapping(value="/addOfertaNominativa", method=RequestMethod.POST)
	public String processAddOfertaNominativa(@ModelAttribute("oferta") OfertaProyecto oferta,
			@ModelAttribute("estancia") Estancia estancia,@ModelAttribute("nominativa") OfertaNominativa nominativa,
	                                BindingResult bindingResult, HttpSession session) { 
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		if (bindingResult.hasErrors())
				return "empresa/addOferta";
		

		// Se crea la estancia....
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String cif = datosUsuario.getDni_cif();
		Estudiante estudiante;
		
		//---------------Comprueba que exista el estudiante---------
		
		try {
			
			estudiante = estudianteDao.getEstudiante(nominativa.getDniAlumno());
			
		}catch(Exception e) {	
			return "/empresa/fallo";
		}
		
		
		
		estancia.setCifEmpresa(cif);
		estanciaDao.addEstancia(estancia);
		
		// Se crea la oferta....
	
		List<Estancia> listaEstancias = estanciaDao.getEstanciasEmpresa(cif);
		Estancia idEstanciaNueva = listaEstancias.get(listaEstancias.size()-1);
		Calendar currentTime = Calendar.getInstance();
		Date sqlDate = new Date((currentTime.getTime()).getTime());
		oferta.setFechaAlta(sqlDate);
		oferta.setFechaUltimoCambio(sqlDate);
		oferta.setEstado("Introducida");
		oferta.setVisible(false);
		oferta.setIdEstancia(idEstanciaNueva.getIdEstancia());
		ofertaProyectoDao.addOfertaProyecto(oferta);
		
		List<OfertaProyecto> listaOferta =ofertaProyectoDao.getOfertasProyectoEmpresa(cif);
		OfertaProyecto idOferta = listaOferta.get(listaOferta.size()-1);
		
		nominativa.setIdOferta(idOferta.getIdOferta());
		nominativa.setNombreAlumno(estudiante.getNombre());
		ofertaNominativaDao.addOfertaNominativa(nominativa);
		
		return "redirect:../empresa/listOfertasEmpresa";	 
	}
	
	
	/* Añadir una peticion de revision de los alumnos */
	@RequestMapping(value="/editarOfertas/{idOferta}", method = RequestMethod.GET)
	public String getOfertaPeticion(Model model,HttpSession session,
			@PathVariable int idOferta){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		 
		List<Itinerario> listaItinerarios= itinerarioDao.getItinerarios();
		
		model.addAttribute("itinerarios",listaItinerarios);
		model.addAttribute("ofertaNueva",new OfertaProyecto());
		model.addAttribute("oferta",ofertaProyectoDao.getOfertaProyecto(idOferta));
		
		
		return "empresa/editarOfertas";
	}
	
	@RequestMapping(value="/editarOfertas/{idOferta}", method = RequestMethod.POST) 
	public String processAddSubmitPeticion(@ModelAttribute("ofertaNueva") OfertaProyecto ofertaNueva, @PathVariable int idOferta,
                            BindingResult bindingResult, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		 if (bindingResult.hasErrors()) 
			 return "empresa/addPeticionRevision";
		 OfertaProyecto ofertaVieja = ofertaProyectoDao.getOfertaProyecto(idOferta);
		 
		 Calendar currentTime = Calendar.getInstance();
		 Date sqlDateNueva = new Date((currentTime.getTime()).getTime());
		 
		 ofertaNueva.setFechaAlta(ofertaVieja.getFechaAlta());
		 ofertaNueva.setFechaUltimoCambio(sqlDateNueva);
		 ofertaNueva.setIdOferta(idOferta);
		 ofertaNueva.setEstado("Pendiente de Revisión");
		 ofertaNueva.setIdEstancia(ofertaVieja.getIdEstancia());
		 ofertaNueva.setVisible(false);
		 
		 ofertaProyectoDao.updateOfertaProyecto(ofertaNueva);
		 
		 return "redirect:../listOfertasEmpresa"; 
	  }
}
