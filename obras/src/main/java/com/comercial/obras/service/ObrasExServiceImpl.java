package com.comercial.obras.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.comercial.obras.entity.ObrasEx;
import com.comercial.obras.repository.ObrasExRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Transactional
public class ObrasExServiceImpl implements ObrasExService {

	@Autowired
	private ObrasExRepository obraExRepository;

	@Override
	public List<ObrasEx> listar() {		
		
		return (List<ObrasEx>) obraExRepository.findAll();
	}

	@Override
	public Optional<ObrasEx> listarId(int idUsu) {
		
		return obraExRepository.findById(idUsu);		
	}

	@Override
	public int save(ObrasEx obrasEx) {
		
		int res=0;
		ObrasEx obrEx=obraExRepository.save(obrasEx);
		
		if (!obrEx.equals(null)) {
			res=1;
		}
		return res;
		
	}

	@Override
	public void delete(int idObrEx) {
		
		obraExRepository.deleteById(idObrEx);
		
	}

	@Override
	public Optional<ObrasEx> findByObraId(int idObrEx) {
		
		return obraExRepository.findById(idObrEx);
	}

	
	@Override
	public List<ObrasEx> findByAvance() {

		List<ObrasEx> obr = obraExRepository.findByAvance();
        return obr;
	}

	@Override
	public boolean createPdf(List<ObrasEx> obrasEx, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4,15,15,45,30);
		
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			
			boolean exists = new File(filePath).exists();
			if (!exists) {
				new File(filePath).mkdirs();
			}
			
	       
			PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"obras externas.pdf"));
			document.open();
				       	     	        		
			Font mainFont = FontFactory.getFont("Arial",8,BaseColor.BLACK);
			
			/*String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRYKknkLIZkOOaKdswV_AjDrhRrkx2KSzG9C-3PKp991mcu9ZhZ&usqp=CAU";
	        Image image = Image.getInstance(url);
	        image.scaleAbsolute(100f, 50f);
	        image.setAlignment(Image.ALIGN_CENTER);*/
			
			Paragraph paragraph = new Paragraph("Listado de Obras Externas", mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(5);
			document.add(paragraph);
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(5);
						
			Font tableHeader = FontFactory.getFont("Arial",6,BaseColor.WHITE);
			Font tableBody = FontFactory.getFont("Arial",5,BaseColor.BLACK);
			
			float[] columnWidths = {1f,1f,1f,1f,1f,1f,1f,1f,1f};
			table.setWidths(columnWidths);
			
			PdfPCell nombreObrEx = new PdfPCell(new Paragraph("Proyecto", tableHeader));
			nombreObrEx.setBorderColor(BaseColor.BLACK);
			nombreObrEx.setPaddingLeft(10);
			nombreObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			nombreObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			nombreObrEx.setBackgroundColor(BaseColor.GRAY);
			nombreObrEx.setExtraParagraphSpace(5f);
			table.addCell(nombreObrEx);
			
			PdfPCell ciudadObrEx= new PdfPCell(new Paragraph("Ciudad", tableHeader));
			ciudadObrEx.setBorderColor(BaseColor.BLACK);
			ciudadObrEx.setPaddingLeft(10);
			ciudadObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			ciudadObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			ciudadObrEx.setBackgroundColor(BaseColor.GRAY);
			ciudadObrEx.setExtraParagraphSpace(5f);
			table.addCell(ciudadObrEx);
			
			PdfPCell sectorObrEx= new PdfPCell(new Paragraph("Sector", tableHeader));
			sectorObrEx.setBorderColor(BaseColor.BLACK);
			sectorObrEx.setPaddingLeft(10);
			sectorObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			sectorObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			sectorObrEx.setBackgroundColor(BaseColor.GRAY);
			sectorObrEx.setExtraParagraphSpace(5f);
			table.addCell(sectorObrEx);
			
			PdfPCell constObrEx= new PdfPCell(new Paragraph("Constructora", tableHeader));
			constObrEx.setBorderColor(BaseColor.BLACK);
			constObrEx.setPaddingLeft(10);
			constObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			constObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			constObrEx.setBackgroundColor(BaseColor.GRAY);
			constObrEx.setExtraParagraphSpace(5f);
			table.addCell(constObrEx);
			
			PdfPCell statusObrEx = new PdfPCell(new Paragraph("Estado", tableHeader));
			statusObrEx.setBorderColor(BaseColor.BLACK);
			statusObrEx.setPaddingLeft(10);
			statusObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			statusObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			statusObrEx.setBackgroundColor(BaseColor.GRAY);
			statusObrEx.setExtraParagraphSpace(5f);
			table.addCell(statusObrEx);
			
			PdfPCell cotizacionObrEx = new PdfPCell(new Paragraph("Cotización", tableHeader));
			cotizacionObrEx.setBorderColor(BaseColor.BLACK);
			cotizacionObrEx.setPaddingLeft(10);
			cotizacionObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			cotizacionObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			cotizacionObrEx.setBackgroundColor(BaseColor.GRAY);
			cotizacionObrEx.setExtraParagraphSpace(5f);
			table.addCell(cotizacionObrEx);
			
			PdfPCell fechaCotObrEx = new PdfPCell(new Paragraph("Fecha Cotización", tableHeader));
			fechaCotObrEx.setBorderColor(BaseColor.BLACK);
			fechaCotObrEx.setPaddingLeft(10);
			fechaCotObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			fechaCotObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			fechaCotObrEx.setBackgroundColor(BaseColor.GRAY);
			fechaCotObrEx.setExtraParagraphSpace(5f);
			table.addCell(fechaCotObrEx);
			
			PdfPCell fechaCieObrEx = new PdfPCell(new Paragraph("Fecha Cierre", tableHeader));
			fechaCieObrEx.setBorderColor(BaseColor.BLACK);
			fechaCieObrEx.setPaddingLeft(10);
			fechaCieObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			fechaCieObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			fechaCieObrEx.setBackgroundColor(BaseColor.GRAY);
			fechaCieObrEx.setExtraParagraphSpace(5f);
			table.addCell(fechaCieObrEx);
			
			PdfPCell comentObrEx = new PdfPCell(new Paragraph("Comentarios", tableHeader));
			comentObrEx.setBorderColor(BaseColor.BLACK);
			comentObrEx.setPaddingLeft(10);
			comentObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			comentObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			comentObrEx.setBackgroundColor(BaseColor.GRAY);
			comentObrEx.setExtraParagraphSpace(5f);
			table.addCell(comentObrEx);
			
			for (ObrasEx obras:obrasEx) {
								
				PdfPCell nombreObrExVal = new PdfPCell(new Paragraph(obras.getNombreObrEx(), tableBody));
				nombreObrExVal.setBorderColor(BaseColor.BLACK);
				nombreObrExVal.setPaddingLeft(10);
				nombreObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				nombreObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				nombreObrExVal.setBackgroundColor(BaseColor.WHITE);
				nombreObrExVal.setExtraParagraphSpace(5f);
				table.addCell(nombreObrExVal);
				
				PdfPCell ciudadObrExVal = new PdfPCell(new Paragraph(obras.getCiudadObrEx(), tableBody));
				ciudadObrExVal.setBorderColor(BaseColor.BLACK);
				ciudadObrExVal.setPaddingLeft(10);
				ciudadObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				ciudadObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				ciudadObrExVal.setBackgroundColor(BaseColor.WHITE);
				ciudadObrExVal.setExtraParagraphSpace(5f);
				table.addCell(ciudadObrExVal);
				
				PdfPCell sectorObrExVal = new PdfPCell(new Paragraph(obras.getSectorObrEx(), tableBody));
				sectorObrExVal.setBorderColor(BaseColor.BLACK);
				sectorObrExVal.setPaddingLeft(10);
				sectorObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				sectorObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				sectorObrExVal.setBackgroundColor(BaseColor.WHITE);
				sectorObrExVal.setExtraParagraphSpace(5f);
				table.addCell(sectorObrExVal);
				
				PdfPCell constObrExVal = new PdfPCell(new Paragraph(obras.getConstObrEx(), tableBody));
				constObrExVal.setBorderColor(BaseColor.BLACK);
				constObrExVal.setPaddingLeft(10);
				constObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				constObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				constObrExVal.setBackgroundColor(BaseColor.WHITE);
				constObrExVal.setExtraParagraphSpace(5f);
				table.addCell(constObrExVal);
						
				PdfPCell statusObrExVal = new PdfPCell(new Paragraph(obras.getStatusObrEx(), tableBody));
				statusObrExVal.setBorderColor(BaseColor.BLACK);
				statusObrExVal.setPaddingLeft(10);
				statusObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				statusObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				statusObrExVal.setBackgroundColor(BaseColor.WHITE);
				statusObrExVal.setExtraParagraphSpace(5f);
				table.addCell(statusObrExVal);
				
				PdfPCell cotizacionObrExVal = new PdfPCell(new Paragraph(obras.getCotizacionObrEx().toString(), tableBody));
				cotizacionObrExVal.setBorderColor(BaseColor.BLACK);
				cotizacionObrExVal.setPaddingLeft(10);
				cotizacionObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				cotizacionObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				cotizacionObrExVal.setBackgroundColor(BaseColor.WHITE);
				cotizacionObrExVal.setExtraParagraphSpace(5f);
				table.addCell(cotizacionObrExVal);
				
				PdfPCell fechaCotObrExVal = new PdfPCell(new Paragraph(obras.getFechaCotObrEx().toString(), tableBody));
				fechaCotObrExVal.setBorderColor(BaseColor.BLACK);
				fechaCotObrExVal.setPaddingLeft(10);
				fechaCotObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				fechaCotObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				fechaCotObrExVal.setBackgroundColor(BaseColor.WHITE);
				fechaCotObrExVal.setExtraParagraphSpace(5f);
				table.addCell(fechaCotObrExVal);
				
				PdfPCell fechaCieObrExVal = new PdfPCell(new Paragraph(obras.getFechaCieObrEx().toString(), tableBody));
				fechaCieObrExVal.setBorderColor(BaseColor.BLACK);
				fechaCieObrExVal.setPaddingLeft(10);
				fechaCieObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				fechaCieObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				fechaCieObrExVal.setBackgroundColor(BaseColor.WHITE);
				fechaCieObrExVal.setExtraParagraphSpace(5f);
				table.addCell(fechaCieObrExVal);
				
				PdfPCell comentObrExVal = new PdfPCell(new Paragraph(obras.getComentObrEx(), tableBody));
				comentObrExVal.setBorderColor(BaseColor.BLACK);
				comentObrExVal.setPaddingLeft(10);
				comentObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				comentObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				comentObrExVal.setBackgroundColor(BaseColor.WHITE);
				comentObrExVal.setExtraParagraphSpace(5f);
				table.addCell(comentObrExVal);
				
			}
			
			//document.add(image);
			document.add(table);
			document.close();
			writer.close();
			return true;
			
			
		} catch (Exception e) {
			return false;
		}		
		
	}

	@Override
	public Page<ObrasEx> getAll(Pageable pageable) {
		return 	obraExRepository.findAll(pageable);
	}

	@Override
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {

		List<ObrasEx> obrEx=(List<ObrasEx>) obraExRepository.findAll();
		
		String path ="D:\\JasperReports\\";
		File file= ResourceUtils.getFile("classpath:obras externas.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(obrEx);
		Map<String,Object> parameters = new HashMap<>();
		
		JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport, parameters, dataSource);
				
		if (reportFormat.equalsIgnoreCase("pdf")) {
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\obras externas.pdf");
		}
		return "report generated in path:"+path;
		
	}

}
