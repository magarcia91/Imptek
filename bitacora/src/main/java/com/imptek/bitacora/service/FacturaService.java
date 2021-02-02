package com.imptek.bitacora.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imptek.bitacora.entity.Factura;

public interface FacturaService {

	Page<Factura> getAll(Pageable pageable);
	public boolean createExcel(List<Factura> facturas, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	@Query("SELECT f FROM Factura f WHERE f.cliente.codCliente=?1")
	Page<Factura> buscarDatos(Pageable pageable,@Param("codCliente")String codCliente);
}
