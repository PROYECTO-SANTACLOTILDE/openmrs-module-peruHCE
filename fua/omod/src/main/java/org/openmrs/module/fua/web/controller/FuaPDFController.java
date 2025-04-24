package org.openmrs.module.fua.web.controller;

import org.openmrs.module.fua.FuaPDF;
import org.openmrs.module.fua.api.FuaPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/fua/fuapdf")
public class FuaPDFController {
	
	@Autowired
	private FuaPDFService fuaPDFService;
	
	@GetMapping
	public List<FuaPDF> getAll() {
		return fuaPDFService.getAllFuaPDFs();
	}
	
	@GetMapping("/{id}")
	public FuaPDF getById(@PathVariable("id") Integer id) {
		return fuaPDFService.getFuaPDF(id);
	}
	
	@PostMapping
	public FuaPDF create(@RequestBody FuaPDF fuaPDF) {
		return fuaPDFService.saveFuaPDF(fuaPDF);
	}
	
	@PutMapping("/{id}")
	public FuaPDF update(@PathVariable("id") Integer id, @RequestBody FuaPDF fuaPDF) {
		fuaPDF.setId(id);
		return fuaPDFService.saveFuaPDF(fuaPDF);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		FuaPDF fuaPDF = fuaPDFService.getFuaPDF(id);
		if (fuaPDF != null) {
			fuaPDFService.purgeFuaPDF(fuaPDF);
		}
	}
}
