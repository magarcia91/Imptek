package com.comercial.obras.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.comercial.obras.entity.ContactoProyecto;
import com.comercial.obras.entity.ObrasEx;

public interface ContactoProyectoService {
	
	public List<ContactoProyecto>listar();
	public Optional<ContactoProyecto>listarId(Long idContact);
	public int save(ContactoProyecto contacto);
	public void delete(Long idContact);
	public List<ContactoProyecto>findByAvance();
	public boolean createPdf(List<ContactoProyecto> contactos, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	Page<ContactoProyecto> getAll(Pageable pageable);
	/*public List<ContactoProyecto>getcontactoProyectoObr();*/
}
