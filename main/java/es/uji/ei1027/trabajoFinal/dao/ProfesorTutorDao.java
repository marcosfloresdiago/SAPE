package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.ProfesorTutor;

@Repository
public class ProfesorTutorDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	
	}
	
	private static final class ProfesorTutorMapper implements RowMapper<ProfesorTutor> {

		
		public ProfesorTutor mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ProfesorTutor tutor = new ProfesorTutor();
			tutor.setDni(rs.getString("dni"));
			tutor.setNombre(rs.getString("nombre"));
			tutor.setDepartamento(rs.getString("departamento"));
			tutor.setDespacho(rs.getString("despacho"));
			tutor.setEmail(rs.getString("email"));
			
			return tutor;
			
		}
	}
		
		public List<ProfesorTutor> getProfesoresTutores(){
			return this.jdbcTemplate.query("select * from profesortutor ORDER BY dni;",new ProfesorTutorMapper());
		}
		
		public ProfesorTutor getProfesorTutor(String dni){
			return this.jdbcTemplate.queryForObject("select * from ProfesorTutor where dni=?", new Object[] {dni},new ProfesorTutorMapper());
			
		}
		
		public void updateProfesorTutor(ProfesorTutor tutor) {
			this.jdbcTemplate.update(
					"update ProfesorTutor set dni=?, nombre=?, departamento=?, despacho=?, email= ? where dni = ?", 
					tutor.getDni(),tutor.getNombre(),tutor.getDepartamento(),tutor.getDespacho(),tutor.getEmail(),tutor.getDni());
		}

		public void deleteProfesorTutor(String dni) {
			this.jdbcTemplate.update(
			        "delete from ProfesorTutor where dni = ?", dni);
		}

		public void addProfesorTutor(ProfesorTutor tutor) {
			this.jdbcTemplate.update(
					"insert into ProfesorTutor (dni,nombre,departamento,despacho,email) values(?, ?, ?, ?, ?)", 
					tutor.getDni(),tutor.getNombre(),tutor.getDepartamento(),tutor.getDespacho(),tutor.getEmail());
		}
		
		
		
}

	
