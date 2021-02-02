package com.imptek.bitacora.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFact;
	private String numFact;
	private Date fechaFact;	
	private String numComp;		
	private Date fechaComp;	
	private String docFacSap;
	private String factNumLote;
	private String factReferencia;
	private Double valorPago;
	private Double valorPPago;
	@Lob
	private String fotoLote;
	@Lob
	private String fotoPago;	
	private String centroFactura; 
	private boolean factComprobacion;
	//private boolean factEstado;
	//private boolean factCatalogo;
	private Date createAt;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Catalogos catalogos;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPago")
	private Pago pago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAccion")
	private ProntoPago ppago;
	    
	@Transient
	private String boLote,boNumLote;
	
	@Transient
	private String boPago,boPPago,boRef;

	
/*	@OneToMany(mappedBy="factura")
	//@JoinColumn(name = "idFact")
	private List<Pago> pago;*/

	/*@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}*/

	public Factura() {
		super();
	}

	
	public Factura(Long idFact, String numFact, Date fechaFact, String numComp, Date fechaComp,
			String docFacSap, String factNumLote, String factReferencia,Double valorPago,Double valorPPago,String fotoLote, String fotoPago, String centroFactura,
			boolean factComprobacion, Date createAt, Cliente cliente, Pago pago,ProntoPago ppago) {
		
		this.idFact = idFact;
		this.numFact = numFact;
		this.fechaFact = fechaFact;
		this.numComp = numComp;
		this.fechaComp = fechaComp;
		this.docFacSap = docFacSap;
		this.factNumLote = factNumLote;
		this.factReferencia = factReferencia;
		this.valorPago = valorPago;
		this.valorPPago = valorPPago;
		this.fotoLote = fotoLote;
		this.fotoPago = fotoPago;
		this.centroFactura = centroFactura;
		this.factComprobacion = factComprobacion;
		//this.factEstado = factEstado;
		//this.factCatalogo = factCatalogo;
		this.createAt = createAt;
		this.cliente = cliente;
		this.pago = pago;
		this.ppago = ppago;
	}



	public Long getIdFact() {
		return idFact;
	}

	public void setIdFact(Long idFact) {
		this.idFact = idFact;
	}

	public String getNumFact() {
		return numFact;
	}

	public void setNumFact(String numFact) {
		this.numFact = numFact;
	}

	public Date getFechaFact() {
		return fechaFact;
	}

	public void setFechaFact(Date fechaFact) {
		this.fechaFact = fechaFact;
	}

	public String getNumComp() {
		return numComp;
	}

	public void setNumComp(String numComp) {
		this.numComp = numComp;
	}

	public Date getFechaComp() {
		return fechaComp;
	}

	public void setFechaComp(Date fechaComp) {
		this.fechaComp = fechaComp;
	}

	
	public String getDocFacSap() {
		return docFacSap;
	}

	public void setDocFacSap(String docFacSap) {
		this.docFacSap = docFacSap;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Pago getPago() {
		return pago;
	}


	public void setPago(Pago pago) {
		this.pago = pago;
	}

	
	public ProntoPago getPpago() {
		return ppago;
	}


	public void setPpago(ProntoPago ppago) {
		this.ppago = ppago;
	}


	public String getFactNumLote() {
		return factNumLote;
	}


	public void setFactNumLote(String factNumLote) {
		this.factNumLote = factNumLote;
	}

		
	public String getFactReferencia() {
		return factReferencia;
	}


	public void setFactReferencia(String factReferencia) {
		this.factReferencia = factReferencia;
	}

	
	public Double getValorPago() {
		return valorPago;
	}


	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}
	
	public Double getValorPPago() {
		return valorPPago;
	}


	public void setValorPPago(Double valorPPago) {
		this.valorPPago = valorPPago;
	}


	public String getFotoLote() {
		return fotoLote;
	}


	public void setFotoLote(String fotoLote) {
		this.fotoLote = fotoLote;
	}

	
	public String getFotoPago() {
		return fotoPago;
	}


	public void setFotoPago(String fotoPago) {
		this.fotoPago = fotoPago;
	}
	
	
	public String getCentroFactura() {
		return centroFactura;
	}


	public void setCentroFactura(String centroFactura) {
		this.centroFactura = centroFactura;
	}


	public boolean isFactComprobacion() {
		return factComprobacion;
	}


	public void setFactComprobacion(boolean factComprobacion) {
		this.factComprobacion = factComprobacion;
	}

	public String getBoLote() {
		return boLote;
	}


	public void setBoLote(String boLote) {
		this.boLote = boLote;
	}


	public String getBoPago() {
		return boPago;
	}


	public void setBoPago(String boPago) {
		this.boPago = boPago;
	}

	
	public String getBoPPago() {
		return boPPago;
	}


	public void setBoPPago(String boPPago) {
		this.boPPago = boPPago;
	}


	public String getBoNumLote() {
		return boNumLote;
	}


	public void setBoNumLote(String boNumLote) {
		this.boNumLote = boNumLote;
	}


	public String getBoRef() {
		return boRef;
	}


	public void setBoRef(String boRef) {
		this.boRef = boRef;
	}


/*	public boolean isFactEstado() {
		return factEstado;
	}


	public void setFactEstado(boolean factEstado) {
		this.factEstado = factEstado;
	}*/

		
/*	public boolean isFactCatalogo() {
		return factCatalogo;
	}


	public void setFactCatalogo(boolean factCatalogo) {
		this.factCatalogo = factCatalogo;
	}*/
				
/*	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total = 0.0;
		int size = items.size();
		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte();
		}
		return total;
	}*/

}
