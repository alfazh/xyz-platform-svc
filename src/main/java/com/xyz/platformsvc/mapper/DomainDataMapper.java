package com.xyz.platformsvc.mapper;

public interface DomainDataMapper <T,U> {
	
	public T toEntityObj(U u);
	
	public U toRestObj(T t);

}
