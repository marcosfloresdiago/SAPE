package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.OfertaNominativa;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;


@Repository
public class OfertaProyectoDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class OfertaProyectoMapper implements RowMapper<OfertaProyecto> {
		
		public  OfertaProyecto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			OfertaProyecto  ofertaProyecto = new OfertaProyecto();
			
			ofertaProyecto.setIdOferta(rs.getInt("idoferta"));
	
			ofertaProyecto.setTarea(rs.getString("tarea"));
			
			ofertaProyecto.setObjetivo(rs.getString("objetivo"));
	
			ofertaProyecto.setEstado(rs.getString("estado"));
		
			ofertaProyecto.setFechaAlta(rs.getDate("fechaAlta"));
		
			ofertaProyecto.setFechaUltimoCambio(rs.getDate("fechaUltimoCambio"));

			ofertaProyecto.setItinerario(rs.getString("itinerario"));
			
			ofertaProyecto.setIdEstancia(rs.getInt("idEstancia"));
			
			ofertaProyecto.setVisible(rs.getBoolean("visible"));
			
			return ofertaProyecto;
			
		}
	}
	
	public List<OfertaProyecto>getOfertasProyecto(){
		
		return this.jdbcTemplate.query("select * from ofertaproyecto;",new OfertaProyectoMapper());
		
	}
	
	public List<OfertaProyecto> getOfertasProyectoEmpresa(String cif){
		return this.jdbcTemplate.query("select * from OfertaProyecto JOIN Estancia on ofertaproyecto.idestancia = estancia.idestancia   where cifempresa=?", new Object[] {cif},new OfertaProyectoMapper());
		
	}
	
	public List<OfertaProyecto> getOfertaProyectoSinNominativaEmpresa(String cif){
		return this.jdbcTemplate.query("select oferPro.* from ofertaproyecto as oferPro LEFT OUTER JOIN ofertanominativa as oferNo on oferPro.idoferta = oferNo.idoferta "
				+ " JOIN Estancia as est on est.idestancia = oferPro.idestancia  where est.cifempresa=? and  oferNo.idOferta IS  NULL ORDER BY idoferta", new Object[] {cif}, new OfertaProyectoMapper());
		
	}
	
	public List<OfertaProyecto> getOfertaProyectoSoloNominativaEmpresa(String cif){
		return this.jdbcTemplate.query("select oferPro.* from ofertaproyecto as oferPro LEFT OUTER JOIN ofertanominativa as oferNo on oferPro.idoferta = oferNo.idoferta "
				+ " JOIN Estancia as est on est.idestancia = oferPro.idestancia  where est.cifempresa=? and  oferNo.idOferta IS NOT NULL ORDER BY idoferta", new Object[] {cif}, new OfertaProyectoMapper());
		
	}
	
	
	public OfertaProyecto getOfertaProyecto(int idoferta){
		return this.jdbcTemplate.queryForObject("select * from OfertaProyecto  where idoferta=?", new Object[] {idoferta},new OfertaProyectoMapper());
		
	}
	public void addOfertaProyecto(OfertaProyecto oferta) {
		this.jdbcTemplate.update(
				"insert into ofertaproyecto  (tarea,objetivo,estado,fechaAlta,fechaUltimoCambio,itinerario,idEstancia,visible) values(?, ?, ?, ?,?,?,?,?)", 
				oferta.getTarea(), oferta.getObjetivo(),
				oferta.getEstado(),oferta.getFechaAlta(), oferta.getFechaUltimoCambio(),
				oferta.getItinerario(),oferta.getIdEstancia(),oferta.getVisible());
	}
	public void updateOfertaProyecto(OfertaProyecto oferta) {
		this.jdbcTemplate.update(
				"update ofertaproyecto set tarea=?, objetivo=?, estado=?, fechaAlta=?,fechaUltimoCambio=?,itinerario=?,visible=? where idoferta = ?", 
				oferta.getTarea(), oferta.getObjetivo(),oferta.getEstado(), 
				oferta.getFechaAlta(), oferta.getFechaUltimoCambio(), oferta.getItinerario(),
				oferta.getVisible(),oferta.getIdOferta());
	}

	public void deleteOfertaProyecto(int idEstancia) {
		this.jdbcTemplate.update(
		        "delete from ofertaProyecto where idEstancia = ?", idEstancia);
	}
	public List<OfertaProyecto>getOfertasProyectoAceptadas(){
		
		return this.jdbcTemplate.query("select * from ofertaproyecto where estado='Aceptada';",new OfertaProyectoMapper());
		
	}
	
	public List<OfertaProyecto>getOfertasProyectoAceptadasItinerario(String itinerario){
		
		return this.jdbcTemplate.query("select * from ofertaproyecto where estado='Visible para Alumnos' and itinerario=?;",new Object[] {itinerario},new OfertaProyectoMapper());
		
	}
	
	public List<OfertaProyecto>getOfertasProyectoNoVisibles(){
		
		return this.jdbcTemplate.query("select * from ofertaproyecto where visible=FALSE and estado='Aceptada';",new OfertaProyectoMapper());
		
	}
	
}
