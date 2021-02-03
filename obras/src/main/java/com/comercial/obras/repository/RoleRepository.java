package com.comercial.obras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comercial.obras.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	
	@Query(value = "select c from Role c where c.id=:id")
	Role findByRoleId(@Param("id")Long id);
	
	public Role findByName(String role);
	
}
