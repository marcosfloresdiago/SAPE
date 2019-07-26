package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import es.uji.ei1027.trabajoFinal.model.EstadosAsignacion;

@Repository
public class EstadosAsignacionDao {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class EstadosAsignacionMapper implements RowMapper<EstadosAsignacion> {
		
		public EstadosAsignacion mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			EstadosAsignacion estado = new EstadosAsignacion();
			
			estado.setEstado(rs.getString("tipo"));
			
			return estado;
			
		}
	}
	

	public List<EstadosAsignacion>getEstados(){
		
		return this.jdbcTemplate.query("select * from estadoasignacion",new EstadosAsignacionMapper());
		
	}

}
