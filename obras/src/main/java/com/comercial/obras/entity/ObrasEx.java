package com.comercial.obras.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="obrasEx")
public class ObrasEx {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idObrEx")
	private int idObrEx;
	@Column(name="ciudadObrEx")
	private String ciudadObrEx;
	@Column(name="zonaObrEx")
	private String zonaObrEx;
	@Column(name="sectorObrEx")
	private String sectorObrEx;
	@Column(name="nombreObrEx")
	private String nombreObrEx;
	@Column(name="statusObrEx")
	private String statusObrEx;
	@Column(name="constObrEx")
	private String constObrEx;
	@Column(name="fechaCotObrEx")
	private Date fechaCotObrEx;
	@Column(name="fechaCieObrEx")
	private Date fechaCieObrEx;
	@Column(name="cotizacionObrEx")
	private Double cotizacionObrEx;
	@Column(name="comentObrEx")
	private String comentObrEx;
	/*@Column(name="avanceObrEx")
	private String avanceObrEx;*/
	
	@ManyToOne
    @JoinColumn(name = "fk_contacto_ex")
    private ContactoProyectoEx contactoEx;
	
	/*@OneToMany(mappedBy="contactoEx")
    private List<ObrasEx> obraEx;*/
	
	/*
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="obras_ex_contacto_ex",
	joinColumns= {@JoinColumn(name="idObrEx")},
	inverseJoinColumns= {@JoinColumn (name="idContactEx")})
    private List<ContactoProyectoEx> contactoEx;*/

		
	public ObrasEx() {
	
	}

	public ObrasEx(int idObrEx, String ciudadObrEx, String zonaObrEx, String sectorObrEx, String nombreObrEx,
			String statusObrEx, String constObrEx, Date fechaCotObrEx, Date fechaCieObrEx, Double cotizacionObrEx,
			String comentObrEx, String avanceObrEx, ContactoProyectoEx contactoEx) {
		super();
		this.idObrEx = idObrEx;
		this.ciudadObrEx = ciudadObrEx;
		this.zonaObrEx = zonaObrEx;
		this.sectorObrEx = sectorObrEx;
		this.nombreObrEx = nombreObrEx;
		this.statusObrEx = statusObrEx;
		this.constObrEx = constObrEx;
		this.fechaCotObrEx = fechaCotObrEx;
		this.fechaCieObrEx = fechaCieObrEx;
		this.cotizacionObrEx = cotizacionObrEx;
		this.comentObrEx = comentObrEx;
		//this.avanceObrEx = avanceObrEx;
		//this.contactoEx = contactoEx;
	}


	public int getIdObrEx() {
		return idObrEx;
	}


	public void setIdObrEx(int idObrEx) {
		this.idObrEx = idObrEx;
	}


	public String getCiudadObrEx() {
		return ciudadObrEx;
	}


	public void setCiudadObrEx(String ciudadObrEx) {
		this.ciudadObrEx = ciudadObrEx;
	}


	public String getZonaObrEx() {
		return zonaObrEx;
	}


	public void setZonaObrEx(String zonaObrEx) {
		this.zonaObrEx = zonaObrEx;
	}


	public String getSectorObrEx() {
		return sectorObrEx;
	}


	public void setSectorObrEx(String sectorObrEx) {
		this.sectorObrEx = sectorObrEx;
	}


	public String getNombreObrEx() {
		return nombreObrEx;
	}


	public void setNombreObrEx(String nombreObrEx) {
		this.nombreObrEx = nombreObrEx;
	}


	public String getStatusObrEx() {
		return statusObrEx;
	}


	public void setStatusObrEx(String statusObrEx) {
		this.statusObrEx = statusObrEx;
	}


	public String getConstObrEx() {
		return constObrEx;
	}


	public void setConstObrEx(String constObrEx) {
		this.constObrEx = constObrEx;
	}


	public Date getFechaCotObrEx() {
		return fechaCotObrEx;
	}


	public void setFechaCotObrEx(Date fechaCotObrEx) {
		this.fechaCotObrEx = fechaCotObrEx;
	}


	public Date getFechaCieObrEx() {
		return fechaCieObrEx;
	}


	public void setFechaCieObrEx(Date fechaCieObrEx) {
		this.fechaCieObrEx = fechaCieObrEx;
	}


	public Double getCotizacionObrEx() {
		return cotizacionObrEx;
	}


	public void setCotizacionObrEx(Double cotizacionObrEx) {
		this.cotizacionObrEx = cotizacionObrEx;
	}

	

	public String getComentObrEx() {
		return comentObrEx;
	}


	public void setComentObrEx(String comentObrEx) {
		this.comentObrEx = comentObrEx;
	}

	/*public String getAvanceObrEx() {
		return avanceObrEx;
	}


	public void setAvanceObrEx(String avanceObrEx) {
		this.avanceObrEx = avanceObrEx;
	}*/


	public ContactoProyectoEx getContacto() {
		return contactoEx;
	}


	public void setContacto(ContactoProyectoEx contactoEx) {
		this.contactoEx = contactoEx;
	}
	

	/*
	public List<ContactoProyectoEx> getContactoEx() {
		return contactoEx;
	}


	public void setContactoEx(List<ContactoProyectoEx> contactoEx) {
		this.contactoEx = contactoEx;
	}*/
}
