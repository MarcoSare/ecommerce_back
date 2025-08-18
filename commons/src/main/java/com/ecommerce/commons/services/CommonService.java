package com.ecommerce.commons.services;

import java.util.List;

public interface CommonService <RQ, RS>{
	
	List<RS> listar();
	
	RS obtenerPorId(Long id);
	
	RS insertar(RQ request);
	
	RS actualizar(RQ request, Long id);
	
	RS eliminar(Long id);


}
