package com.imptek.bitacora.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;
import com.imptek.bitacora.entity.Factura;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FacturasReport {

	
	//Aqui debe cambiar a la clase que desee hacer el reporte
		private final Collection<Factura> list;

		//Aqui debe cambiar a la clase que desee hacer el reporte
		public FacturasReport(Collection<Factura> collection) {
			list = new ArrayList<>(collection);
		}

		public JasperPrint getReport() throws ColumnBuilderException, JRException, ClassNotFoundException {
			Style headerStyle = createHeaderStyle();
			Style detailTextStyle = createDetailTextStyle();
			Style detailNumberStyle = createDetailNumberStyle();
			DynamicReport dynaReport = getReport(headerStyle, detailTextStyle, detailNumberStyle);
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
					new JRBeanCollectionDataSource(list));
			return jp;
		}

		private Style createHeaderStyle() {		
			return new StyleBuilder(true)
					.setFont(Font.VERDANA_MEDIUM_BOLD)
					.setBorder(Border.THIN())
					.setBorderBottom(Border.PEN_2_POINT())
					.setBorderColor(Color.BLACK)
					.setBackgroundColor(Color.ORANGE)
					.setTextColor(Color.BLACK)
					.setHorizontalAlign(HorizontalAlign.CENTER)
					.setVerticalAlign(VerticalAlign.MIDDLE)
					.setTransparency(Transparency.OPAQUE)
					.build();
		}

		private Style createDetailTextStyle() {
			return new StyleBuilder(true)
					.setFont(Font.VERDANA_MEDIUM)
					.setBorder(Border.DOTTED())
					.setBorderColor(Color.BLACK)
					.setTextColor(Color.BLACK)
					.setHorizontalAlign(HorizontalAlign.CENTER)
					.setVerticalAlign(VerticalAlign.MIDDLE)
					.setPaddingLeft(5)
					.build();
		}

		private Style createDetailNumberStyle() {
			return new StyleBuilder(true)
					.setFont(Font.VERDANA_MEDIUM)
					.setBorder(Border.DOTTED())
					.setBorderColor(Color.BLACK)
					.setTextColor(Color.BLACK)
					.setHorizontalAlign(HorizontalAlign.CENTER)
					.setVerticalAlign(VerticalAlign.MIDDLE)
					.setPaddingRight(5)
					.setPattern("#,##0.00")
					.build();
		}

		private AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle, Style detailStyle)
				throws ColumnBuilderException {
			return ColumnBuilder.getNew()
					.setColumnProperty(property, type.getName())
					.setTitle(title)
					.setWidth(Integer.valueOf(width))
					.setStyle(detailStyle)
					.setHeaderStyle(headerStyle)
					.build();
		}

		
		//Este metodo cuando quiera hacer un nuevo reporte debe ser modificado
		private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
				throws ColumnBuilderException, ClassNotFoundException {
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(new Date());	
			
			//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			//String fechaCotObrEx = format1.format("fechaCotObrEx");
		
			DynamicReportBuilder report = new DynamicReportBuilder();
			
			//Modificar la columna deseada, el tipo de dato, y un titulo para la columna
			AbstractColumn columnCodCliente = createColumn("cliente.codCliente", String.class, "CodCliente", 30, headerStyle, detailNumStyle);
			AbstractColumn columnNomCliente = createColumn("cliente.nomCliente", String.class, "Cliente", 30, headerStyle, detailNumStyle);
			AbstractColumn columnNumFact = createColumn("numFact", String.class, "#Factura", 50, headerStyle, detailTextStyle);
			AbstractColumn columnFechaFact = createColumn("fechaFact", Date.class, "Fecha", 30, headerStyle, detailNumStyle);
			AbstractColumn columnPagoFactura = createColumn("pago.formaPago", String.class, "Pago", 30, headerStyle, detailNumStyle);
			AbstractColumn columnNumComprobante = createColumn("docFacSap", String.class, "#Comprobante", 70, headerStyle, detailTextStyle);
			AbstractColumn columnNumLote = createColumn("factNumLote", String.class, "#Lote", 30, headerStyle, detailTextStyle);
			AbstractColumn columnNumReferencia = createColumn("factReferencia", String.class, "#Referencia", 30, headerStyle, detailTextStyle);
			AbstractColumn columnNumCompensacion = createColumn("numComp", String.class, "#Compensación", 70, headerStyle, detailTextStyle);
			AbstractColumn columnCentroFactura = createColumn("centroFactura", String.class, "Centro", 30, headerStyle, detailTextStyle);
			AbstractColumn columnEstado = createColumn("factComprobacion", Boolean.class, "Estado", 30, headerStyle, detailTextStyle);
			
			//Agregar cada columna al reporte
			report.addColumn(columnCodCliente).addColumn(columnNomCliente).addColumn(columnNumFact).addColumn(columnFechaFact).addColumn(columnPagoFactura)
			.addColumn(columnNumComprobante).addColumn(columnNumLote).addColumn(columnNumReferencia).addColumn(columnNumCompensacion).addColumn(columnCentroFactura)
			.addColumn(columnEstado);
			StyleBuilder titleStyle = new StyleBuilder(true);
			titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
			titleStyle.setFont(new Font(20, null, true));
			StyleBuilder subTitleStyle = new StyleBuilder(true);
			subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
			subTitleStyle.setFont(new Font(Font.BIG, null, true));
			//Cambiar el titulo
			report.setTitle("Facturas Report");
			report.setTitleStyle(titleStyle.build());
			//Cambiar el subtitulo
			report.setSubtitle("Lista de Facturas"+"\\n"+"Fecha de creación: "+dateString+"\\n");
			report.setSubtitleStyle(subTitleStyle.build());
			report.setUseFullPageWidth(true);
			return report.build();
		}
}
