package com.comercial.obras.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comercial.obras.entity.ContactoProyecto;


@Repository
public interface ContactoProyectoRepository extends CrudRepository<ContactoProyecto, Long> {

/*	@Query(value ="select c.contacto_proyecto_obr from contacto c")
	public List<ContactoProyecto>findBycontactoProyectoObr(String contactoProyectoObr);*/
	
	@Query(value = "select c.id_contact,c.nombre_contact,c.telefono_contact,c.correo_contact from contacto c where c.id_contact=?1", nativeQuery = true)
	List<Object[]> findBycontactoProyectoObr(@Param("nombreProyecto")String contactoProyectoObr);
	
	@Query(value="SELECT * FROM contacto WHERE contacto_avance_obr ='100%'", nativeQuery=true)
	public List<ContactoProyecto> findByAvance();
	
	Page<ContactoProyecto> findAll(Pageable pageable);
	
		
}
