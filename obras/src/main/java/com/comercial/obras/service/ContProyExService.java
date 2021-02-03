package com.comercial.obras.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.comercial.obras.entity.ContactoProyectoEx;

import net.sf.jasperreports.engine.JRException;

public interface ContProyExService {
	
	public List<ContactoProyectoEx>listar();
	public Optional<ContactoProyectoEx>listarId(int idContEx);
	public int save(ContactoProyectoEx contEx);
	public void delete(int idContEx);
	public boolean createPdf(List<ContactoProyectoEx> contactos_ex, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException;
	Page<ContactoProyectoEx> getAll(Pageable pageable);
	public List<ContactoProyectoEx> findByAvance();	
	
}
