package es.uji.ei1027.trabajoFinal.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.validation.Errors; 
import org.springframework.validation.Validator;

import es.uji.ei1027.trabajoFinal.dao.UserDao; 
import es.uji.ei1027.trabajoFinal.model.UserDetails;

class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return UserDetails.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
	  // Exercici: Afegeix codi per comprovar que 
         // l'usuari i la contrasenya no estiguen buits 
         // ...

	}
}

@Controller
public class LoginController {
	@Autowired
	private UserDao userDao;

	@RequestMapping("/login")
	public String login(Model model,HttpSession session) {
		
		
		UserDetails user = (UserDetails) session.getAttribute("user");
	
		if (user != null) {
			String ruta = "indices/index";
			String tipo = user.getTipo();
			ruta += tipo;
			
			return ruta;
		
		}else
			
		model.addAttribute("user", new UserDetails());
		
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") UserDetails user,  		
				BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator(); 
		userValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}
	    // Comprova que el login siga correcte 
		// intentant carregar les dades de l'usuari 
		user = userDao.loadUserByUsername(user.getUsername(),user.getPassword()); 
		if (user == null) {
			bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta"); 
			return "login";
		}
		// Autenticats correctament. 
		// Guardem les dades de l'usuari autenticat a la sessió
		session.setAttribute("user", user); 
		
		// Torna a la pàgina principal
		
		String ruta = "indices/index";
		String tipo = user.getTipo();
		ruta += tipo;
		
		return ruta;
	}
	
	
	
	

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
	
	
	public static boolean permisosUsuario(String tipo, HttpSession session){
	
		
		UserDetails user = (UserDetails) session.getAttribute("user");

		if(user == null){
			return false;
		}
		
		if (user.getTipo() != tipo){
			return false;
		}
		
		return true;

	}
	
	
	
	
	
	
	
}
