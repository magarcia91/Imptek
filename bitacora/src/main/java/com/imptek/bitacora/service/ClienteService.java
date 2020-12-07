package com.imptek.bitacora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;

public interface ClienteService {
	
	public List<Cliente> findAll();

	Page<Cliente> getAll(Pageable pageable);
	
	/*Page<Cliente> getAll1(Pageable pageable);*/
	
	void save(Cliente cliente);

	public Cliente findOne(Long idCliente);
	
	public Optional<Cliente> findByCodCliente(String codCliente);

	public void delete(Long idCliente);
	
	//public boolean checkClientExist(Cliente cliente) throws Exception;
	
	//public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura factura);
	
	//public Producto findProductoByid(Long id);
	
	public Factura findFacturaById(Long idFact);
	
	public void deleteFactura(Long idFact);
	
	public Factura fetchByIdWithItemFacturaWithProducto(Long id);
	
	public Cliente fetchByIdWithFacturas(Long idCliente);
}
