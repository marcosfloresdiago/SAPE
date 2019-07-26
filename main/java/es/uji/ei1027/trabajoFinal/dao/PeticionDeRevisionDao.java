package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.PeticionRevision;


@Repository
public class PeticionDeRevisionDao {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class PeticionRevisionMapper implements RowMapper<PeticionRevision> {
		
		public PeticionRevision mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			PeticionRevision peticion = new PeticionRevision();
			peticion.setIdPeticion(rs.getInt("idpeticion"));
			peticion.setIdOfertaProyecto(rs.getInt("idofertaproyecto"));
			peticion.setFecha(rs.getDate("fecha"));
			peticion.setTextoPeticion(rs.getString("textopeticion"));
			return peticion;
		}
	}
	
	public List<PeticionRevision> getPeticionesRevisiones(){
		return this.jdbcTemplate.query("select * from PeticionRevision ORDER BY idpeticion;",new PeticionRevisionMapper());
	}
	
	public PeticionRevision getPeticionRevision(int idPeticion){
		return this.jdbcTemplate.queryForObject("select * from PeticionRevision where idpeticion=?", new Object[] {idPeticion},new PeticionRevisionMapper());
		
	}
	public void addPeticionRevision(PeticionRevision peticion){
		
		
		this.jdbcTemplate.update(
				"insert into PeticionRevision (idofertaproyecto,fecha,textopeticion) values(?, ?,?)",
				peticion.getIdOfertaProyecto(),peticion.getFecha(),peticion.getTextoPeticion());
		
	}	
	public void deletePeticionRevision(int idPeticion) {
		this.jdbcTemplate.update(
		        "delete from PeticionRevision where idpeticion = ?", idPeticion);
	}
	
	public void updatePeticionRevision(PeticionRevision peticion) {
		this.jdbcTemplate.update(
				"update PeticionRevision set idpeticion=?, idofertaproyecto=?, fecha=?, textopeticion=?,", 
				peticion.getIdPeticion(),peticion.getIdOfertaProyecto(),peticion.getFecha(),peticion.getTextoPeticion(),peticion.getIdPeticion());
	}
}
