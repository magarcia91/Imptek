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
@Table(name="pago")
public class Pago {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="idPago")
	private int idPago;
	@Column(name="formaPago")
	private String formaPago;
		
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)//@JoinColumn(name = "idFact")
	private List<Factura> factura;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idFact")
	private Factura factura;
	*/
		
	public Pago() {
		super();		
	}

	public Pago(int idPago, String formaPago, List<Factura> factura) {
		super();
		this.idPago = idPago;
		this.formaPago = formaPago;
		this.factura = factura;
	}


	public int getIdPago() {
		return idPago;
	}


	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}


	public String getFormaPago() {
		return formaPago;
	}


	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	public List<Factura> getFactura() {
		return factura;
	}


	public void setFactura(List<Factura> factura) {
		this.factura = factura;
	}

		
			
}
