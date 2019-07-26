package es.uji.ei1027.trabajoFinal.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jasypt.util.password.BasicPasswordEncryptor;

import es.uji.ei1027.trabajoFinal.model.UserDetails;

public class UserProvider implements UserDao {
	
	final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

	public UserProvider() {
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
		UserDetails userFidel = new UserDetails(); 
		userFidel.setUsername("fidel"); 
		userFidel.setPassword(passwordEncryptor.encryptPassword("fidel"));
		userFidel.setDni_cif("20784759I");
		userFidel.setTipo("BTC");
		knownUsers.put("fidel", userFidel);	
		
       UserDetails userBob = new UserDetails(); 
       userBob.setUsername("bob"); 
       userBob.setPassword(passwordEncryptor.encryptPassword("bob")); 
       userBob.setDni_cif("18436483I");
       userBob.setTipo("CCD");
       knownUsers.put("bob", userBob);
       
       UserDetails tutor= new UserDetails(); 
       tutor.setUsername("manuel"); 
       tutor.setPassword(passwordEncryptor.encryptPassword("manuel")); 
       tutor.setDni_cif("20898097N");
       tutor.setTipo("Tutor");
       knownUsers.put("manuel", tutor);
       
       UserDetails user = new UserDetails(); 
       user.setUsername("albertin");
       user.setPassword(passwordEncryptor.encryptPassword("albertin")); 
       user.setDni_cif("03993390L");
       user.setTipo("Empresa");
       knownUsers.put("albertin", user);
       
       UserDetails userEstudiante = new UserDetails(); 
       userEstudiante.setUsername("mark");
       userEstudiante.setPassword(passwordEncryptor.encryptPassword("mark")); 
       userEstudiante.setDni_cif("20573254H");
       userEstudiante.setTipo("Estudiante");
       knownUsers.put("mark", userEstudiante);
	}
	

	@Override
	public UserDetails loadUserByUsername(String username, String password) {
		
		 UserDetails user = knownUsers.get(username.trim());
		 if (user == null)
			 return null; // Usuari no trobat
		  // Contrasenya
		 BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
		 if (passwordEncryptor.checkPassword(password, user.getPassword())) {
			 
		 // Es deuria esborrar de manera segura el camp password abans de tornar-lo
			 return user; 
	         } 
		 else {
			 return null; // bad login!
		 }
	  }

	@Override	
	public Collection<UserDetails> listAllUsers() {
		
		return knownUsers.values();
	}

}
