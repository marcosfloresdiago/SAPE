package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.PreferenciaAlumno;


@Repository
public class PreferenciaAlumnoDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class PreferenciaAlumnoMapper implements RowMapper<PreferenciaAlumno> {
		
		public PreferenciaAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			PreferenciaAlumno preferenciaAlumno = new PreferenciaAlumno();
			preferenciaAlumno.setOrden(rs.getInt("orden"));
			preferenciaAlumno.setDniEstudiante(rs.getString("dniEstudiante"));
			
			preferenciaAlumno.setIdOfertaProyecto(rs.getInt("idOfertaProyecto"));
			
			return preferenciaAlumno;
		}
	}
	
	public List<PreferenciaAlumno> getPreferenciaAlumnos(){
		return this.jdbcTemplate.query("select * from preferenciaAlumno ORDER BY dniEstudiante;",new PreferenciaAlumnoMapper());
	}
	

	

	
	
	public List<PreferenciaAlumno> getPreferenciasAlumno(String dniEstudiante){
		return this.jdbcTemplate.query("select * from preferenciaAlumno where dniEstudiante=? order by orden", new Object[] {dniEstudiante},new PreferenciaAlumnoMapper());
		
	}
	
	public void updatePreferenciaAlumno(PreferenciaAlumno preferenciaAlumno) {
		this.jdbcTemplate.update(
				"update PreferenciaAlumno set orden=?, dniEstudiante=?,idOfertaProyecto=? where dniEstudiante = ?", 
				preferenciaAlumno.getOrden(), preferenciaAlumno.getDniEstudiante(), 
				preferenciaAlumno.getIdOfertaProyecto(), preferenciaAlumno.getDniEstudiante());
	}

	public void deletePreferenciaAlumno(String dniEstudiante) {
		this.jdbcTemplate.update(
		        "delete from preferenciaAlumno where dniEstudiante = ?", dniEstudiante);
	}

	public void addPreferenciaAlumno(PreferenciaAlumno preferenciaAlumno) {
		this.jdbcTemplate.update(
				"insert into preferenciaAlumno(orden,dniEstudiante,idOfertaProyecto) values (?, ?, ?)", 
				preferenciaAlumno.getOrden(), preferenciaAlumno.getDniEstudiante(), 
				preferenciaAlumno.getIdOfertaProyecto());
	}



	public PreferenciaAlumno getPreferenciasAlumnoIdOferta(Integer idOferta, String dniEstudiante) {
		return this.jdbcTemplate.queryForObject("select * from preferenciaAlumno where idOfertaProyecto=? AND dniEstudiante=? order by orden", new Object[] {idOferta,dniEstudiante},new PreferenciaAlumnoMapper());
	
	}

}
