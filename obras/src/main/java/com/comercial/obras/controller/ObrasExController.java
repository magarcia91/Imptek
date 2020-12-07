package com.comercial.obras.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.comercial.obras.entity.ObrasEx;
import com.comercial.obras.report.ObrasExternasReport;
import com.comercial.obras.repository.ObrasExRepository;
import com.comercial.obras.service.ObrasExService;
import com.comercial.obras.service.ReportService;

import net.sf.jasperreports.engine.JRException;


@Controller
@RequestMapping("/obras_ex")
public class ObrasExController {
	
	
	/*REPORTE */
	@Autowired
	public ObrasExController(final ObrasExRepository obraExRepository, final ReportService reportService){
		this.obraExRepository = obraExRepository;
		this.reportService = reportService;
	}
	
	private final ReportService reportService;
	private final ObrasExRepository obraExRepository;
	
	/*FIN REPORTE*/
	
	@Autowired
	private ObrasExService obrExService;
	
	@Autowired
	private ServletContext context;

	@GetMapping("listar")
	public String listar(Model model) {		
		List<ObrasEx> obras_ex = obrExService.listar();	
		model.addAttribute("obras_ex", obras_ex);
		return "listar_obras_ex";
	}
	
	@GetMapping("new")
	public String agregar(Model model){			
		model.addAttribute("obra_ex", new ObrasEx());
		return "registro_obras_ex";
	}
	
	
	@PostMapping("save")
	public String save(@Valid ObrasEx obra_ex, Model model) {
		
		obrExService.save(obra_ex);
		return "redirect:/obras_ex/obraExListar";
		
	}
	
	@GetMapping("editar/{idObrEx}")
	public String update(@PathVariable int idObrEx ,Model model) {		
		Optional<ObrasEx>obras=obrExService.listarId(idObrEx);
		model.addAttribute("obra_ex", obras);
		
		return "registro_obras_ex";
	}
	
	@GetMapping("eliminar/{idObrEx}")
	public String delete(Model model,@PathVariable int idObrEx) {
	
		obrExService.delete(idObrEx);
		return "redirect:/obras_ex/listar";
	}
	
	
	@GetMapping("/avance")
	public String getAvance(@RequestParam Map<String, Object> params,Model model) {
		
		List<ObrasEx>avance = obrExService.findByAvance();
	    model.addAttribute("avance", avance);
	    
	    int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		
		Page<ObrasEx> listaContacto = obrExService.getAll(pageRequest);
		
		int totalPage = listaContacto.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras cerradas externas", listaContacto.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		
		return "obras_cerradas_ex";
	}
	
	@GetMapping("/report/{format}")
	public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {	
		
		obrExService.exportReport(format);

		return "redirect:/obras_ex/obraExListar" ;
	}
	
	@GetMapping(value ="/obraExListar")
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 10);
		
		Page<ObrasEx> listaObras = obrExService.getAll(pageRequest);
		
		int totalPage = listaObras.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("obras_ex", listaObras.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		return "listar_obras_ex";
	}
	
	@GetMapping(value = "/obrasExReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getObrasExReportPdf(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ObrasExternasReport report = new ObrasExternasReport(obraExRepository.findAll());
		final byte[] data = reportService.getReportPdf(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=obrasExReport.pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}

	
	/*	@GetMapping(value="/createpdf")		
	public void createPdf(HttpServletRequest request, HttpServletResponse response) {
			
	List<ObrasEx>obras_ex = obrExService.listar();	
	boolean isFlag = obrExService.createPdf(obras_ex,context,request,response);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"obras externas"+".pdf");
			filedownload(fullPath,response,"obras externas.pdf");
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
}
