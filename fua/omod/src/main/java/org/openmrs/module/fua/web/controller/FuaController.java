package org.openmrs.module.fua.web.controller;

import org.openmrs.module.fua.Fua;
import org.openmrs.module.fua.api.FuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/fua")
public class FuaController {
	
	@Autowired
	private FuaService fuaService;
	
	@GetMapping
	public List<Fua> getAll() {
		return fuaService.getAllFuas();
	}
	
	@GetMapping("/{id}")
	public Fua getById(@PathVariable("id") Integer id) {
		return fuaService.getFua(id);
	}
	
	@PostMapping
	public Fua create(@RequestBody Fua fua) {
		return fuaService.saveFua(fua);
	}
	
	@PutMapping("/{id}")
	public Fua update(@PathVariable("id") Integer id, @RequestBody Fua fua) {
		fua.setId(id);
		return fuaService.saveFua(fua);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		Fua fua = fuaService.getFua(id);
		if (fua != null) {
			fuaService.purgeFua(fua);
		}
	}
}
