package com.lcomputerstudy.testmvc.service;

import java.util.ArrayList;

import com.lcomputerstudy.testmvc.dao.UserDAO;
import com.lcomputerstudy.testmvc.vo.User;

public class UserService {
	
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		return service;
	}
	
	public ArrayList<User> getUsers() {
		return dao.getUsers();
	}

	public void insertUser(User user) {
		dao.insertUser(user);
	}
	
//	public void detailUser(User user) {		//01-17
//		
//	}

	public User detailUser(User user) {		//01-18 상세
		return dao.detailUser(user);
	}

	public void modifyUser(User user) {		//01-18 수정
		dao.modifyUser(user);
	}
}
