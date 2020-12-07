package com.comercial.obras.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comercial.obras.entity.ObrasEx;

@Repository
public interface ObrasExRepository extends JpaRepository<ObrasEx, Integer> {


	@Query(value="SELECT * FROM obras_ex WHERE avance_obr_ex ='100%'", nativeQuery=true)
	public List<ObrasEx> findByAvance();	
	
	@Query(value="delete from obras", nativeQuery=true)
	public void deleteObras(int idObr);	
	
	Page<ObrasEx> findAll(Pageable pageable);
}
