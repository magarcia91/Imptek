package com.imptek.bitacora.service;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imptek.bitacora.entity.Factura;
import com.imptek.bitacora.repository.FacturaRepository;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Override
	public Page<Factura> getAll(Pageable pageable) {
		return facturaRepository.findAll(pageable);
	}

	@Override
	public boolean createExcel(List<Factura> facturas, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		

			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			
			boolean exists = new File(filePath).exists();
			if (!exists) {
				new File(filePath).mkdirs();
			}
			
	       try {
	    	   
	    	   FileOutputStream outputStream = new FileOutputStream(file+"/"+"facturas"+".xls");
	    	   HSSFWorkbook workbook = new HSSFWorkbook();
	    	   HSSFSheet workSheet = workbook.createSheet("Facturas");
	    	   workSheet.setDefaultColumnWidth(30);
	    
	    	   
	    	   HSSFCellStyle headerCellStyle = workbook.createCellStyle();
	    	   HSSFFont headerFont = workbook.createFont();
	    	   headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	           headerCellStyle.setFont(headerFont);
	    	   headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    	   headerCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
	    	   headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    	   
	    	   HSSFRow headerRow = workSheet.createRow(0);	    	   
	    	 
	    	   HSSFCell codCliente = headerRow.createCell(0);
	    	   codCliente.setCellValue("Cod.Cliente");
	    	   codCliente.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell nomCliente = headerRow.createCell(1);
	    	   nomCliente.setCellValue("Nombre");
	    	   nomCliente.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell numFactura = headerRow.createCell(2);
	    	   numFactura.setCellValue("#Factura");
	    	   numFactura.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell fechaFactura = headerRow.createCell(3);
	    	   fechaFactura.setCellValue("Fecha");
	    	   fechaFactura.setCellStyle(headerCellStyle);
	    	   
	    	   int i = 1;
	    	   for (Factura factura : facturas) {
	    		   
	    		   HSSFRow bodyRow = workSheet.createRow(i);
	    		   
	    		   HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
	    		   bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
	    		    		   
	    		   HSSFCell  codClienteValue = bodyRow.createCell(0);
	    		   codClienteValue.setCellValue(factura.getCliente().getCodCliente());
	    		   codClienteValue.setCellStyle(bodyCellStyle);
	    		   
	    		   
	    		   HSSFCell  nomClienteValue = bodyRow.createCell(1);
	    		   nomClienteValue.setCellValue(factura.getCliente().getNomCliente());
	    		   nomClienteValue.setCellStyle(bodyCellStyle);
	    		   
	    		   
	    		   HSSFCell  numFacturaValue = bodyRow.createCell(2);
	    		   numFacturaValue.setCellValue(factura.getNumFact());
	    		   numFacturaValue.setCellStyle(bodyCellStyle);
	    		   
	    		   
	    		   HSSFCell  fechaFacturaValue = bodyRow.createCell(3);
	    		   fechaFacturaValue.setCellValue(factura.getFechaFact().toString());
	    		   fechaFacturaValue.setCellStyle(bodyCellStyle);
	    		   
	    		   i++;
				
	    	   }
	    	   
	    	   workbook.write(outputStream);
	    	   outputStream.flush();
	    	   outputStream.close();
	    	 return true;
	    	   
	    	   
	       }catch (Exception e) {
			
	    	  return false;
		}		 
		
	}

}
