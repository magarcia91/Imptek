package com.comercial.obras.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comercial.obras.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {


	@Query(value="SELECT role_id FROM user_roles WHERE user_id=:user_id order by role_id desc", nativeQuery=true)
	public List<Long> findByRole(@Param("user_id")Long user_id);	
	
	public Optional<Users> findByUsername(String username);
 }
