package com.comercial.obras.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comercial.obras.entity.ContactoProyectoEx;


@Repository
public interface ContProyExRepository extends JpaRepository<ContactoProyectoEx, Integer> {

	@Query(value="SELECT * FROM contacto_ex WHERE cont_avan_obr_ex ='100%'", nativeQuery=true)
	public List<ContactoProyectoEx> findByAvance();	
	
	Page<ContactoProyectoEx> findAll(Pageable pageable);
		
}
