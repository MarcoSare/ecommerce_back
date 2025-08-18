package com.ecommerce.commons.mappers;

public abstract class CommonMapper <RQ, RS, E>{
	
public abstract RS entityToResponse(E entity);
	
	public abstract E requestToEntity(RQ request);

}
