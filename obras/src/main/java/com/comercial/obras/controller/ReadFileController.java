package com.comercial.obras.controller;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comercial.obras.entity.Obras;
import com.comercial.obras.service.ReadFileService;

@Controller
@RequestMapping("/detalle")
public class ReadFileController {

	@Autowired
	private ReadFileService readFileService;
	
	String ciudad;
	String zona;
	String sector;
	String estado;
	
	
	@GetMapping(value ="/buscar")
	public String findAllCompletos(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		ciudad = (params.get("ciudad") != null || !"".equals(params.get("ciudad"))) ? (String.valueOf(params.get("ciudad").toString())) :null;
		zona = (params.get("zona") != null || !"".equals(params.get("zona"))) ? (String.valueOf(params.get("zona").toString())) :null;
		sector = (params.get("sector") != null || !"".equals(params.get("sector"))) ? (String.valueOf(params.get("sector").toString())) :null;
		estado = (params.get("estado") != null || !"".equals(params.get("estado"))) ? (String.valueOf(params.get("estado").toString())) :null;
		
		if("".equals(ciudad)) {
			ciudad=null;
		}else {
			ciudad=ciudad.trim();
		}
		
		if("".equals(zona)) {
			zona=null;
		}else {
			zona=zona.trim();
		}
		
		if("".equals(sector)) {
			sector=null;
		}else {
			sector=sector.trim();
		}
		
		if("".equals(estado)) {
			estado=null;
		}else {
			estado=estado.trim();
		}
		
		
		
		PageRequest pageRequest = PageRequest.of(page, 100);
		
		//Page<Obras> listaObras = readFileService.getAll(pageRequest);
		//Page<Obras> listaObras = readFileService.buscarDatos(pageRequest,ciudad,zona,sector,estado);
		
		Page<Obras> listaObras = readFileService.buscarByCiudad(pageRequest,ciudad,zona,sector,estado);
		
		int totalPage = listaObras.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras", listaObras.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("ciudad",ciudad);
		model.addAttribute("zona",zona);
		model.addAttribute("sector",sector);
		return "obras";
	}
	

	@GetMapping(value="/index1")
	public String home(Model model){
		model.addAttribute("obra", new Obras());
		List<Obras> obras= readFileService.findAll();
		model.addAttribute("obras", obras);	
		return "obras";
	}
	
	@GetMapping(value ="/obraLista")
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 100);
		
		Page<Obras> listaObras = readFileService.getAll(pageRequest);
		
		int totalPage = listaObras.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras", listaObras.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		return "obras";
	}
	
	
	@GetMapping(value ="/buscarDato")
	public String findAllDatos(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 100);
		
		Page<Obras> listaObras = readFileService.getAll(pageRequest);
		
		int totalPage = listaObras.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras", listaObras.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		return "/obras";
	}
	
	
	@PostMapping(value="/fileupload")
	public String uploadFile(@ModelAttribute Obras obras, RedirectAttributes redirectAttributes) {
		
		boolean isFlag= readFileService.saveDataFromUploadfile(obras.getFile());
		if (isFlag) {
			
			redirectAttributes.addFlashAttribute("successmessage", "Archivo cargado exitosamente");			
		}else {
			redirectAttributes.addFlashAttribute("errormessage", "No se pudo cargar el archivo, intente de nuevo");
		}
		
		return "redirect:/detalle/obraLista";	
		
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getZona() {
		return zona;
	}


	public void setZona(String zona) {
		this.zona = zona;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	

	/*@GetMapping("/informacion/{idObr}")
	public String update(@PathVariable Long idObr ,Model model) {	
		
		Optional<Obras>obra=readFileService.listarId(idObr);
		model.addAttribute("obra", obra);
		
		return "registro_info";
	}
	*/
	
	
	
}
