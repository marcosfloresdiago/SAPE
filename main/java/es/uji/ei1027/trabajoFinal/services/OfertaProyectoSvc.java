package es.uji.ei1027.trabajoFinal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.uji.ei1027.trabajoFinal.dao.OfertaProyectoDao;
import es.uji.ei1027.trabajoFinal.model.OfertaProyecto;



public class OfertaProyectoSvc implements OfertaProyectoService{
	
	@Autowired
	OfertaProyectoDao ofertaProyectoDao;

	@Override
	public List<OfertaProyecto> getOfertas() {
		 return ofertaProyectoDao.getOfertasProyecto();
	}

}
