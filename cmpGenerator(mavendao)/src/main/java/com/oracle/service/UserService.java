package com.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.dao.UserMapper;
import com.oracle.vo.User;

@Service
public class UserService {

	@Autowired
	UserMapper userdao;
	
	@Transactional(readOnly=true)
	public User login(String name,String password) {		
		return userdao.selectByNameAndPwd(name, password);
	}
}
