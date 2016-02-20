package com.xidian.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.UserDao;
import com.xidian.forms.User;
import com.xidian.service.api.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService{
	@Resource(name="userDaoImpl")
	UserDao userDao;
	
	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public String getPassword(String name) {
		return userDao.getPassword(name);
	}
	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public void deleteUser(long id) {
		userDao.deleteUser(id);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public User getUserById(Long id) {
		return userDao.getUserById(id);
	}
}
