package com.imptek.bitacora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;

public interface ClienteService {
	
	public List<Cliente> findAll();

	Page<Cliente> getAll(Pageable pageable);
	
	void save(Cliente cliente);

	public Cliente findOne(Long idCliente);
	
	public Optional<Cliente> findByCodCliente(String codCliente);

	public void delete(Long idCliente);
	
	public void saveFactura(Factura factura);
	
	public Factura findFacturaById(Long idFact);
	
	public void deleteFactura(Long idFact);
	
	public Factura fetchByIdWithItemFacturaWithProducto(Long id);
	
	public Cliente fetchByIdWithFacturas(Long idCliente);
	
	@Query("SELECT c FROM Cliente c WHERE (:codCliente is null or c.codCliente = :codCliente)")
	Page<Cliente> buscarDatos(Pageable pageable,@Param("codCliente")String codCliente);
	
	//Page<Cliente> getAll1(Pageable pageable);
	//public boolean checkClientExist(Cliente cliente) throws Exception;	
	//public List<Producto> findByNombre(String term);
	//public Producto findProductoByid(Long id);
}
