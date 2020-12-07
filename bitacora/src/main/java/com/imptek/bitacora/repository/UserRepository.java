package com.imptek.bitacora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imptek.bitacora.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {


	@Query(value="select role_id from user_roles where user_id=:user_id order by role_id desc", nativeQuery=true)
	public List<Long> findByRole(@Param("user_id")Long user_id);	
	
	public Optional<Users> findByUsername(String username);
 }
