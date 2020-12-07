package com.comercial.obras.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.comercial.obras.entity.Obras;


public interface ReadFileService {

	List<Obras> findAll();
	boolean saveDataFromUploadfile(MultipartFile file);	
	boolean readDataFromJson(MultipartFile file);
	@Query("select distinct(o.ciudadObr) from Obras o")
	List<Obras> listarCiudad();
	Optional<Obras> findByObraId(Long idObr);
	int save(Obras obras);
	Page<Obras> getAll(Pageable pageable);
	
	//@Query("SELECT p FROM Obras p WHERE p.ciudadObr LIKE %?1%")
	//Page<Obras> buscarDatos(Pageable pageable,@Param("ciudad")String ciudad);
	
	//@Query("SELECT p FROM Obras p WHERE p.ciudadObr LIKE %?1%  and p.zonaObr LIKE %?2% and p.sectorObr LIKE %?3% and p.statusObr LIKE %?4% " )
	@Query("SELECT p FROM Obras p WHERE (:ciudadObr is null or p.ciudadObr = :ciudadObr) and (:zonaObr is null or p.zonaObr = :zonaObr) and (:sectorObr is null or p.sectorObr = :sectorObr) and (:statusObr is null or p.statusObr = :statusObr) " )
	Page<Obras> buscarDatos(Pageable pageable,@Param("ciudadObr")String ciudad,@Param("zonaObr")String zona,@Param("sectorObr")String sector,@Param("statusObr")String estado);
	
	
	@Query("SELECT p FROM Obras p WHERE (:ciudadObr is null or p.ciudadObr = :ciudadObr) and (:zonaObr is null or p.zonaObr = :zonaObr) and (:sectorObr is null or p.sectorObr = :sectorObr) and (:statusObr is null or p.statusObr = :statusObr)" )
	Page<Obras> buscarByCiudad(Pageable pageable,@Param("ciudadObr")String ciudad,@Param("zonaObr")String zona,@Param("sectorObr")String sector,@Param("statusObr")String estado);
	
}
