package com.comercial.obras.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.comercial.obras.entity.ObrasEx;

import net.sf.jasperreports.engine.JRException;

public interface ObrasExService {

	List<ObrasEx> listar();
	public Optional<ObrasEx>listarId(int idObrEx);
	public int save(ObrasEx obrasEx);
	public void delete(int idObrEx);
	Optional<ObrasEx> findByObraId(int idObrEx);
	public boolean createPdf(List<ObrasEx> obrasEx, ServletContext context, HttpServletRequest request, HttpServletResponse response);	
	public List<ObrasEx>findByAvance();
	Page<ObrasEx> getAll(Pageable pageable);
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException;
	
}
