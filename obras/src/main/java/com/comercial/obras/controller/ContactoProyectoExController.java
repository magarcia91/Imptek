package com.comercial.obras.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comercial.obras.entity.ContactoProyecto;
import com.comercial.obras.entity.ContactoProyectoEx;
import com.comercial.obras.entity.Obras;
import com.comercial.obras.entity.ObrasEx;
import com.comercial.obras.report.ContactoProyExternasReport;
import com.comercial.obras.report.ObrasExternasCerradasReport;
import com.comercial.obras.repository.ContProyExRepository;
import com.comercial.obras.service.ContProyExService;
import com.comercial.obras.service.ObrasExService;
import com.comercial.obras.service.ReportService;

import net.sf.jasperreports.engine.JRException;


@Controller
@RequestMapping("/contacto_ex")
public class ContactoProyectoExController {
	
	/*REPORTE */
	private final ReportService reportService;
	private final ContProyExRepository contProyExRepository;
	
	@Autowired
	public ContactoProyectoExController(final ContProyExRepository contProyExRepository, final ReportService reportService){
		this.contProyExRepository = contProyExRepository;
		this.reportService = reportService;
	}
		
	/*FIN REPORTE*/	
	
	@Autowired
	private ContProyExService contExService;
	
	@Autowired
	private ObrasExService obrExService;

	@Autowired
	private ServletContext context,context1;

	
	@GetMapping("/listar")
	public String listar(Model model) {	
		/*List<ObrasEx> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);*/
		List<ObrasEx> tests = obrExService.listar();
		model.addAttribute("tests", tests);
		List<ContactoProyectoEx>contacto_ex = contExService.listar();	
		model.addAttribute("contacto_ex", contacto_ex);
		return "listar_proyecto_ex";
	}
	

	@GetMapping("/new/{obraId}")
	public String agregarContacto(@PathVariable int obraId, Model model){		
		Optional<ObrasEx> obra=obrExService.findByObraId(obraId);
		ObrasEx obraa=obra.get();
		
		//model.addAttribute("obra", obraa);
		ContactoProyectoEx contacto_ex=new ContactoProyectoEx();
		
		if(obraa.getContacto()!=null) {
			contacto_ex.setNombreContactEx(obraa.getContacto().getNombreContactEx());
			contacto_ex.setTelefonoContactEx(obraa.getContacto().getTelefonoContactEx());
			contacto_ex.setCorreoContactEx(obraa.getContacto().getCorreoContactEx());
			contacto_ex.setContAvanObrEx(obraa.getContacto().getContAvanObrEx());
		}
		
		contacto_ex.setObraSel(obraa);
		contacto_ex.setIdObrEx(obraa.getIdObrEx());
		
		contacto_ex.setContProyObrEx(obraa.getNombreObrEx());
		contacto_ex.setContCiuObrEx(obraa.getCiudadObrEx());
		contacto_ex.setContConsObrEx(obraa.getConstObrEx());
		//contacto_ex.setContSecObrEx(obraa.getSectorObrEx());
		contacto_ex.setContStatObrEx(obraa.getStatusObrEx());
		contacto_ex.setContZonObrEx(obraa.getZonaObrEx());
		
		model.addAttribute("contacto_ex", contacto_ex);
		return "registro_contacto_ex";
	}
	
	@PostMapping("save")
	public String save(@Valid ContactoProyectoEx contacto_ex, Model model) {
		Optional<ObrasEx> obra=obrExService.findByObraId(contacto_ex.getIdObrEx());
		ObrasEx obraa=obra.get();		
		contacto_ex.setIdObrEx(obraa.getIdObrEx());
		contExService.save(contacto_ex);	
		obraa.setContacto(contacto_ex);
		obrExService.save(obraa);
		
		return "redirect:/contacto_ex/contExListar";
		
	}	
	
		
	@GetMapping("/editar/{idContEx}")
	public String update(@PathVariable int idContEx ,Model model) {	
		
		List<ObrasEx> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);
		List<ObrasEx> tests = obrExService.listar();
		model.addAttribute("tests", tests);
		Optional<ContactoProyectoEx>contacto_ex=contExService.listarId(idContEx);
		model.addAttribute("contacto_ex", contacto_ex);		
		return "registro_contacto_ex";
	}
	
	@GetMapping("/eliminar/{idContEx}")
	public String delete(Model model,@PathVariable int idContEx) {
	
		contExService.delete(idContEx);
		return "redirect:/contacto_ex/listar";
	}
	

	@GetMapping(value ="/contExListar")
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		
		Page<ContactoProyectoEx> listaContEx = contExService.getAll(pageRequest);
		
		int totalPage = listaContEx.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("contacto_ex", listaContEx.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		return "listar_proyecto_ex";
	}
	
	@GetMapping("/avance")
	public String getAvance(@RequestParam Map<String, Object> params,Model model) {
		
		
		List<ContactoProyectoEx>avance = contExService.findByAvance();
	    model.addAttribute("avance", avance);
			
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		
		Page<ContactoProyectoEx> listaContactEx = contExService.getAll(pageRequest);
		
		int totalPage = listaContactEx.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras cerradas externas", listaContactEx.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
	
		return "obras_cerradas_ex";
	}
	
	/*METODO REPORTE */
	
	@GetMapping(value = "/contactoExReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getContactoExReportPdf(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ContactoProyExternasReport report = new ContactoProyExternasReport(contProyExRepository.findAll());
		final byte[] data = reportService.getReportPdf(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=contactoExReport.pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}
	
	/*FIN REPORTE*/
	
	
	/*METODO REPORTE */
	
	@GetMapping(value = "/obrasExCerradasReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getObrasExCerradasReportPdf(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ObrasExternasCerradasReport report = new ObrasExternasCerradasReport(contProyExRepository.findByAvance());
		final byte[] data = reportService.getReportPdf(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=obrasExCerradasReport.pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}
	
	/*FIN REPORTE*/
	
	
	/*
	@GetMapping("/new")
	public String agregar(Model model){		
		List<ObrasEx> test = new ArrayList<>();	 	        
		model.addAttribute("test", test);
		List<ObrasEx> tests = obrExService.listar();
		model.addAttribute("tests", tests);
		model.addAttribute("contacto", new ContactoProyectoEx());
		return "registro_contacto_ex";
	}*/
	
	
	/*
	@PostMapping("/save")
	public String save(@Valid ContactoProyectoEx contacto_ex, Model model) {
		
		contExService.save(contacto_ex);	
		return "redirect:/api/contacto_ex/listar";
		
	}*/
	
	/*	@Autowired
	private ContProyExRepository contExRepository;*/
	
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
	
	/*	@GetMapping(value="/createpdf")		
	public void createPdf(HttpServletRequest request, HttpServletResponse response) {
			
	List<ContactoProyectoEx>contactos = contExService.listar();	
	boolean isFlag = contExService.createPdf(contactos,context1,request,response);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"contactos externos"+".pdf");
			filedownload(fullPath,response,"contactos externos.pdf");
		}
	}*/

/*	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
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
			    response.setHeader("Content-disposition", " filename1=" + fileName);
				
				
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
	}
	
	@GetMapping("/report/{format}")
	public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {	
		
		obrExService.exportReport(format);

		return "redirect:/contacto_ex/contExListar";
	}*/
	
}
