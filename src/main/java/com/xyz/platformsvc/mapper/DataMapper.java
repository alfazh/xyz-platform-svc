package com.xyz.platformsvc.mapper;

public interface DataMapper <T,U> {
	
	public T toEntity(U u);
	
	public U toRestObj(T t);

}
