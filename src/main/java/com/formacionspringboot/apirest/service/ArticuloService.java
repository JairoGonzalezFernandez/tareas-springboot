package com.formacionspringboot.apirest.service;

import java.util.List;

import com.formacionspringboot.apirest.entity.Articulo;



public interface ArticuloService {
	
	public List<Articulo> findAll();
	
	public Articulo findByNombre(String nombre);
	
	public Articulo save(Articulo articulo);
	
	public void delete(Long id);

	public Articulo findById(Long id);

}
