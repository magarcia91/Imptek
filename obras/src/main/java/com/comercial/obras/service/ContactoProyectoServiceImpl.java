package com.comercial.obras.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comercial.obras.entity.ContactoProyecto;
import com.comercial.obras.entity.ContactoProyectoEx;
import com.comercial.obras.entity.ObrasEx;
import com.comercial.obras.repository.ContactoProyectoRepository;
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

@Service
@Transactional
public class ContactoProyectoServiceImpl implements ContactoProyectoService{

	@Autowired
	private ContactoProyectoRepository contactoRepository;

	@Override
	public List<ContactoProyecto> listar() {
		return (List<ContactoProyecto>) contactoRepository.findAll();
	}

	@Override
	public Optional<ContactoProyecto> listarId(Long idContact) {
		return contactoRepository.findById(idContact);
	}

	@Override
	public int save(ContactoProyecto contacto) {
		
		int res=0;
		ContactoProyecto con=contactoRepository.save(contacto);
		
		if (!con.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void delete(Long idContact) {
		contactoRepository.deleteById(idContact);		
	}
	
	@Override
	public List<ContactoProyecto> findByAvance() {
		
		List<ContactoProyecto> con = contactoRepository.findByAvance();
        return con;
	}

	@Override
	public boolean createPdf(List<ContactoProyecto> contactos, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
	
		Document document = new Document(PageSize.A4,15,15,45,30);
		
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			
			boolean exists = new File(filePath).exists();
			if (!exists) {
				new File(filePath).mkdirs();
			}
			
	       
			PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"contactos imptek.pdf"));
			document.open();
				       	     	        		
			Font mainFont = FontFactory.getFont("Arial",8,BaseColor.BLACK);
		/*	
			String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRYKknkLIZkOOaKdswV_AjDrhRrkx2KSzG9C-3PKp991mcu9ZhZ&usqp=CAU";
	        Image image = Image.getInstance(url);
	        image.scaleAbsolute(100f, 50f);
	        image.setAlignment(Image.ALIGN_CENTER);*/
		 
			/*String filename = "/src/main/resources/static/img/Imptek-pdf.jpg";
		    Image image = Image.getInstance(filename);*/
								
			Paragraph paragraph = new Paragraph("Listado de Contactos Imptek", mainFont);
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
			
			PdfPCell contactoProyectoObr = new PdfPCell(new Paragraph("Proyecto", tableHeader));
			contactoProyectoObr.setBorderColor(BaseColor.BLACK);
			contactoProyectoObr.setPaddingLeft(10);
			contactoProyectoObr.setHorizontalAlignment(Element.ALIGN_CENTER);
			contactoProyectoObr.setVerticalAlignment(Element.ALIGN_CENTER);
			contactoProyectoObr.setBackgroundColor(BaseColor.GRAY);
			contactoProyectoObr.setExtraParagraphSpace(5f);
			table.addCell(contactoProyectoObr);
			
			PdfPCell contactoCiudadObr= new PdfPCell(new Paragraph("Ciudad", tableHeader));
			contactoCiudadObr.setBorderColor(BaseColor.BLACK);
			contactoCiudadObr.setPaddingLeft(10);
			contactoCiudadObr.setHorizontalAlignment(Element.ALIGN_CENTER);
			contactoCiudadObr.setVerticalAlignment(Element.ALIGN_CENTER);
			contactoCiudadObr.setBackgroundColor(BaseColor.GRAY);
			contactoCiudadObr.setExtraParagraphSpace(5f);
			table.addCell(contactoCiudadObr);
			
			PdfPCell contactoZonaObr= new PdfPCell(new Paragraph("Zona", tableHeader));
			contactoZonaObr.setBorderColor(BaseColor.BLACK);
			contactoZonaObr.setPaddingLeft(10);
			contactoZonaObr.setHorizontalAlignment(Element.ALIGN_CENTER);
			contactoZonaObr.setVerticalAlignment(Element.ALIGN_CENTER);
			contactoZonaObr.setBackgroundColor(BaseColor.GRAY);
			contactoZonaObr.setExtraParagraphSpace(5f);
			table.addCell(contactoZonaObr);
			
			PdfPCell contactoConstructoraObr= new PdfPCell(new Paragraph("Constructora", tableHeader));
			contactoConstructoraObr.setBorderColor(BaseColor.BLACK);
			contactoConstructoraObr.setPaddingLeft(10);
			contactoConstructoraObr.setHorizontalAlignment(Element.ALIGN_CENTER);
			contactoConstructoraObr.setVerticalAlignment(Element.ALIGN_CENTER);
			contactoConstructoraObr.setBackgroundColor(BaseColor.GRAY);
			contactoConstructoraObr.setExtraParagraphSpace(5f);
			table.addCell(contactoConstructoraObr);
			
			PdfPCell contactoAvanceObr = new PdfPCell(new Paragraph("Avance Obra", tableHeader));
			contactoAvanceObr.setBorderColor(BaseColor.BLACK);
			contactoAvanceObr.setPaddingLeft(10);
			contactoAvanceObr.setHorizontalAlignment(Element.ALIGN_CENTER);
			contactoAvanceObr.setVerticalAlignment(Element.ALIGN_CENTER);
			contactoAvanceObr.setBackgroundColor(BaseColor.GRAY);
			contactoAvanceObr.setExtraParagraphSpace(5f);
			table.addCell(contactoAvanceObr);
			
			PdfPCell contactoStatusObr = new PdfPCell(new Paragraph("Estado Obra", tableHeader));
			contactoStatusObr.setBorderColor(BaseColor.BLACK);
			contactoStatusObr.setPaddingLeft(10);
			contactoStatusObr.setHorizontalAlignment(Element.ALIGN_CENTER);
			contactoStatusObr.setVerticalAlignment(Element.ALIGN_CENTER);
			contactoStatusObr.setBackgroundColor(BaseColor.GRAY);
			contactoStatusObr.setExtraParagraphSpace(5f);
			table.addCell(contactoStatusObr);
			
			PdfPCell nombreContact = new PdfPCell(new Paragraph("Contacto", tableHeader));
			nombreContact.setBorderColor(BaseColor.BLACK);
			nombreContact.setPaddingLeft(10);
			nombreContact.setHorizontalAlignment(Element.ALIGN_CENTER);
			nombreContact.setVerticalAlignment(Element.ALIGN_CENTER);
			nombreContact.setBackgroundColor(BaseColor.GRAY);
			nombreContact.setExtraParagraphSpace(5f);
			table.addCell(nombreContact);
			
			PdfPCell telefonoContact = new PdfPCell(new Paragraph("Teléfono", tableHeader));
			telefonoContact.setBorderColor(BaseColor.BLACK);
			telefonoContact.setPaddingLeft(10);
			telefonoContact.setHorizontalAlignment(Element.ALIGN_CENTER);
			telefonoContact.setVerticalAlignment(Element.ALIGN_CENTER);
			telefonoContact.setBackgroundColor(BaseColor.GRAY);
			telefonoContact.setExtraParagraphSpace(5f);
			table.addCell(telefonoContact);
			
			PdfPCell correoContact = new PdfPCell(new Paragraph("Correo", tableHeader));
			correoContact.setBorderColor(BaseColor.BLACK);
			correoContact.setPaddingLeft(10);
			correoContact.setHorizontalAlignment(Element.ALIGN_CENTER);
			correoContact.setVerticalAlignment(Element.ALIGN_CENTER);
			correoContact.setBackgroundColor(BaseColor.GRAY);
			correoContact.setExtraParagraphSpace(5f);
			table.addCell(correoContact);
			
			for (ContactoProyecto contacto:contactos) {
								
				PdfPCell contactoProyectoObrVal = new PdfPCell(new Paragraph(contacto.getContactoProyectoObr(), tableBody));
				contactoProyectoObrVal.setBorderColor(BaseColor.BLACK);
				contactoProyectoObrVal.setPaddingLeft(10);
				contactoProyectoObrVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contactoProyectoObrVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contactoProyectoObrVal.setBackgroundColor(BaseColor.WHITE);
				contactoProyectoObrVal.setExtraParagraphSpace(5f);
				table.addCell(contactoProyectoObrVal);
				
				PdfPCell contactoCiudadObrVal = new PdfPCell(new Paragraph(contacto.getContactoCiudadObr(), tableBody));
				contactoCiudadObrVal.setBorderColor(BaseColor.BLACK);
				contactoCiudadObrVal.setPaddingLeft(10);
				contactoCiudadObrVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contactoCiudadObrVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contactoCiudadObrVal.setBackgroundColor(BaseColor.WHITE);
				contactoCiudadObrVal.setExtraParagraphSpace(5f);
				table.addCell(contactoCiudadObrVal);
				
				PdfPCell contactoZonaObrVal = new PdfPCell(new Paragraph(contacto.getContactoZonaObr(), tableBody));
				contactoZonaObrVal.setBorderColor(BaseColor.BLACK);
				contactoZonaObrVal.setPaddingLeft(10);
				contactoZonaObrVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contactoZonaObrVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contactoZonaObrVal.setBackgroundColor(BaseColor.WHITE);
				contactoZonaObrVal.setExtraParagraphSpace(5f);
				table.addCell(contactoZonaObrVal);
				
				PdfPCell contactoConstructoraObrVal = new PdfPCell(new Paragraph(contacto.getContactoConstructoraObr(), tableBody));
				contactoConstructoraObrVal.setBorderColor(BaseColor.BLACK);
				contactoConstructoraObrVal.setPaddingLeft(10);
				contactoConstructoraObrVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contactoConstructoraObrVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contactoConstructoraObrVal.setBackgroundColor(BaseColor.WHITE);
				contactoConstructoraObrVal.setExtraParagraphSpace(5f);
				table.addCell(contactoConstructoraObrVal);
				
				PdfPCell contactoAvanceObrVal = new PdfPCell(new Paragraph(contacto.getContactoAvanceObr(), tableBody));
				contactoAvanceObrVal.setBorderColor(BaseColor.BLACK);
				contactoAvanceObrVal.setPaddingLeft(10);
				contactoAvanceObrVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contactoAvanceObrVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contactoAvanceObrVal.setBackgroundColor(BaseColor.WHITE);
				contactoAvanceObrVal.setExtraParagraphSpace(5f);
				table.addCell(contactoAvanceObrVal);
				
				PdfPCell contactoStatusObrVal = new PdfPCell(new Paragraph(contacto.getContactoStatusObr(), tableBody));
				contactoStatusObrVal.setBorderColor(BaseColor.BLACK);
				contactoStatusObrVal.setPaddingLeft(10);
				contactoStatusObrVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				contactoStatusObrVal.setVerticalAlignment(Element.ALIGN_CENTER);
				contactoStatusObrVal.setBackgroundColor(BaseColor.WHITE);
				contactoStatusObrVal.setExtraParagraphSpace(5f);
				table.addCell(contactoStatusObrVal);
				
				PdfPCell nombreContactVal = new PdfPCell(new Paragraph(contacto.getNombreContact(), tableBody));
				nombreContactVal.setBorderColor(BaseColor.BLACK);
				nombreContactVal.setPaddingLeft(10);
				nombreContactVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				nombreContactVal.setVerticalAlignment(Element.ALIGN_CENTER);
				nombreContactVal.setBackgroundColor(BaseColor.WHITE);
				nombreContactVal.setExtraParagraphSpace(5f);
				table.addCell(nombreContactVal);
				
				PdfPCell telefonoContactVal = new PdfPCell(new Paragraph(contacto.getTelefonoContact(), tableBody));
				telefonoContactVal.setBorderColor(BaseColor.BLACK);
				telefonoContactVal.setPaddingLeft(10);
				telefonoContactVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				telefonoContactVal.setVerticalAlignment(Element.ALIGN_CENTER);
				telefonoContactVal.setBackgroundColor(BaseColor.WHITE);
				telefonoContactVal.setExtraParagraphSpace(5f);
				table.addCell(telefonoContactVal);
				
				PdfPCell correoContactVal = new PdfPCell(new Paragraph(contacto.getCorreoContact(), tableBody));
				correoContactVal.setBorderColor(BaseColor.BLACK);
				correoContactVal.setPaddingLeft(10);
				correoContactVal.setHorizontalAlignment(Element.ALIGN_CENTER);
				correoContactVal.setVerticalAlignment(Element.ALIGN_CENTER);
				correoContactVal.setBackgroundColor(BaseColor.WHITE);
				correoContactVal.setExtraParagraphSpace(5f);
				table.addCell(correoContactVal);
				
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
	public Page<ContactoProyecto> getAll(Pageable pageable) {		
		return contactoRepository.findAll(pageable);
	}

	
	
}
