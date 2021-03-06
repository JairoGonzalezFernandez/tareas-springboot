package com.formacionspringboot.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionspringboot.apirest.entity.Articulo;
import com.formacionspringboot.apirest.entity.Cliente;
import com.formacionspringboot.apirest.entity.Compra;
import com.formacionspringboot.apirest.service.ArticuloService;
import com.formacionspringboot.apirest.service.ClienteService;
import com.formacionspringboot.apirest.service.CompraService;

@RestController
@RequestMapping("/api")
public class CompraRestController {
	
	@Autowired
	CompraService servicio;
	@Autowired
	ArticuloService servicioArticulo;
	@Autowired
	ClienteService servicioCliente;
	
	@GetMapping({"/compras"})
	public List<Compra> index(){
		return servicio.findAll();
	}
	

	@GetMapping("/compras/{id}")
	public ResponseEntity<?> findCompraById(@PathVariable Long id)
	{
		Compra compra = null;
		Map<String,Object> response = new HashMap<>();
		
		try
		{
			compra = servicio.findById(id);		
		}
		catch(DataAccessException e) 
		{
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(compra == null)
		{
			response.put("mensaje", "La compra ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Compra>(compra, HttpStatus.OK);
	}
	

	@PostMapping("/compra")
	public ResponseEntity<?> saveCompra(@RequestBody Compra compra)
	{
		Compra compraNew = null;
		Map<String,Object> response = new HashMap<>();
		
		try
		{
			compraNew = servicio.save(compra);		
		}
		catch(DataAccessException e) 
		{
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La compra ha sido creada con ??xito");
		response.put("compra", compraNew);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/compra/{id}")
	public ResponseEntity<?> updateCompra(@RequestBody Compra compra, @PathVariable Long id)
	{
		Compra compraActual = servicio.findById(id);	
		Map<String,Object> response = new HashMap<>();
		if(compraActual == null)
		{
			response.put("mensaje", "No se puede editar la compra, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}		
		try 
		{		
			compraActual.setFecha(compra.getFecha());
			compraActual.setArticulo(compra.getArticulo());
			compraActual.setCliente(compra.getCliente());			
			servicio.save(compraActual);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La compra ha sido actualizado con ??xito");
		response.put("compra", compraActual);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/compra/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long id)
	{
		Compra compraEliminada = servicio.findById(id);	
		Map<String,Object> response = new HashMap<>();
		if(compraEliminada == null)
		{
			response.put("mensaje", "No se puede eliminar la compra, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}		
		try 
		{
			servicio.delete(id);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La compra ha sido eliminado con ??xito");
		response.put("compra", compraEliminada);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	

	
	@GetMapping("/compras/clientes")
	public List<Cliente>listarClientes()
	{
		return servicio.findAllClientes();
	}
	@GetMapping("/compras/articulos")
	public List<Articulo>listarArticulos()
	{
		return servicio.findAllArticulos();
	}
}
