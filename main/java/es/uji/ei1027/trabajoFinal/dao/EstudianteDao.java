package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import es.uji.ei1027.trabajoFinal.model.Estudiante;


@Repository
public class EstudianteDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class EstudianteMapper implements RowMapper<Estudiante> {
		
		public Estudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Estudiante estudiante = new Estudiante();
			
			estudiante.setDni(rs.getString("dni"));
	
			estudiante.setNombre(rs.getString("nombre"));
			
			estudiante.setOrden(rs.getInt("orden"));
			
			estudiante.setItinerario(rs.getString("itinerario"));
	
			estudiante.setNumAsigPend(rs.getInt("numasignaturaspendientes4t"));
		
			estudiante.setSemestreInicio(rs.getInt("semestreinicioestancia"));
			
			estudiante.setEstadoPreferencia(rs.getString("estadopreferencia"));
			
			estudiante.setFechaUltimoCambioPreferencia(rs.getDate("fechaultimocambiopreferencia"));
		
			return estudiante;
			
		}
	}
	
	public List<Estudiante>getEstudiantes(){
		
		return this.jdbcTemplate.query("select * from estudiante ORDER BY orden;",new EstudianteMapper());
		
	}
	
	public Estudiante getEstudiante(String dni){
		return this.jdbcTemplate.queryForObject("select * from Estudiante where dni=?", new Object[] {dni},new EstudianteMapper());
		
	}
	
	
	public void addEstudiante(Estudiante estudiante){
		
		this.jdbcTemplate.update(
				"insert into Estudiante (dni,nombre,orden,itinenario,numasignaturaspendientes4t,semestreinicioestancia) values(?, ?, ?, ?, ?, ? )", 
				estudiante.getDni(),estudiante.getNombre(),
				estudiante.getOrden(),estudiante.getItinerario(),
				estudiante.getNumAsigPend(),estudiante.getSemestreInicio());
		
		
	}
	
	public void deleteEstudiante(String dni) {
		this.jdbcTemplate.update(
		        "delete from Estudiante where dni = ?", dni);
	}
	
	public void updateEstudiante(Estudiante estudiante) {
		this.jdbcTemplate.update(
				"update Estudiante set nombre=?, orden=?,"
				+ "itinerario=?,numasignaturaspendientes4t=?,semestreinicioestancia=? WHERE dni=?" , 
				estudiante.getNombre(),estudiante.getOrden(),
				estudiante.getItinerario(),estudiante.getNumAsigPend(),estudiante.getSemestreInicio(),estudiante.getDni());
	}


}
