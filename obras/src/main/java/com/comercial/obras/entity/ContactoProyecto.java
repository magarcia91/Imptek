package com.comercial.obras.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="contacto")
public class ContactoProyecto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long idContact;
	
	private Long idObr;
	private String nombreContact;
	private String telefonoContact;
	private String correoContact;
	private String contactoProyectoObr;
	private String contactoCiudadObr;
	private String contactoZonaObr;
	private String contactoSectorObr;
	private String contactoConstructoraObr;
	private String contactoAvanceObr;
	private String contactoStatusObr;
	
	@Transient
	private Obras obraSel;
		
	
    @ManyToOne
    @JoinColumn(name = "fk_idObr")
    private ContactoProyecto contacto;
	
    @OneToMany(mappedBy="contacto")
    private List<Obras> obra;
	
	
	public ContactoProyecto() {
		super();		
	}

	public ContactoProyecto(Long idContact, Long idObr, String nombreContact, String telefonoContact, String correoContact,
			String contactoProyectoObr, String contactoCiudadObr, String contactoZonaObr ,String contactoSectorObr,
			String contactoConstructoraObr, String contactoAvanceObr, String contactoStatusObr,
			ContactoProyecto contacto) {
		
		super();
		this.idContact = idContact;
		this.idObr = idObr;
		this.nombreContact = nombreContact;
		this.telefonoContact = telefonoContact;
		this.correoContact = correoContact;
		this.contactoProyectoObr = contactoProyectoObr;
		this.contactoCiudadObr = contactoCiudadObr;
		this.contactoZonaObr = contactoZonaObr;
		this.contactoSectorObr = contactoSectorObr;
		this.contactoConstructoraObr = contactoConstructoraObr;
		this.contactoAvanceObr = contactoAvanceObr;
		this.contactoStatusObr = contactoStatusObr;
		this.contacto = contacto;
		
	}
	
	
	public Long getIdContact() {
		return idContact;
	}


	public void setIdContact(Long idContact) {
		this.idContact = idContact;
	}
	
	public Long getIdObr() {
		return idObr;
	}

	public void setIdObr(Long idObr) {
		this.idObr = idObr;
	}

	public String getNombreContact() {
		return nombreContact;
	}


	public void setNombreContact(String nombreContact) {
		this.nombreContact = nombreContact;
	}


	public String getTelefonoContact() {
		return telefonoContact;
	}


	public void setTelefonoContact(String telefonoContact) {
		this.telefonoContact = telefonoContact;
	}


	public String getCorreoContact() {
		return correoContact;
	}


	public void setCorreoContact(String correoContact) {
		this.correoContact = correoContact;
	}


	public String getContactoProyectoObr() {
		return contactoProyectoObr;
	}


	public void setContactoProyectoObr(String contactoProyectoObr) {
		this.contactoProyectoObr = contactoProyectoObr;
	}


	public String getContactoCiudadObr() {
		return contactoCiudadObr;
	}


	public void setContactoCiudadObr(String contactoCiudadObr) {
		this.contactoCiudadObr = contactoCiudadObr;
	}

	
		
	public String getContactoZonaObr() {
		return contactoZonaObr;
	}

	public void setContactoZonaObr(String contactoZonaObr) {
		this.contactoZonaObr = contactoZonaObr;
	}

	public String getContactoSectorObr() {
		return contactoSectorObr;
	}


	public void setContactoSectorObr(String contactoSectorObr) {
		this.contactoSectorObr = contactoSectorObr;
	}


	public String getContactoConstructoraObr() {
		return contactoConstructoraObr;
	}


	public void setContactoConstructoraObr(String contactoConstructoraObr) {
		this.contactoConstructoraObr = contactoConstructoraObr;
	}


	public String getContactoAvanceObr() {
		return contactoAvanceObr;
	}


	public void setContactoAvanceObr(String contactoAvanceObr) {
		this.contactoAvanceObr = contactoAvanceObr;
	}


	public String getContactoStatusObr() {
		return contactoStatusObr;
	}


	public void setContactoStatusObr(String contactoStatusObr) {
		this.contactoStatusObr = contactoStatusObr;
	}


	public ContactoProyecto getContacto() {
		return contacto;
	}


	public void setContacto(ContactoProyecto contacto) {
		this.contacto = contacto;
	}

	public Obras getObraSel() {
		return obraSel;
	}

	public void setObraSel(Obras obraSel) {
		this.obraSel = obraSel;
	}
	
}
