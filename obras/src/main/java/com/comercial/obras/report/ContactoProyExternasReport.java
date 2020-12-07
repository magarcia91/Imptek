package com.comercial.obras.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.comercial.obras.entity.ContactoProyectoEx;
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

public class ContactoProyExternasReport {

	private final Collection<ContactoProyectoEx> list;

	public ContactoProyExternasReport(Collection<ContactoProyectoEx> c) {
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

	private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(new Date());
		
		DynamicReportBuilder report = new DynamicReportBuilder();
		AbstractColumn columnProyObrEx = createColumn("contProyObrEx", String.class, "PROYECTO", 20, headerStyle, detailTextStyle);
		AbstractColumn columnContCiuObr = createColumn("contCiuObrEx", String.class, "CIUDAD", 15, headerStyle, detailTextStyle);
		AbstractColumn columnContZonObr = createColumn("contZonObrEx", String.class, "ZONA", 15, headerStyle, detailNumStyle);
		AbstractColumn columnContAvanObr = createColumn("contAvanObrEx", String.class, "AVANCE", 15, headerStyle, detailNumStyle);
		AbstractColumn columnNombreContEx = createColumn("nombreContactEx", String.class, "CONTACTO", 20, headerStyle, detailNumStyle);
		AbstractColumn columnTelfContEx = createColumn("telefonoContactEx", String.class, "TELEFONO", 20, headerStyle, detailNumStyle);
		AbstractColumn columnCorreoContEx = createColumn("correoContactEx", String.class, "CORREO", 30, headerStyle, detailNumStyle);
		report.addColumn(columnProyObrEx).addColumn(columnContCiuObr).addColumn(columnContZonObr).addColumn(columnContAvanObr).addColumn(columnNombreContEx).addColumn(columnTelfContEx).addColumn(columnCorreoContEx);
		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(20, null, true));
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(Font.BIG, null, true));
		report.setTitle("Contactos Obras Externas Report");
		report.setTitleStyle(titleStyle.build());
		report.setSubtitle("Listado Contactos Obras Externas"+"\\n"+"Fecha de creación: "+dateString+"\\n");
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}
}