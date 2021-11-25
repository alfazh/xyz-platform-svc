package com.xyz.platformsvc.mapper;

public interface DataMapper <T,U> {
	
	public T toEntityObj(U u);
	
	public U toRestObj(T t);

}
