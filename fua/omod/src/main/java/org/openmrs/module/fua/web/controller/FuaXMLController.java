package org.openmrs.module.fua.web.controller;

import org.openmrs.module.fua.FuaXML;
import org.openmrs.module.fua.api.FuaXMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/fua/fuaxml")
public class FuaXMLController {
	
	@Autowired
	private FuaXMLService fuaXMLService;
	
	@GetMapping
	public List<FuaXML> getAll() {
		return fuaXMLService.getAllFuaXMLs();
	}
	
	@GetMapping("/{id}")
	public FuaXML getById(@PathVariable("id") Integer id) {
		return fuaXMLService.getFuaXML(id);
	}
	
	@PostMapping
	public FuaXML create(@RequestBody FuaXML fuaXML) {
		return fuaXMLService.saveFuaXML(fuaXML);
	}
	
	@PutMapping("/{id}")
	public FuaXML update(@PathVariable("id") Integer id, @RequestBody FuaXML fuaXML) {
		fuaXML.setId(id);
		return fuaXMLService.saveFuaXML(fuaXML);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		FuaXML fuaXML = fuaXMLService.getFuaXML(id);
		if (fuaXML != null) {
			fuaXMLService.purgeFuaXML(fuaXML);
		}
	}
}
