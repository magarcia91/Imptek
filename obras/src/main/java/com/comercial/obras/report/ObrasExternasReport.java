package com.comercial.obras.report;

import java.awt.Color;
import java.util.Date;

import java.util.ArrayList;
import java.util.Collection;

import com.comercial.obras.entity.ObrasEx;
import com.ibm.icu.text.SimpleDateFormat;

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

public class ObrasExternasReport {

	//Aqui debe cambiar a la clase que desee hacer el reporte
	private final Collection<ObrasEx> list;

	//Aqui debe cambiar a la clase que desee hacer el reporte
	public ObrasExternasReport(Collection<ObrasEx> c) {
		list = new ArrayList<>(c);
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
		AbstractColumn columnNombreObrEx = createColumn("nombreObrEx", String.class, "PROYECTO", 30, headerStyle, detailNumStyle);
		AbstractColumn columnObrEx = createColumn("ciudadObrEx", String.class, "CIUDAD", 20, headerStyle, detailTextStyle);
		//AbstractColumn columnSectorObrEx = createColumn("sectorObrEx", String.class, "SECTOR", 35, headerStyle, detailNumStyle);
		AbstractColumn columnZonaObrEx = createColumn("zonaObrEx", String.class, "ZONA", 20, headerStyle, detailNumStyle);
		AbstractColumn columnStatusObrEx = createColumn("statusObrEx", String.class, "ESTADO", 25, headerStyle, detailNumStyle);
		AbstractColumn columnConstObrEx = createColumn("constObrEx", String.class, "CONSTRUCTORA", 27, headerStyle, detailNumStyle);
		AbstractColumn columnCotizacionObrEx = createColumn("cotizacionObrEx", Double.class, "COTIZACIÓN", 20, headerStyle, detailNumStyle);
		//AbstractColumn columnFechaCotObrEx = createColumn(fechaCotObrEx, String.class, "FECHA", 12, headerStyle, detailNumStyle);
			
		
		//Agregar cada columna al reporte
		report.addColumn(columnNombreObrEx).addColumn(columnObrEx).addColumn(columnStatusObrEx).addColumn(columnZonaObrEx)
		.addColumn(columnConstObrEx).addColumn(columnCotizacionObrEx);
		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(20, null, true));
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(Font.BIG, null, true));
		//Cambiar el titulo
		report.setTitle("Obras Externas Report");
		report.setTitleStyle(titleStyle.build());
		//Cambiar el subtitulo
		report.setSubtitle("Listado de Obras Externas"+"\\n"+"Fecha de creación: "+dateString+"\\n");
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}
}