package com.comercial.obras.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.comercial.obras.report.ObrasExternasReport;
import com.comercial.obras.repository.ObrasExRepository;
import com.comercial.obras.service.ReportService;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/reportObraEx")
public class ReportController {

	private final ObrasExRepository obraExRepository;
	private final ReportService reportService;

	@Autowired
	public ReportController(final ObrasExRepository obraExRepository, final ReportService reportService){
		this.obraExRepository = obraExRepository;
		this.reportService = reportService;
	}

	@GetMapping
	public String getHome(){
		return "redirect:/obrasExReport.pdf";
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

	@GetMapping(value = "/obrasExReport.xlsx", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	@ResponseBody
	public HttpEntity<byte[]> getObrasExReportXlsx(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ObrasExternasReport report = new ObrasExternasReport(obraExRepository.findAll());
		final byte[] data = reportService.getReportXlsx(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=employeeReport.xlsx");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}
	
}
