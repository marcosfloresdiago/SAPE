package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.Itinerario;

@Repository
public class ItinerarioDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class ItinerarioMapper implements RowMapper<Itinerario> {
		
		public Itinerario mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Itinerario itinerario = new Itinerario();
			
			itinerario.setNombre(rs.getString("tipo"));
			
			return itinerario;
		}
	}
	public List<Itinerario> getItinerarios(){
		return this.jdbcTemplate.query("select * from itinerario;",new ItinerarioMapper());
	}

}
