package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.EstadoOferta;


@Repository
public class EstadosOfertaDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class EstadosOfertaMapper implements RowMapper<EstadoOferta> {
		
		public EstadoOferta mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			EstadoOferta estado = new EstadoOferta();
			
			estado.setEstado(rs.getString("tipo"));
			
			return estado;
			
		}
	}
	

	public List<EstadoOferta>getEstados(){
		
		return this.jdbcTemplate.query("select * from estadooferta",new EstadosOfertaMapper());
		
	}
	
	

}
