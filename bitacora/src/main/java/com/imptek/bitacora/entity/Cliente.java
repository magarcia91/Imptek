package com.imptek.bitacora.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;

	private String nomCliente;
	private String codCliente;
	private String lineaCliente;
	private String cedulaCliente;
	private Date createAt;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Factura> facturas;

	public Cliente() {
		facturas = new ArrayList<Factura>();
	}

	public Cliente(Long idCliente, String nomCliente, String codCliente, String lineaCliente, String cedulaCliente, Date createAt,
			List<Factura> facturas) {
		super();
		this.idCliente = idCliente;
		this.nomCliente = nomCliente;
		this.codCliente = codCliente;
		this.lineaCliente = lineaCliente;
		this.cedulaCliente = cedulaCliente;
		this.createAt = createAt;
		this.facturas = facturas;
	}



	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomCliente() {
		return nomCliente;
	}

	public void setNomCliente(String nomCliente) {
		this.nomCliente = nomCliente;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
	
	public String getLineaCliente() {
		return lineaCliente;
	}

	public void setLineaCliente(String lineaCliente) {
		this.lineaCliente = lineaCliente;
	}

	public String getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura(Factura factura) {
		this.facturas.add(factura);
	}

	@Override
	public String toString() {
		return this.nomCliente;
	}
	
	

}
