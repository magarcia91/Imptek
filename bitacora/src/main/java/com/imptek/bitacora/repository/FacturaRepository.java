package com.imptek.bitacora.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;

public interface FacturaRepository extends CrudRepository<Factura, Long> {
	
	@Query("SELECT f FROM Factura f join fetch f.cliente c WHERE f.id=?1")
	public Factura fetchByIdWithItemFacturaWithProducto(Long id);

	@Query("SELECT f FROM Factura f WHERE f.fechaFact >= :startDate AND f.fechaFact <= :endDate")
	public List<Factura> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	Page<Factura> findAll(Pageable pageable);
	
	@Query("SELECT f FROM Factura f WHERE f.cliente.codCliente=?1")
	Page<Factura> buscarDatos(Pageable pageable,@Param("codCliente")String codCliente);
	
	
	//	@Query(value = "SELECT * FROM facturas WHERE fecha_fact >= 'startDate' AND fecha_fact <= 'endDate'", nativeQuery=true)
}
