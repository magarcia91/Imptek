package com.imptek.bitacora.service;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;
import com.imptek.bitacora.repository.FacturaRepository;

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
	    	   workSheet.setDefaultColumnWidth(20);
	    
	    	   
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
	    	   
	    	   HSSFCell montoPago = headerRow.createCell(4);
	    	   montoPago.setCellValue("Monto");
	    	   montoPago.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell formaPago = headerRow.createCell(5);
	    	   formaPago.setCellValue("Pago");
	    	   formaPago.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell docFacSap = headerRow.createCell(6);
	    	   docFacSap.setCellValue("#Comprobante");
	    	   docFacSap.setCellStyle(headerCellStyle);
	    	   	    	   
	    	   HSSFCell numLote = headerRow.createCell(7);
	    	   numLote.setCellValue("#Lote");
	    	   numLote.setCellStyle(headerCellStyle);
	    	   	    	   
	    	   HSSFCell factReferencia = headerRow.createCell(8);
	    	   factReferencia.setCellValue("#Referencia");
	    	   factReferencia.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell numComp = headerRow.createCell(9);
	    	   numComp.setCellValue("#Compensación");
	    	   numComp.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell fechaComp = headerRow.createCell(10);
	    	   fechaComp.setCellValue("Fecha");
	    	   fechaComp.setCellStyle(headerCellStyle);
	    	   
	    	   HSSFCell centroFactura = headerRow.createCell(11);
	    	   centroFactura.setCellValue("Centro");
	    	   centroFactura.setCellStyle(headerCellStyle);
	    	   
	    	   /*HSSFCell fechaCreacion = headerRow.createCell(12);
	    	   fechaCreacion.setCellValue("Creación");
	    	   fechaCreacion.setCellStyle(headerCellStyle);*/
	    	   
	    	   HSSFCell estadoFactura = headerRow.createCell(12);
	    	   estadoFactura.setCellValue("Depósito");
	    	   estadoFactura.setCellStyle(headerCellStyle);
	    	   
	    	  /* HSSFCell prontoPago = headerRow.createCell(13);
	    	   prontoPago.setCellValue("Pronto Pago");
	    	   prontoPago.setCellStyle(headerCellStyle);*/
	    	   	    	   
	    	   HSSFCell valorProntoPago = headerRow.createCell(13);
	    	   valorProntoPago.setCellValue("Subtotal");
	    	   valorProntoPago.setCellStyle(headerCellStyle);
	    	   
	    	  	    	   
	    	   int i = 1;
	    	   for (Factura factura : facturas) {
	    		   
	    		   String s,s1,s2,s3,s4;
	    		   HSSFRow bodyRow = workSheet.createRow(i);
	    		   
	    		   HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
	    		   bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
	    		   bodyCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    		    		   
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
	    		   fechaFacturaValue.setCellValue(s=String.valueOf(factura.getFechaFact()));
	    		   fechaFacturaValue.setCellStyle(bodyCellStyle);
	    		   
	    		   HSSFCell  montoPagoValue = bodyRow.createCell(4);
	    		   montoPagoValue.setCellValue(s=String.valueOf(factura.getValorPago()));
	    		   montoPagoValue.setCellStyle(bodyCellStyle);
	    		   
	    		   HSSFCell  formaPagoValue = bodyRow.createCell(5);
	    		   formaPagoValue.setCellValue(factura.getPago().getFormaPago());
	    		   formaPagoValue.setCellStyle(bodyCellStyle);
	    		   
	    		   HSSFCell  docFacSapValue = bodyRow.createCell(6);
	    		   docFacSapValue.setCellValue(factura.getDocFacSap());
	    		   docFacSapValue.setCellStyle(bodyCellStyle);
	    		   
	    		   HSSFCell  numLoteValue = bodyRow.createCell(7);
	    		   numLoteValue.setCellValue(factura.getFactNumLote());
	    		   numLoteValue.setCellStyle(bodyCellStyle);
	    		   
	    		   HSSFCell  factReferenciaValue = bodyRow.createCell(8);
	    		   factReferenciaValue.setCellValue(factura.getFactReferencia());
	    		   factReferenciaValue.setCellStyle(bodyCellStyle);
	    		   
	     		   HSSFCell numCompValue = bodyRow.createCell(9);
	     		   numCompValue.setCellValue(factura.getNumComp());
	     		   numCompValue.setCellStyle(bodyCellStyle);
	     		   
	     		   HSSFCell fechaCompValue = bodyRow.createCell(10);
	     		   fechaCompValue.setCellValue(s=String.valueOf(factura.getFechaComp()));
	     		   fechaCompValue.setCellStyle(bodyCellStyle);
	     		   
	     		   HSSFCell centroFacturaValue = bodyRow.createCell(11);
	     		   centroFacturaValue.setCellValue(factura.getCentroFactura());
	     		   centroFacturaValue.setCellStyle(bodyCellStyle);
	    		   
	     		  /*HSSFCell fechaCreacionValue = bodyRow.createCell(12);
	     		   fechaCreacionValue.setCellValue(s=String.valueOf(factura.getCreateAt().toString()));
	     		   fechaCreacionValue.setCellStyle(bodyCellStyle);*/
	     		   
	     		   HSSFCell estadoFacturaValue = bodyRow.createCell(12);
	     		   estadoFacturaValue.setCellValue(factura.isFactComprobacion());
	     		   estadoFacturaValue.setCellStyle(bodyCellStyle);
	     		   
	     		  /* HSSFCell prontoPagoValue = bodyRow.createCell(13);
	     		   prontoPagoValue.setCellValue(factura.getPpago().getTipoAccion());
	     		   prontoPagoValue.setCellStyle(bodyCellStyle);*/
	     		   
	     		   HSSFCell valorProntoPagoValue = bodyRow.createCell(13);
	     		   valorProntoPagoValue.setCellValue(s1=String.valueOf(factura.getValorPPago()));
	     		   valorProntoPagoValue.setCellStyle(bodyCellStyle);
	    		 
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

	@Override
	public Page<Factura> buscarDatos(Pageable pageable, String codCliente) {
		return (Page<Factura>) facturaRepository.buscarDatos(pageable, codCliente);
	}

}
