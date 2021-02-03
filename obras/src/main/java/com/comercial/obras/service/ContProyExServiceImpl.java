package com.comercial.obras.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
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

import com.comercial.obras.entity.ContactoProyecto;
import com.comercial.obras.entity.ContactoProyectoEx;
import com.comercial.obras.repository.ContProyExRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
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
public class ContProyExServiceImpl implements ContProyExService{

	@Autowired
	private ContProyExRepository contExRepository;

	@Override
	public List<ContactoProyectoEx> listar() {
		return (List<ContactoProyectoEx>) contExRepository.findAll();
	}

	@Override
	public Optional<ContactoProyectoEx> listarId(int idContactEx) {
		return contExRepository.findById(idContactEx);
	}

	@Override
	public int save(ContactoProyectoEx contEx) {
		
		int res=0;
		ContactoProyectoEx con=contExRepository.save(contEx);
		
		if (!con.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void delete(int idContact) {
		contExRepository.deleteById(idContact);		
	}

	@Override
	public boolean createPdf(List<ContactoProyectoEx> contactos_ex, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
	
		Document document = new Document(PageSize.A4,15,15,45,30);
		
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			
			boolean exists = new File(filePath).exists();
			if (!exists) {
				new File(filePath).mkdirs();
			}
			
	       
			PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"contactos externos.pdf"));
			document.open();
				       	     	        		
			Font mainFont = FontFactory.getFont("Arial",8,BaseColor.BLACK);
		/*	
			String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRYKknkLIZkOOaKdswV_AjDrhRrkx2KSzG9C-3PKp991mcu9ZhZ&usqp=CAU";
	        Image image = Image.getInstance(url);
	        image.scaleAbsolute(100f, 50f);
	        image.setAlignment(Image.ALIGN_CENTER);*/
		 
				
			Paragraph paragraph = new Paragraph("Listado de Contactos Externos", mainFont);
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
			
			PdfPCell contProyObrEx = new PdfPCell(new Paragraph("Proyecto", tableHeader));
			contProyObrEx.setBorderColor(BaseColor.BLACK);
			contProyObrEx.setPaddingLeft(10);
			contProyObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			contProyObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			contProyObrEx.setBackgroundColor(BaseColor.GRAY);
			contProyObrEx.setExtraParagraphSpace(5f);
			table.addCell(contProyObrEx);
			
			PdfPCell contCiuObrEx= new PdfPCell(new Paragraph("Ciudad", tableHeader));
			contCiuObrEx.setBorderColor(BaseColor.BLACK);
			contCiuObrEx.setPaddingLeft(10);
			contCiuObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			contCiuObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			contCiuObrEx.setBackgroundColor(BaseColor.GRAY);
			contCiuObrEx.setExtraParagraphSpace(5f);
			table.addCell(contCiuObrEx);
			
			PdfPCell contZonObrEx= new PdfPCell(new Paragraph("Zona", tableHeader));
			contZonObrEx.setBorderColor(BaseColor.BLACK);
			contZonObrEx.setPaddingLeft(10);
			contZonObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			contZonObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			contZonObrEx.setBackgroundColor(BaseColor.GRAY);
			contZonObrEx.setExtraParagraphSpace(5f);
			table.addCell(contZonObrEx);
			
			PdfPCell contConsObrEx= new PdfPCell(new Paragraph("Constructora", tableHeader));
			contConsObrEx.setBorderColor(BaseColor.BLACK);
			contConsObrEx.setPaddingLeft(10);
			contConsObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			contConsObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			contConsObrEx.setBackgroundColor(BaseColor.GRAY);
			contConsObrEx.setExtraParagraphSpace(5f);
			table.addCell(contConsObrEx);
			
			PdfPCell contStatObrEx = new PdfPCell(new Paragraph("Estado", tableHeader));
			contStatObrEx.setBorderColor(BaseColor.BLACK);
			contStatObrEx.setPaddingLeft(10);
			contStatObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			contStatObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			contStatObrEx.setBackgroundColor(BaseColor.GRAY);
			contStatObrEx.setExtraParagraphSpace(5f);
			table.addCell(contStatObrEx);
			
			PdfPCell contAvanObrEx = new PdfPCell(new Paragraph("Avance", tableHeader));
			contAvanObrEx.setBorderColor(BaseColor.BLACK);
			contAvanObrEx.setPaddingLeft(10);
			contAvanObrEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			contAvanObrEx.setVerticalAlignment(Element.ALIGN_CENTER);
			contAvanObrEx.setBackgroundColor(BaseColor.GRAY);
			contAvanObrEx.setExtraParagraphSpace(5f);
			table.addCell(contAvanObrEx);
			
			PdfPCell nombreContactEx = new PdfPCell(new Paragraph("Contacto", tableHeader));
			nombreContactEx.setBorderColor(BaseColor.BLACK);
			nombreContactEx.setPaddingLeft(10);
			nombreContactEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			nombreContactEx.setVerticalAlignment(Element.ALIGN_CENTER);
			nombreContactEx.setBackgroundColor(BaseColor.GRAY);
			nombreContactEx.setExtraParagraphSpace(5f);
			table.addCell(nombreContactEx);
			
			PdfPCell telefonoContactEx = new PdfPCell(new Paragraph("Teléfono", tableHeader));
			telefonoContactEx.setBorderColor(BaseColor.BLACK);
			telefonoContactEx.setPaddingLeft(10);
			telefonoContactEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			telefonoContactEx.setVerticalAlignment(Element.ALIGN_CENTER);
			telefonoContactEx.setBackgroundColor(BaseColor.GRAY);
			telefonoContactEx.setExtraParagraphSpace(5f);
			table.addCell(telefonoContactEx);
			
			PdfPCell correoContactEx = new PdfPCell(new Paragraph("Correo", tableHeader));
			correoContactEx.setBorderColor(BaseColor.BLACK);
			correoContactEx.setPaddingLeft(10);
			correoContactEx.setHorizontalAlignment(Element.ALIGN_CENTER);
			correoContactEx.setVerticalAlignment(Element.ALIGN_CENTER);
			correoContactEx.setBackgroundColor(BaseColor.GRAY);
			correoContactEx.setExtraParagraphSpace(5f);
			table.addCell(correoContactEx);
			
			for (ContactoProyectoEx contacto:contactos_ex) {
								
				PdfPCell contProyObrExVal = new PdfPCell(new Paragraph(contacto.getContProyObrEx(), tableBody));
				contProyObrExVal.setBorderColor(BaseColor.BLACK);
				contProyObrExVal.setPaddingLeft(10);
				contProyObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contProyObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contProyObrExVal.setBackgroundColor(BaseColor.WHITE);
				contProyObrExVal.setExtraParagraphSpace(5f);
				table.addCell(contProyObrExVal);
				
				PdfPCell contCiuObrExVal = new PdfPCell(new Paragraph(contacto.getContCiuObrEx(), tableBody));
				contCiuObrExVal.setBorderColor(BaseColor.BLACK);
				contCiuObrExVal.setPaddingLeft(10);
				contCiuObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contCiuObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contCiuObrExVal.setBackgroundColor(BaseColor.WHITE);
				contCiuObrExVal.setExtraParagraphSpace(5f);
				table.addCell(contCiuObrExVal);
				
				PdfPCell contZonObrExVal = new PdfPCell(new Paragraph(contacto.getContZonObrEx(), tableBody));
				contZonObrExVal.setBorderColor(BaseColor.BLACK);
				contZonObrExVal.setPaddingLeft(10);
				contZonObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contZonObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contZonObrExVal.setBackgroundColor(BaseColor.WHITE);
				contZonObrExVal.setExtraParagraphSpace(5f);
				table.addCell(contZonObrExVal);
				
				PdfPCell contConsObrExVal = new PdfPCell(new Paragraph(contacto.getContConsObrEx(), tableBody));
				contConsObrExVal.setBorderColor(BaseColor.BLACK);
				contConsObrExVal.setPaddingLeft(10);
				contConsObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contConsObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contConsObrExVal.setBackgroundColor(BaseColor.WHITE);
				contConsObrExVal.setExtraParagraphSpace(5f);
				table.addCell(contConsObrExVal);
				
				
				PdfPCell contStatObrExVal = new PdfPCell(new Paragraph(contacto.getContStatObrEx(), tableBody));
				contStatObrExVal.setBorderColor(BaseColor.BLACK);
				contStatObrExVal.setPaddingLeft(10);
				contStatObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contStatObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contStatObrExVal.setBackgroundColor(BaseColor.WHITE);
				contStatObrExVal.setExtraParagraphSpace(5f);
				table.addCell(contStatObrExVal);
				
				PdfPCell contAvanObrExVal = new PdfPCell(new Paragraph(contacto.getContAvanObrEx(), tableBody));
				contAvanObrExVal.setBorderColor(BaseColor.BLACK);
				contAvanObrExVal.setPaddingLeft(10);
				contAvanObrExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contAvanObrExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contAvanObrExVal.setBackgroundColor(BaseColor.WHITE);
				contAvanObrExVal.setExtraParagraphSpace(5f);
				table.addCell(contAvanObrExVal);
				
				PdfPCell nombreContactExVal = new PdfPCell(new Paragraph(contacto.getNombreContactEx(), tableBody));
				nombreContactExVal.setBorderColor(BaseColor.BLACK);
				nombreContactExVal.setPaddingLeft(10);
				nombreContactExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				nombreContactExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				nombreContactExVal.setBackgroundColor(BaseColor.WHITE);
				nombreContactExVal.setExtraParagraphSpace(5f);
				table.addCell(nombreContactExVal);
				
				PdfPCell telefonoContactExVal = new PdfPCell(new Paragraph(contacto.getTelefonoContactEx(), tableBody));
				telefonoContactExVal.setBorderColor(BaseColor.BLACK);
				telefonoContactExVal.setPaddingLeft(10);
				telefonoContactExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				telefonoContactExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				telefonoContactExVal.setBackgroundColor(BaseColor.WHITE);
				telefonoContactExVal.setExtraParagraphSpace(5f);
				table.addCell(telefonoContactExVal);
				
				PdfPCell correoContactExVal = new PdfPCell(new Paragraph(contacto.getCorreoContactEx(), tableBody));
				correoContactExVal.setBorderColor(BaseColor.BLACK);
				correoContactExVal.setPaddingLeft(10);
				correoContactExVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				correoContactExVal.setVerticalAlignment(Element.ALIGN_CENTER);
				correoContactExVal.setBackgroundColor(BaseColor.WHITE);
				correoContactExVal.setExtraParagraphSpace(5f);
				table.addCell(correoContactExVal);
				
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
	public Page<ContactoProyectoEx> getAll(Pageable pageable) {
		return contExRepository.findAll(pageable) ;
	}

	@Override
	public List<ContactoProyectoEx> findByAvance() {
		return contExRepository.findByAvance() ;
	}

	@Override
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		
		List<ContactoProyectoEx> obrEx=(List<ContactoProyectoEx>) contExRepository.findAll();
		
		String path ="D:\\JasperReports\\";
		File file= ResourceUtils.getFile("classpath:contactos.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(obrEx);
		Map<String,Object> parameters = new HashMap<>();
		
		JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport, parameters, dataSource);
				
		if (reportFormat.equalsIgnoreCase("pdf")) {
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\contactos.pdf");
		}
		return "report generated in path:"+path;
	}

	
/*	@Override
	public List<ContactoProyectoEx> findByAvance() {
	
		List<ContactoProyectoEx> con = contExRepository.findByAvance();
        return con;
	}*/



}
