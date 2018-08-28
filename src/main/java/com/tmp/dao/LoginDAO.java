package com.tmp.dao;

import com.tmp.entity.User;
import com.tmp.entity.UserRoleMapping;

public interface LoginDAO {
	public User getAuthenticateUser(String name,  String password);
	public UserRoleMapping getUserRole(int userId);
}
