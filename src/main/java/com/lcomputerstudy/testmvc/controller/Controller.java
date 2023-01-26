package com.lcomputerstudy.testmvc.controller;

import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lcomputerstudy.testmvc.service.UserService;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.User;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		UserService userService = null; 
		User user = null;
		
		int page = 1;
		int count = 0;
		Pagination pagination = null;
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		switch (command) {
			case "/user-list.do":
				String reqPage = request.getParameter("page");
				if (reqPage != null) 
					page = Integer.parseInt(reqPage);
					
				userService = UserService.getInstance();
				count = userService.getUsersCount();
				
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(count);
				pagination.init();
				
				ArrayList<User> list = userService.getUsers(pagination);
				
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
				
				view = "user/list";
				break;
				
			case "/user-insert.do":			//보여주기
				view = "user/insert";
				break;
				
			case "/user-insert-process.do":		//실제 저장하는 코드 -> 있는 이유 : 이게 있어야 저장이 된다. 위에 하나만 있으면 시작하자마자 바로 저장이 되어서 입력할수가 없다.
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				userService = UserService.getInstance();
				userService.insertUser(user);
				view = "user/insert-result";
				break;
				
			case "/user-detail.do":		//01-18
				user = new User();		//user라는 저장공간을 만들어 놓은 것이다. 새로운 인스턴스를 만든게 아니라.
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.detailUser(user);
				request.setAttribute("user", user);
				view = "user/detail";
				break;
				
			case "/user-update.do":		//01-19
				user = new User();		//user라는 저장공간을 만들어 놓은 것이다. 새로운 인스턴스를 만든게 아니라.
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.detailUser(user);
				request.setAttribute("user", user);
				view = "user/update";
				break;
			
			case "/user-update-process.do":		//01-19
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_telArr(user.getU_tel().split("-"));	//1
				user.setU_age(request.getParameter("age"));
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				userService.updateUser(user);
				view = "user/update-result";
				break; 
				
			case "/user-delete.do":		//01-19
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.deleteUser(user);
				view = "user/delete";
				break;
				
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view + ".jsp");
		rd.forward(request, response);
	}
	

}
