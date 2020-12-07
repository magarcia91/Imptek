package com.imptek.bitacora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;
import com.imptek.bitacora.repository.ClienteRepository;
import com.imptek.bitacora.repository.FacturaRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FacturaRepository facturaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteRepository.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
				
		clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

	
	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaRepository.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchByIdWithItemFacturaWithProducto(Long id) {
		return facturaRepository.fetchByIdWithItemFacturaWithProducto(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithFacturas(Long id) {
		return clienteRepository.fetchByIdWithFacturas(id);
	}

	@Override
	public Page<Cliente> getAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
	public Optional<Cliente> findByCodCliente(String codCliente) {
		return clienteRepository.findByCodCliente(codCliente);
	}

/*	@Override
	public boolean checkClientExist(Cliente cliente) throws Exception {
	
		Optional<Cliente> clienteFound = clienteRepository.findByCodCliente(cliente.getCodCliente());
		if (clienteFound.isPresent()) {			
			throw new Exception("Cliente ya existe");	
			
		} 
		return false;
	}*/

}
