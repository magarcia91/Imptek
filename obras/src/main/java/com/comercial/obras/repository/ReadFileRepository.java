package com.comercial.obras.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comercial.obras.entity.Obras;

@Repository
public interface ReadFileRepository extends JpaRepository<Obras, Long>{
/*extends CrudRepository<Obras, Long> {*/

	@Query("select distinct(ciudadObr) from Obras")
	List<Obras> listarCiudad();
	
	@Query(value = "select c from Obras c where c.idObr=?1", nativeQuery = true)
	Obras findByObraId(@Param("idObr")Long idObr);
	
	/*@Query("SELECT p FROM Obras p WHERE p.ciudadObr LIKE %?1%  and p.zonaObr LIKE %?2% and p.sectorObr LIKE %?3% and p.statusObr LIKE %?4% " )*/
	@Query("SELECT p FROM Obras p WHERE (:ciudadObr is null or p.ciudadObr = :ciudadObr) and (:zonaObr is null or p.zonaObr = :zonaObr) and (:sectorObr is null or p.sectorObr = :sectorObr) and (:statusObr is null or p.statusObr = :statusObr) " )
	Page<Obras> buscarDatos(Pageable pageable,@Param("ciudadObr")String ciudad,@Param("zonaObr")String zona,@Param("sectorObr")String sector,@Param("statusObr")String estado);
	
	
	@Query("SELECT p FROM Obras p WHERE (:ciudadObr is null or p.ciudadObr = :ciudadObr) and (:zonaObr is null or p.zonaObr = :zonaObr) and (:sectorObr is null or p.sectorObr = :sectorObr) and (:statusObr is null or p.statusObr = :statusObr)" )
	Page<Obras> buscarByCiudad(Pageable pageable,@Param("ciudadObr")String ciudad,@Param("zonaObr")String zona,@Param("sectorObr")String sector,@Param("statusObr")String estado);
	
	
	
}
