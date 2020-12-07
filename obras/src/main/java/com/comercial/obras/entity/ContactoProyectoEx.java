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
@Table(name="contactoEx")
public class ContactoProyectoEx {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private int idContactEx;
	private int idObrEx;
	private String nombreContactEx;
	private String telefonoContactEx;
	private String correoContactEx;
	private String contProyObrEx;
	private String contCiuObrEx;
	private String contZonObrEx;
	//private String contSecObrEx;
	private String contConsObrEx;
	private String contAvanObrEx;
	private String contStatObrEx;
		
	
    @ManyToOne
    @JoinColumn(name = "fk_idObrEx")
    private ContactoProyectoEx contactoEx;
	
    @OneToMany(mappedBy="contactoEx")
    private List<ObrasEx> obraEx;
    
    @Transient
	private ObrasEx obraSel;
	
	
	public ContactoProyectoEx() {
		super();		
	}

	public ContactoProyectoEx(int idContactEx, int idObrEx, String nombreContactEx, String telefonoContactEx, String correoContactEx,
			String contProyObrEx, String contCiuObrEx, String contZonObrEx ,String contSecObrEx,
			String contConsObrEx, String contAvanObrEx, String contStatObrEx,
			ContactoProyectoEx contactoEx) {
		
		super();
		this.idContactEx = idContactEx;
		this.idObrEx = idObrEx;
		this.nombreContactEx = nombreContactEx;
		this.telefonoContactEx = telefonoContactEx;
		this.correoContactEx = correoContactEx;
		this.contProyObrEx = contProyObrEx;
		this.contCiuObrEx = contCiuObrEx;
		this.contZonObrEx = contZonObrEx;
		//this.contSecObrEx = contSecObrEx;
		this.contConsObrEx = contConsObrEx;
		this.contAvanObrEx = contAvanObrEx;
		this.contStatObrEx = contStatObrEx;
		this.contactoEx = contactoEx;
		
	}

	public int getIdContactEx() {
		return idContactEx;
	}

	public void setIdContactEx(int idContactEx) {
		this.idContactEx = idContactEx;
	}

	public int getIdObrEx() {
		return idObrEx;
	}

	public void setIdObrEx(int idObrEx) {
		this.idObrEx = idObrEx;
	}

	public String getNombreContactEx() {
		return nombreContactEx;
	}

	public void setNombreContactEx(String nombreContactEx) {
		this.nombreContactEx = nombreContactEx;
	}

	public String getTelefonoContactEx() {
		return telefonoContactEx;
	}

	public void setTelefonoContactEx(String telefonoContactEx) {
		this.telefonoContactEx = telefonoContactEx;
	}

	public String getCorreoContactEx() {
		return correoContactEx;
	}

	public void setCorreoContactEx(String correoContactEx) {
		this.correoContactEx = correoContactEx;
	}

	public String getContProyObrEx() {
		return contProyObrEx;
	}

	public void setContProyObrEx(String contProyObrEx) {
		this.contProyObrEx = contProyObrEx;
	}

	public String getContCiuObrEx() {
		return contCiuObrEx;
	}

	public void setContCiuObrEx(String contCiuObrEx) {
		this.contCiuObrEx = contCiuObrEx;
	}

	public String getContZonObrEx() {
		return contZonObrEx;
	}

	public void setContZonObrEx(String contZonObrEx) {
		this.contZonObrEx = contZonObrEx;
	}

/*	public String getContSecObrEx() {
		return contSecObrEx;
	}

	public void setContSecObrEx(String contSecObrEx) {
		this.contSecObrEx = contSecObrEx;
	}*/

	public String getContConsObrEx() {
		return contConsObrEx;
	}

	public void setContConsObrEx(String contConsObrEx) {
		this.contConsObrEx = contConsObrEx;
	}

	public String getContAvanObrEx() {
		return contAvanObrEx;
	}

	public void setContAvanObrEx(String contAvanObrEx) {
		this.contAvanObrEx = contAvanObrEx;
	}

	public String getContStatObrEx() {
		return contStatObrEx;
	}

	public void setContStatObrEx(String contStatObrEx) {
		this.contStatObrEx = contStatObrEx;
	}

	public ContactoProyectoEx getContactoEx() {
		return contactoEx;
	}

	public void setContactoEx(ContactoProyectoEx contactoEx) {
		this.contactoEx = contactoEx;
	}

	public List<ObrasEx> getObraEx() {
		return obraEx;
	}

	public void setObraEx(List<ObrasEx> obraEx) {
		this.obraEx = obraEx;
	}

	public ObrasEx getObraSel() {
		return obraSel;
	}

	public void setObraSel(ObrasEx obraSel) {
		this.obraSel = obraSel;
	}
	
	

		
}
