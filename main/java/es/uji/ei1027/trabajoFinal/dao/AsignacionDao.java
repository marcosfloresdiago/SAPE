package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.Asignacion;

@Repository
public class AsignacionDao {
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class AsignacionMapper implements RowMapper<Asignacion> {
		
		public Asignacion mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Asignacion asignacion = new Asignacion();
			
			asignacion.setIdAsignacion(rs.getInt("idasignacion"));
			asignacion.setFechaPropuesta(rs.getDate("fechaPropuesta"));
			asignacion.setFechaAceptacion(rs.getDate("fechaAceptacion"));
			asignacion.setFechaRechazo(rs.getDate("fechaRechazo"));
			asignacion.setFechaTraspasoIGLU(rs.getDate("fechatraspasoiglu"));
			asignacion.setEstado(rs.getString("estado"));
			asignacion.setIdOfertaProyecto(rs.getInt("idofertaproyecto"));
			asignacion.setDniProfesorAsignado(rs.getString("dniprofesorasignado"));
			asignacion.setDniEstudianteAsignado(rs.getString("dniestudianteasignado"));
			
			return asignacion;
		}
	}
	public List<Asignacion> getAsignaciones(){
		return this.jdbcTemplate.query("select * from Asignacion ORDER BY idasignacion;",new AsignacionMapper());
	}
	
	public Asignacion getAsignacion(int idasignacion){
		return this.jdbcTemplate.queryForObject("select * from Asignacion where idasignacion=?", new Object[] {idasignacion},new AsignacionMapper());
		
	}
	public List<Asignacion> getOfertaAsignada(int idofertaproyecto){
		return this.jdbcTemplate.query("select * from Asignacion where idofertaproyecto=?", new Object[] {idofertaproyecto},new AsignacionMapper());
		
	}
	
	public List<Asignacion> getOfertaAsignadaQuitandoEstudiante(int idofertaproyecto,String dni){
		return this.jdbcTemplate.query("select * from Asignacion where idofertaproyecto=? and dniestudianteasignado!=? and (estado='Propuesta' or estado='Aceptada')" , new Object[] {idofertaproyecto,dni},new AsignacionMapper());
		
	}
	
	public List<Asignacion> getAsignacionEstudianteOferta(int idofertaproyecto,String dni){
		return this.jdbcTemplate.query("select * from Asignacion where idofertaproyecto=? and dniestudianteasignado=? and (estado='Propuesta' or estado='Aceptada')", new Object[] {idofertaproyecto,dni},new AsignacionMapper());
		
	}
	
	public List<Asignacion> getAsignacionEstudiante(String dni){
		return this.jdbcTemplate.query("select * from Asignacion where dniestudianteasignado=? and (estado='Propuesta' or estado='Aceptada' or estado='Rechazada')", new Object[] {dni},new AsignacionMapper());
		
	}
	public List<Asignacion> getAsignacionesTutor(String dniProfesorAsignado){
		return this.jdbcTemplate.query("select * from Asignacion where dniprofesorasignado=?", new Object[] {dniProfesorAsignado},new AsignacionMapper());
		
	}
	
	public void updateAsignacion(Asignacion asignacion) {
		this.jdbcTemplate.update(
				"update Asignacion set fechaPropuesta=?, fechaAceptacion=?,"
				+ "estado= ?, idofertaproyecto = ?,"
				+ "fechaRechazo = ?,"		
				+ "dniprofesorasignado=?, dniestudianteasignado=?  where idasignacion = ?", 
				asignacion.getFechaPropuesta(),
				asignacion.getFechaAceptacion(), 
				asignacion.getEstado(),asignacion.getIdOfertaProyecto(),asignacion.getFechaRechazo(),asignacion.getDniProfesorAsignado(),
				asignacion.getDniEstudianteAsignado(),asignacion.getIdAsignacion());
	}

	public void deleteAsignacion(int idAsignacion) {
		this.jdbcTemplate.update(
		        "delete from Asignacion where idasignacion = ?", idAsignacion);
	}

	public void addAsignacion(Asignacion asignacion) {
		this.jdbcTemplate.update(
				"insert into Asignacion (fechaPropuesta,fechaAceptacion,fechaRechazo,fechatraspasoiglu,"
				+ "estado,idofertaproyecto,dniprofesorasignado,dniestudianteasignado) values(?, ?, ?, ?, ?, ?, ?, ?)", 
				asignacion.getFechaPropuesta(),
				asignacion.getFechaAceptacion(),asignacion.getFechaRechazo(),asignacion.getFechaTraspasoIGLU(),
				asignacion.getEstado(),asignacion.getIdOfertaProyecto(),asignacion.getDniProfesorAsignado(),
				asignacion.getDniEstudianteAsignado());
	}
	
	

}
