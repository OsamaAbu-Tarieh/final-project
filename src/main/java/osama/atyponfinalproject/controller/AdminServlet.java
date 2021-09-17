package osama.atyponfinalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osama.atyponfinalproject.config.Router;
import osama.atyponfinalproject.model.bean.User;
import osama.atyponfinalproject.model.dao.UserDao;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends BaseServlet{

	private UserDao userDao = UserDao.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> userList = new ArrayList<>();
		List<User> adminList = new ArrayList<>();
		
		String operation = request.getParameter("operation"); 
		System.out.println(operation);
		
		if(operation.equals("get")) {
			try {
				userList = userDao.getAll();
				for(User u : userList) {
					if(u.getRole().equals("admin"))
						adminList.add(u);
				}
				request.setAttribute("adminList", adminList);
				forward(request,response, Router.ADMIN);
			} catch (Exception e) {
				final String errorMsg = e.getMessage();
				redirect(response, Router.HOME, new HashMap<String, String>(){{put("error", errorMsg);}});
			}
		} else if(operation.equals("delete")) {
			String id = request.getParameter("userId");
			Integer userId = Integer.valueOf(id);
			try {
				userDao.deleteById(userId);
				redirect(response, Router.ADMIN, new HashMap<String, String>() {{put("operation", "get");put("&error", "Successfull Delete Admin");}});
			} catch (Exception e) {
				final String errorMsg = e.getMessage();
				redirect(response, Router.HOME, new HashMap<String, String>(){{put("error", errorMsg);}});
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String adminName = request.getParameter("adminname");
		String adminPassword = request.getParameter("adminpassword");
		
		User user = User.getInstance();
		user.setUsername(adminName);
		user.setPassword(adminPassword);
		user.setRole("admin");
		
		try {
			userDao.save(user);
			redirect(response, Router.ADMIN, new HashMap<String, String>() {{put("operation", "get");put("&error", "Successfull Add Admin");}});
		} catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.ADMIN, new HashMap<String, String>() {{put("error", errorMsg);}});
		}
	
	}
	
}
