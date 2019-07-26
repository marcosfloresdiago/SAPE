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

@Repository
public class OfertaNominativaDao {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class OfertaNominativaMapper implements RowMapper<OfertaNominativa> {
		
		public OfertaNominativa mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			OfertaNominativa oferta = new OfertaNominativa();
			oferta.setIdOferta(rs.getInt("idoferta"));
			oferta.setDniAlumno(rs.getString("dniestudiante"));
			oferta.setNombreAlumno(rs.getString("nombreestudiante"));
			
			
			return oferta;
		}
	}
	
	public List<OfertaNominativa> getOfertasNominativas(){
		return this.jdbcTemplate.query("select * from ofertanominativa ORDER BY idoferta;",new OfertaNominativaMapper());
	}
	
	public OfertaNominativa OfertaNominativa(int idOferta){
		return this.jdbcTemplate.queryForObject("select * from ofertanominativa where idoferta=?", new Object[] {idOferta},new OfertaNominativaMapper());
		
	}
	
	public List<OfertaNominativa> getOfertaNominativasEmpresa(String cif){
		return this.jdbcTemplate.query("select oferNo.idOferta,oferNo.nombreestudiante,oferNo.dniestudiante from ofertanominativa as oferNo  JOIN ofertaproyecto as oferPro on oferPro.idoferta = oferNo.idoferta "
				+ " JOIN Estancia as est on est.idestancia = oferPro.idestancia  where est.cifempresa=? ORDER BY idoferta", new Object[] {cif}, new OfertaNominativaMapper());
		
	}
	
	public void updateOfertaNominativa(OfertaNominativa oferta) {
		this.jdbcTemplate.update(
				"update OfertaNominativa set idoferta=? , dniestudiante where idoferta = ?", 
				oferta.getIdOferta(),oferta.getDniAlumno(),oferta.getIdOferta(),oferta.getNombreAlumno());
	}

	public void deleteOfertaNominativa(int idOferta) {
		this.jdbcTemplate.update(
		        "delete from OfertaNominativa where idoferta = ?", idOferta);
	}

	public void addOfertaNominativa(OfertaNominativa oferta) {
		this.jdbcTemplate.update(
				"insert into OfertaNominativa(idoferta,dniestudiante,nombreestudiante) values(?,?,?)", 
				oferta.getIdOferta(),oferta.getDniAlumno(),oferta.getNombreAlumno());
	}
	
    public List<OfertaNominativa> getOfertaNominativasEstudiante(String dniEstudiante) {
        return this.jdbcTemplate.query("select * from ofertanominativa where dniestudiante=? ORDER BY idoferta; ", new Object[] {dniEstudiante}, new OfertaNominativaMapper());
        
    }


}
