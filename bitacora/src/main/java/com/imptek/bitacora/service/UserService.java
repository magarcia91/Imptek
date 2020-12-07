package com.imptek.bitacora.service;

import java.util.List;

import com.imptek.bitacora.Exception.UsernameOrIdNotFound;
import com.imptek.bitacora.dto.ChangePasswordForm;
import com.imptek.bitacora.entity.Users;

public interface UserService {

	public Iterable<Users> getAllUsers();

	public Users createUser(Users user) throws Exception;

	public Users getUserById(Long id) throws Exception;
	
	public Users updateUser(Users user) throws Exception;
	
	public void deleteUser(Long id) throws UsernameOrIdNotFound;
	
	public Users changePassword(ChangePasswordForm form) throws Exception;
	
	public List<Long> findByRole(Long user_id);	
	
}
