package com.imptek.bitacora.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="prontopago")
public class ProntoPago {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="idAccion")
	private int idAccion;
	@Column(name="tipoAccion")
	private String tipoAccion;
		
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)//@JoinColumn(name = "idFact")
	private List<Factura> factura;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idFact")
	private Factura factura;
	*/
		
	public ProntoPago() {
		super();		
	}

	public ProntoPago(int idAccion, String tipoAccion, List<Factura> factura) {
		super();
		this.idAccion = idAccion;
		this.tipoAccion = tipoAccion;
		this.factura = factura;
	}


	public int getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public List<Factura> getFactura() {
		return factura;
	}


	public void setFactura(List<Factura> factura) {
		this.factura = factura;
	}

		
			
}
