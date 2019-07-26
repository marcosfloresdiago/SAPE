package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.trabajoFinal.model.Estancia;


public class EstanciaDao {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	
	}
	
	private static final class EstanciaMapper implements RowMapper<Estancia> {
		
		public Estancia mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Estancia estancia = new Estancia();
			
			estancia.setIdEstancia(rs.getInt("idestancia"));
			estancia.setCifEmpresa(rs.getString("cifempresa"));
			estancia.setPersonaContacto(rs.getString("personacontacto"));
			estancia.setEmailPersonaContacto(rs.getString("emailpersonacontacto"));
			estancia.setDescripcion(rs.getString("descripcion"));
			
			return estancia;
			
		}
	}
	
	public List<Estancia> getEstancias(){
		return this.jdbcTemplate.query("select * from estancia ORDER BY idestancia;",new EstanciaMapper());
	}
	
	public List<Estancia> getEstanciasEmpresa(String cifempresa){
		return this.jdbcTemplate.query("select * from estancia where cifempresa=? ORDER BY idestancia ;",new Object[] {cifempresa},new EstanciaMapper());
	}
	
	public Estancia getEstancia(int idEstancia){
		return this.jdbcTemplate.queryForObject("select * from Estancia where idestancia=?;", new Object[] {idEstancia},new EstanciaMapper());
		
	}
	
	
	
	
	public void updateEstancia(Estancia estancia) {
		this.jdbcTemplate.update(
				"update Estancia set idestancia=?, cifempresa=?, personacontacto=?, emailpersonacontacto=?, descripcion= ? where idestancia = ?", 
				estancia.getIdEstancia(),estancia.getCifEmpresa(),estancia.getPersonaContacto(),estancia.getEmailPersonaContacto(),estancia.getDescripcion(),estancia.getIdEstancia());
	}

	public void deleteEstancia(int idEstancia) {
		this.jdbcTemplate.update(
		        "delete from Estancia where idestancia = ?", idEstancia);
	}

	public void addEstancia(Estancia estancia) {
		this.jdbcTemplate.update(
				"insert into Estancia (cifempresa,personacontacto,emailpersonacontacto,descripcion) values( ?, ?, ?, ?)", 
				estancia.getCifEmpresa(),estancia.getPersonaContacto(),estancia.getPersonaContacto(),estancia.getDescripcion());
	}
	



}
