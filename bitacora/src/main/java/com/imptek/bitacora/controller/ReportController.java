package com.imptek.bitacora.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.report.ClientesReport;
import com.imptek.bitacora.repository.ClienteRepository;
import com.imptek.bitacora.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/reportCliente")
public class ReportController {

	private final ClienteRepository clienteRepository;
	private final ReportService reportService;

	@Autowired
	public ReportController(final ClienteRepository clienteRepository, final ReportService reportService){
		this.clienteRepository = clienteRepository;
		this.reportService = reportService;
	}

	@GetMapping
	public String getHome(){
		return "redirect:/clientesReport.xlsx";
	}

	@GetMapping(value = "/clientesReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getObrasExReportPdf(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ClientesReport report = new ClientesReport((Collection<Cliente>) clienteRepository.findAll());
		final byte[] data = reportService.getReportPdf(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=clientesReport.pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}

	@GetMapping(value = "/clientesReport.xlsx", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	@ResponseBody
	public HttpEntity<byte[]> getClientesReportXlsx(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ClientesReport report = new ClientesReport((Collection<Cliente>) clienteRepository.findAll());
		final byte[] data = reportService.getReportXlsx(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=clientesReport.xlsx");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}
	
}
