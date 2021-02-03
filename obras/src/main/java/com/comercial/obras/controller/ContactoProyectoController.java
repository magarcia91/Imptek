package com.comercial.obras.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comercial.obras.entity.ContactoProyecto;
import com.comercial.obras.entity.Obras;
import com.comercial.obras.report.ContactoProyImptekReport;
import com.comercial.obras.report.ObrasImptekCerradasReport;
import com.comercial.obras.repository.ContactoProyectoRepository;
import com.comercial.obras.service.ContactoProyectoService;
import com.comercial.obras.service.ReadFileService;
import com.comercial.obras.service.ReportService;

import net.sf.jasperreports.engine.JRException;


@Controller
@RequestMapping("/contacto")
public class ContactoProyectoController {
	
	/*REPORTE */
	private final ReportService reportService;
	private final ContactoProyectoRepository contProyRepository;
	
	@Autowired
	public ContactoProyectoController(final ContactoProyectoRepository contProyRepository, final ReportService reportService){
		this.contProyRepository = contProyRepository;
		this.reportService = reportService;
	}
		
	/*FIN REPORTE*/	
	
	@Autowired
	private ReadFileService readFileService;
	
	@Autowired
	private ContactoProyectoService contactoService;
	
	@Autowired
	private ReadFileService readService;
	
	
	@Autowired
	private ServletContext context;
	
	
	@GetMapping("/listar")
	public String listar(Model model) {		
		List<ContactoProyecto>contactos = contactoService.listar();	
		model.addAttribute("contactos", contactos);
		return "listar_proyecto";
	}
	
	@GetMapping("/new")
	public String agregar(Model model){
		
		List<Obras> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);
		List<Obras> tests = readService.findAll();
		model.addAttribute("tests", tests);
		model.addAttribute("contacto", new ContactoProyecto());
		return "registro_contacto";
		
	}
	
	@GetMapping("/new/{obraId}")
	public String agregarContacto(@PathVariable Long obraId, Model model){		
		/*List<Obras> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);
		List<Obras> tests = readService.findAll();
		model.addAttribute("tests", tests);
		model.addAttribute("contacto", new ContactoProyecto());*/
		Optional<Obras> obra=readFileService.findByObraId(obraId);
		Obras obraa=obra.get();
		
		//model.addAttribute("obra", obraa);
		ContactoProyecto contac=new ContactoProyecto();
		
		if(obraa.getContacto()!=null) {
			contac.setNombreContact(obraa.getContacto().getNombreContact());
			contac.setTelefonoContact(obraa.getContacto().getTelefonoContact());
			contac.setCorreoContact(obraa.getContacto().getCorreoContact());
			contac.setContactoAvanceObr(obraa.getContacto().getContactoAvanceObr());
		}
		
		contac.setObraSel(obraa);
		contac.setIdObr(obraa.getIdObr());
		/***/
		contac.setContactoProyectoObr(obraa.getNombreObr());
		contac.setContactoCiudadObr(obraa.getCiudadObr());
		contac.setContactoConstructoraObr(obraa.getConstructoraObr());
		contac.setContactoSectorObr(obraa.getSectorObr());
		contac.setContactoStatusObr(obraa.getStatusObr());
		contac.setContactoZonaObr(obraa.getZonaObr());
		/***/
		model.addAttribute("contacto", contac);
		return "registro_contacto";
	}
	
	@PostMapping("save")
	public String save(@Valid ContactoProyecto contacto, Model model) {
		
		System.out.println("Id contacto del proyecto 1-------"+contacto.getIdContact()); 
		
		Optional<Obras> obra=readFileService.findByObraId(contacto.getIdObr());
		Obras obraa=obra.get();		
		contacto.setIdObr(obraa.getIdObr());
		
		System.out.println("Id contacto del proyecto 2-------"+contacto.getIdContact()); 
		contactoService.save(contacto);	
		
		obraa.setContacto(contacto);
		System.out.println("Id obra del proyecto -------"+obraa.getIdObr()); 
		readFileService.save(obraa);
		
		return "redirect:/contacto/contactoListar";
		
	}
	
	@GetMapping("/editar/{idContact}")
	public String update(@PathVariable Long idContact ,Model model) {	
		
		List<Obras> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);
		List<Obras> tests = readService.findAll();
		model.addAttribute("tests", tests);
		Optional<ContactoProyecto>con=contactoService.listarId(idContact);
		model.addAttribute("contacto", con);		
		return "registro_contacto";
	}
	
	@GetMapping("/eliminar/{idContact}")
	public String delete(Model model,@PathVariable Long idContact) {
	
		contactoService.delete(idContact);
		return "redirect:/contacto/listar";
	}


	@GetMapping("/avance")
	public String getAvance(@RequestParam Map<String, Object> params,Model model) {
		
		List<ContactoProyecto>avance = contactoService.findByAvance();
	    model.addAttribute("avance", avance);
			
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		
		Page<ContactoProyecto> listaContacto = contactoService.getAll(pageRequest);
		
		int totalPage = listaContacto.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras cerradas", listaContacto.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		

		return "obras_cerradas";
	}

	@GetMapping(value ="/contactoListar")
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		
		Page<ContactoProyecto> listaContacto = contactoService.getAll(pageRequest);
		
		int totalPage = listaContacto.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("contactos", listaContacto.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		return "listar_proyecto";
	}
	
	/*METODO REPORTE */
	
	@GetMapping(value = "/contactoReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getContactoReportPdf(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ContactoProyImptekReport report = new ContactoProyImptekReport((Collection<ContactoProyecto>) contProyRepository.findAll());
		final byte[] data = reportService.getReportPdf(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=contactoReport.pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}
	
	/*FIN REPORTE*/
	
	
	/*METODO REPORTE */
	
	@GetMapping(value = "/obrasCerradasReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getObrasCerradasReportPdf(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ObrasImptekCerradasReport report = new ObrasImptekCerradasReport((Collection<ContactoProyecto>) contProyRepository.findByAvance());
		final byte[] data = reportService.getReportPdf(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=obrasCerradasReport.pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}
	
	/*FIN REPORTE*/
		
}

/*	
@GetMapping("/informacion/{contactoProyectoObr}")
public String verInfo(@PathVariable String contactoProyectoObr ,Model model) {	
	
	List<Object[]> info = contactoRepository.findBycontactoProyectoObr(contactoProyectoObr);		
	model.addAttribute("informacion", info);
		
	return "registro_info2";
}*/
	
/*	@GetMapping("/informacion/{idContact}")
	public String verInfo(@PathVariable Long idContact ,Model model) {	
				List<Obras> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);
		List<Obras> tests = readService.findAll();
		model.addAttribute("tests", tests);
		Optional<ContactoProyecto>con=contactoService.listarId(idContact);
		model.addAttribute("contacto", con);
		
		return "ver_informacion";
	}*/
	
	/*	@GetMapping("/HTML")
	public String html(Model model) {		
		 List<Obras> test = new ArrayList<>();
	     //Creamos un objeto HashSet
	        HashSet hs = new HashSet();	       
	     //Lo cargamos con los valores del array, esto hace quite los repetidos
	        hs.addAll(test);	       
	     //Limpiamos el array
	        test.clear();	       
	     //Agregamos los valores sin repetir
	        test.addAll(hs);	        
		 model.addAttribute("test", test);
		 List<Obras> tests = readService.findAll();
		 model.addAttribute("obra", new Obras());
		 model.addAttribute("tests", tests);		
		return "ejHTML";
	}
	*/
	
/*@GetMapping(value="/createpdf")		
public void createPdf(HttpServletRequest request, HttpServletResponse response) {
		
List<ContactoProyecto>contactos = contactoService.listar();	
boolean isFlag = contactoService.createPdf(contactos,context,request,response);
	if (isFlag) {
		String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"contactos imptek"+".pdf");
		filedownload(fullPath,response,"contactos imptek.pdf");
	}
}

private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
	File file = new File(fullPath);
	final int BUFFER_SIZE = 4096;
	if (file.exists()) {
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			String mimeType = context.getMimeType(fullPath);
			//response.setContentType(mimeType);
			//response.setHeader("content-disposition", "attachment: filename="+fileName);
			
			InputStream inputStreamIO = new FileInputStream(new File(fullPath)); // load the file
		    IOUtils.copy(inputStreamIO, response.getOutputStream());
		    
		    response.setContentType("application/pdf");
		    //response.setCharacterEncoding("ISO-8859-1");
		    response.setHeader("Content-disposition", " filename=" + fileName);
			
			
			OutputStream outputStream = response.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer))!= -1) {
				outputStream.write(buffer,0,bytesRead);
			}
				
			inputStream.close();
			//outputStream.close();
			file.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
}*/

