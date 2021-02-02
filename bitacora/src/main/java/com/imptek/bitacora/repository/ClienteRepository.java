package com.imptek.bitacora.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

	@Query("select c from Cliente c left join fetch c.facturas f where c.idCliente= ?1")
	public Cliente fetchByIdWithFacturas(Long idCliente);

	Page<Cliente> findAll(Pageable pageable);

	public Optional<Cliente> findByCodCliente(String codCliente);
	
	@Query("SELECT c FROM Cliente c WHERE (:codCliente is null or c.codCliente = :codCliente)")
	Page<Cliente> buscarDatos(Pageable pageable,@Param("codCliente")String codCliente);
	
	
	//public Optional<Cliente> findByCodCliente(String codCliente);	
	//Page<Cliente> findAll1(Pageable pageable);
}
