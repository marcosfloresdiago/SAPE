package es.uji.ei1027.trabajoFinal.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.trabajoFinal.model.Empresa;


@Repository
public class EmpresaDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	private static final class EmpresaMapper implements RowMapper<Empresa> {
		
		public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Empresa empresa = new Empresa();
			empresa.setNombre(rs.getString("nombre"));
			empresa.setCif(rs.getString("cif"));
			empresa.setDomicilio(rs.getString("domicilio"));
			empresa.setTelefonoPrincipal(rs.getString("telefonoPrincipal"));
			empresa.setEmail(rs.getString("email"));
			
			return empresa;
		}
	}
	
	public List<Empresa> getEmpresas(){
		return this.jdbcTemplate.query("select * from empresa ORDER BY cif;",new EmpresaMapper());
	}
	
	public Empresa getEmpresa(String cif){
		return this.jdbcTemplate.queryForObject("select * from empresa where cif=?", new Object[] {cif},new EmpresaMapper());
		
	}
	
	public void updateEmpresa(Empresa empresa) {
		this.jdbcTemplate.update(
				"update Empresa set nombre=?, cif=?, domicilio=?, telefonoPrincipal=?, email=? where cif = ?", 
				empresa.getNombre(), empresa.getCif(), 
				empresa.getDomicilio(), empresa.getTelefonoPrincipal(), empresa.getEmail(), empresa.getCif());
	}

	public void deleteEmpresa(String cif) {
		this.jdbcTemplate.update(
		        "delete from empresa where cif = ?", cif);
	}

	public void addEmpresa(Empresa empresa) {
		this.jdbcTemplate.update(
				"insert into Empresa (nombre,cif,domicilio,telefonoPrincipal, email) values(?, ?, ?, ?, ?)", 
				empresa.getNombre(), empresa.getCif(), 
				empresa.getDomicilio(), empresa.getTelefonoPrincipal(), empresa.getEmail());
	}
	
	public Empresa getEmpresaOferta(int idEstancia) {
		
		return this.jdbcTemplate.queryForObject("select * from empresa join estancia on estancia.cifempresa = empresa.cif where idestancia=?", new Object[] {idEstancia},new EmpresaMapper());
		
		
	}

}
