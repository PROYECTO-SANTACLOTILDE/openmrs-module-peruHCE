package org.openmrs.module.fua.web.controller;

import org.openmrs.module.fua.FormatoFua;
import org.openmrs.module.fua.api.FormatoFuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/fua/formatofua")
public class FormatoFuaController {
	
	@Autowired
	private FormatoFuaService formatoFuaService;
	
	// Obtener todos los registros
	@GetMapping
	public List<FormatoFua> getAll() {
		return formatoFuaService.getAllFormatoFuas();
	}
	
	// Obtener un registro por ID
	@GetMapping("/{id}")
	public FormatoFua getById(@PathVariable("id") Integer id) {
		return formatoFuaService.getFormatoFua(id);
	}
	
	// Crear un nuevo registro
	@PostMapping
	public FormatoFua create(@RequestBody FormatoFua formatoFua) {
		return formatoFuaService.saveFormatoFua(formatoFua);
	}
	
	// Actualizar un registro existente
	@PutMapping("/{id}")
	public FormatoFua update(@PathVariable("id") Integer id, @RequestBody FormatoFua formatoFua) {
		formatoFua.setId(id);
		return formatoFuaService.saveFormatoFua(formatoFua);
	}
	
	// Eliminar un registro
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		FormatoFua formatoFua = formatoFuaService.getFormatoFua(id);
		if (formatoFua != null) {
			formatoFuaService.purgeFormatoFua(formatoFua);
		}
	}
}
