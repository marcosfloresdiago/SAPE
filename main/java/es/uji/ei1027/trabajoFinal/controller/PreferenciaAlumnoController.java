package es.uji.ei1027.trabajoFinal.controller;

import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.dao.PreferenciaAlumnoDao;
import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;
import es.uji.ei1027.trabajoFinal.model.UserDetails;

@Controller
@RequestMapping("/preferenciaAlumno")
public class PreferenciaAlumnoController {
	
	//ESTO LO HE DEJADO POR SI SE PUEDE "RECICLAR" ALGO,MONKEYS
	
	private PreferenciaAlumnoDao preferenciaAlumnoDao;
	private OfertaProyectoDao ofertaProyectoDao;
	
	@Autowired
	public void setPreferenciaAlumnoDao(PreferenciaAlumnoDao preferenciaAlumnoDao){
		
		this.preferenciaAlumnoDao=preferenciaAlumnoDao;
	}
	
	@Autowired
	public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao){
		
		this.ofertaProyectoDao=ofertaProyectoDao;
	}
	
	
	@RequestMapping("/list")
	public String listPreferenciaAlumnos(Model model){
		
		model.addAttribute("preferenciaAlumnos",preferenciaAlumnoDao.getPreferenciaAlumnos());
		return "preferenciaAlumno/list";
	}
	
	
	
	
	
	@RequestMapping(value="/get/{dniEstudiante}", method = RequestMethod.GET)
	public String getPreferenciaAlumno(Model model, @PathVariable String dniEstudiante){
		
		model.addAttribute("preferenciaAlumno",preferenciaAlumnoDao.getPreferenciasAlumno(dniEstudiante));
		return "preferenciaAlumno/get";
	}
	
	@RequestMapping(value="/addPreferencia") 
	public String addPreferenciaAlumno(Model model) {
		model.addAttribute("preferenciaAlumno", new PreferenciaAlumno());
		model.addAttribute("ofertasProyecto",ofertaProyectoDao.getOfertasProyecto());

		return "preferenciaAlumno/addPreferencia";
	}
	
	@RequestMapping(value="/addPreferencia", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("preferenciaAlumno") PreferenciaAlumno preferenciaAlumno,
	                                BindingResult bindingResult, HttpSession session) {
		
		UserDetails datosUsuario = (UserDetails) session.getAttribute("user");
		String dniEstudiante = datosUsuario.getDni_cif();
		Calendar currentTime = Calendar.getInstance();
		Date sqlDate = new Date((currentTime.getTime()).getTime());
		
		preferenciaAlumno.setDniEstudiante(dniEstudiante);
		
		
		if (bindingResult.hasErrors())
				return "preferenciaAlumno/addPreferencia";
		 preferenciaAlumnoDao.addPreferenciaAlumno(preferenciaAlumno);
		 return "/indices/indexEstudiante";
	 }
	
	@RequestMapping(value="/update/{dniEstudiante}", method = RequestMethod.GET)
	public String UpdatePreferenciaAlumno(Model model, @PathVariable String dniEstudiante){
		model.addAttribute("preferenciaAlumno",preferenciaAlumnoDao.getPreferenciasAlumno(dniEstudiante));
		return "preferenciaAlumno/update";
	}
	
	@RequestMapping(value="/update/{dniEstudiante}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable String dniEstudiante, 
                            @ModelAttribute("preferenciaAlumno") PreferenciaAlumno preferenciaAlumno, 
                            BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) 
			 return "preferenciaAlumno/update";
		 preferenciaAlumnoDao.updatePreferenciaAlumno(preferenciaAlumno);
		 return "redirect:../list"; 
	  }
	
	@RequestMapping(value="/delete/{dniEstudiante}")
	public String processDelete(@PathVariable String dniEstudiante) {
           preferenciaAlumnoDao.deletePreferenciaAlumno(dniEstudiante);
           return "redirect:../list"; 
	}
	
	

}
