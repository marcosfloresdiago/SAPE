package es.uji.ei1027.trabajoFinal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.uji.ei1027.trabajoFinal.dao.AsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.EmpresaDao;
import es.uji.ei1027.trabajoFinal.dao.EstanciaDao;
import es.uji.ei1027.trabajoFinal.dao.EstudianteDao;
import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.dao.ProfesorTutorDao;
import es.uji.ei1027.trabajoFinal.model.Asignacion;
import es.uji.ei1027.trabajoFinal.model.Empresa;
import es.uji.ei1027.trabajoFinal.model.Estancia;
import es.uji.ei1027.trabajoFinal.model.Estudiante;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;
import es.uji.ei1027.trabajoFinal.model.ProfesorTutor;
import es.uji.ei1027.trabajoFinal.model.UserDetails;

@Controller
@RequestMapping("/tutor")
public class TutorController {
	
	
	final String TIPO_USUARIO = "Tutor";

	
	private OfertaProyectoDao ofertaProyectoDao;
	private AsignacionDao asignacionDao;
	private EstudianteDao estudianteDao;
	private EmpresaDao empresaDao;
	private EstanciaDao estanciaDao;
	private ProfesorTutorDao profesorTutorDao;
	
	@Autowired
	public void setProfesorTutorDao(ProfesorTutorDao profesorTutorDao){
		
		this.profesorTutorDao=profesorTutorDao;
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
	public void setEstanciaDao(EstanciaDao estanciaDao){
		
		this.estanciaDao=estanciaDao;
	}
	
	@Autowired
	public void setAsignacionDao(AsignacionDao asignacionDao){
		
		this.asignacionDao=asignacionDao;
	}
	
	@Autowired
	public void setEstudianteDao(EstudianteDao estudianteDao){
		
		this.estudianteDao=estudianteDao;
	}
	
	

	@RequestMapping("/indexTutor")
	public String login(Model model, HttpSession session) {
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		model.addAttribute("user", new UserDetails());
		return "/indices/indexTutor";
	}

	
	@RequestMapping("/listOfertas")
	public String listOfertas(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		
		List<OfertaProyecto> ofertas = ofertaProyectoDao.getOfertasProyectoAceptadas();
		Map<Integer,Empresa> mapa = new HashMap <>();

		
		for (int i=0;i<ofertas.size();i++)
		
			mapa.put(ofertas.get(i).getIdEstancia(),empresaDao.getEmpresa(estanciaDao.getEstancia(ofertas.get(i).getIdEstancia()).getCifEmpresa()));
		

		model.addAttribute("empresas",mapa);
		model.addAttribute("ofertasProyecto",ofertas);
		
		
		return "tutor/listOfertas";

	
	}
	
	@RequestMapping("/listAsignaciones")
	public String listAsignaciones(Model model, HttpSession session){
		
		if(LoginController.permisosUsuario(TIPO_USUARIO, session) == false){
			return "redirect:/";
		}
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");	
		
		String dni = datosUsuario.getDni_cif();
		
		List <Asignacion> asignaciones = asignacionDao.getAsignacionesTutor(dni);
		
		Map <Integer,ProfesorTutor> profesores = new HashMap<>();
		
		Map <Integer,OfertaProyecto> ofertas = new HashMap<>();
		
		Map <Integer,Empresa> empresas = new HashMap<>();
		
		Map <Integer,Empresa> estudiantes = new HashMap<>();
		
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
		model.addAttribute("asignaciones",asignaciones);
		model.addAttribute("empresas",empresas);
		model.addAttribute("asignaciones",asignaciones);
		
		return "tutor/listAsignaciones";
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
		
		return "tutor/getOferta";
		
	}

}
