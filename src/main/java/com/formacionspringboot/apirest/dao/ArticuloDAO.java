package com.formacionspringboot.apirest.dao;

import org.springframework.stereotype.Repository;

import com.formacionspringboot.apirest.entity.Articulo;
import com.formacionspringboot.apirest.entity.Cliente;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ArticuloDAO  extends CrudRepository<Articulo,Long> {
	
	Articulo findByNombre(String nombre);

}
