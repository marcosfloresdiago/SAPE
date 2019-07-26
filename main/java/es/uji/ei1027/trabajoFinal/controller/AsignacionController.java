package es.uji.ei1027.trabajoFinal.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import es.uji.ei1027.trabajoFinal.dao.AsignacionDao;
import es.uji.ei1027.trabajoFinal.dao.ProfesorTutorDao;
import es.uji.ei1027.trabajoFinal.model.Asignacion;

@Controller
@RequestMapping("/asignacion")

public class AsignacionController {
	
	private AsignacionDao asignacionDao;
	private ProfesorTutorDao profesorTutorDao;
		
	@Autowired
	public void setAsignacionDao(AsignacionDao asignacionDao){
		
		this.asignacionDao=asignacionDao;
	}
	
	@Autowired
	public void setProfesorTutorDao(ProfesorTutorDao profesorTutorDao){
		
		this.profesorTutorDao=profesorTutorDao;
	}
	
	
	@RequestMapping(value="/get/{idasignacion}", method = RequestMethod.GET)
	public String getASignacion(Model model, @PathVariable int idasignacion){
		
		model.addAttribute("asignacion",asignacionDao.getAsignacion(idasignacion));
		return "asignacion/get";
	}
	
	@RequestMapping(value="/add") 
	public String addAsignacion(Model model) {
		model.addAttribute("asignacion", new Asignacion());
		return "asignacion/add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("asignacion") Asignacion asignacion,
	                                BindingResult bindingResult) { 
		 if (bindingResult.hasErrors())
				return "asignacion/add";
		 asignacionDao.addAsignacion(asignacion);
		 return "redirect:/list"; 
	 }
	
	@RequestMapping(value="/update/{idasignacion}", method = RequestMethod.GET)
	public String UpdateAsignacion(Model model, @PathVariable int idasignacion){
		model.addAttribute("asignacion",asignacionDao.getAsignacion(idasignacion));
		return "asignacion/update";
	}
	
	@RequestMapping(value="/update/{idasignacion}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable int idasignacion, 
                            @ModelAttribute("asignacion") Asignacion asignacion, 
                            BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) 
			 return "asignacion/update";
		 asignacionDao.updateAsignacion(asignacion);
		 return "redirect:../list"; 
	  }
	
	@RequestMapping(value="/delete/{idasignacion}")
	public String processDelete(@PathVariable int idasignacion) {
           asignacionDao.deleteAsignacion(idasignacion);
           return "redirect:../list"; 
	}

}
