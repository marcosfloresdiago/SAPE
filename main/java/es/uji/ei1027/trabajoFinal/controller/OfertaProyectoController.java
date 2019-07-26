package es.uji.ei1027.trabajoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;

@Controller
@RequestMapping("/ofertaProyecto")
public class OfertaProyectoController {
	
	private OfertaProyectoDao ofertaProyectoDao;
	
	@Autowired
	public void setOfertaProyectoDao(OfertaProyectoDao ofertaProyectoDao){
		
		this.ofertaProyectoDao=ofertaProyectoDao;
	}
	
	@RequestMapping("/listOfertasAlumno")
	public String listOfertaProyecto(Model model){
		
		model.addAttribute("ofertasProyecto",ofertaProyectoDao.getOfertasProyecto());
		return "estudiante/listOfertasAlumno";
	}
	
	@RequestMapping(value="/get/{idEstancia}", method = RequestMethod.GET)
	public String getOfertaProyecto(Model model, @PathVariable int idEstancia){
		
		model.addAttribute("ofertaProyecto",ofertaProyectoDao.getOfertaProyecto(idEstancia));
		return "ofertaProyecto/get";
	}
	
	@RequestMapping(value="/add") 
	public String addOfertaProyecto(Model model) {
		model.addAttribute("ofertaProyecto", new OfertaProyecto());
		return "ofertaProyecto/add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("ofertaProyecto") OfertaProyecto ofertaProyecto,
	                                BindingResult bindingResult) { 
		 if (bindingResult.hasErrors())
				return "ofertaProyecto/add";
		 ofertaProyectoDao.addOfertaProyecto(ofertaProyecto);
		 return "redirect:/list"; 
	 }
	
	@RequestMapping(value="/update/{idEstancia}", method = RequestMethod.GET)
	public String UpdateOfertaProyecto(Model model, @PathVariable int idEstancia){
		model.addAttribute("ofertaProyecto",ofertaProyectoDao.getOfertaProyecto(idEstancia));
		return "ofertaProyecto/update";
	}
	
	@RequestMapping(value="/update/{idEstancia}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable int idEstancia, 
                            @ModelAttribute("ofertaProyecto") OfertaProyecto ofertaProyecto, 
                            BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) 
			 return "ofertaProyecto/update";
		 ofertaProyectoDao.updateOfertaProyecto(ofertaProyecto);
		 return "redirect:../list"; 
	  }
	
	@RequestMapping(value="/delete/{idEstancia}")
	public String processDelete(@PathVariable int idEstancia) {
           ofertaProyectoDao.deleteOfertaProyecto(idEstancia);
           return "redirect:../list"; 
	}
	
	
	
	
	
	
	

}
