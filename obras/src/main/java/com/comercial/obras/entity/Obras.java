package com.comercial.obras.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;



@Entity
@Table(name="obras")
public class Obras {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idObr")
	private Long idObr;
	@Column(name="ciudadObr")
	private String ciudadObr;
	@Column(name="zonaObr")
	private String zonaObr;
	@Column(name="sectorObr")
	private String sectorObr;
	@Column(name="nombreObr")
	private String nombreObr;
	@Column(name="statusObr")
	private String statusObr;
	@Column(name="constructoraObr")
	private String constructoraObr;
	@Column(name="avanceObr")
	private String avanceObr;
	@Column(name="fileType")
	private String fileType;
		
	@Transient
	private MultipartFile file;
	
	
	@OneToMany(mappedBy="contacto")
    private List<Obras> obra;
	
	@ManyToOne
    @JoinColumn(name = "fk_contacto")
    private ContactoProyecto contacto;
	
	public Obras() {
		
	}

	
	public Obras(String ciudadObr, String zonaObr, String sectorObr, String nombreObr, String statusObr,
			String constructoraObr, String avanceObr ,String fileType) {
				
		this.ciudadObr = ciudadObr;
		this.zonaObr = zonaObr;
		this.sectorObr = sectorObr;
		this.nombreObr = nombreObr;
		this.statusObr = statusObr;
		this.constructoraObr = constructoraObr;
		this.avanceObr = avanceObr;
		this.fileType = fileType;
	
	}

	public Long getIdObr() {
		return idObr;
	}


	public void setIdObr(Long idObr) {
		this.idObr = idObr;
	}


	public String getCiudadObr() {
		return ciudadObr;
	}


	public void setCiudadObr(String ciudadObr) {
		this.ciudadObr = ciudadObr;
	}


	public String getZonaObr() {
		return zonaObr;
	}


	public void setZonaObr(String zonaObr) {
		this.zonaObr = zonaObr;
	}


	public String getSectorObr() {
		return sectorObr;
	}


	public void setSectorObr(String sectorObr) {
		this.sectorObr = sectorObr;
	}


	public String getNombreObr() {
		return nombreObr;
	}


	public void setNombreObr(String nombreObr) {
		this.nombreObr = nombreObr;
	}


	public String getStatusObr() {
		return statusObr;
	}


	public void setStatusObr(String statusObr) {
		this.statusObr = statusObr;
	}


	public String getConstructoraObr() {
		return constructoraObr;
	}


	public void setConstructoraObr(String constructoraObr) {
		this.constructoraObr = constructoraObr;
	}
	
		
	public String getAvanceObr() {
		return avanceObr;
	}


	public void setAvanceObr(String avanceObr) {
		this.avanceObr = avanceObr;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public List<Obras> getObra() {
		return obra;
	}


	public void setObra(List<Obras> obra) {
		this.obra = obra;
	}


	public ContactoProyecto getContacto() {
		return contacto;
	}


	public void setContacto(ContactoProyecto contacto) {
		this.contacto = contacto;
	}
				
}
