package osama.atyponfinalproject.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import osama.atyponfinalproject.config.Router;
import osama.atyponfinalproject.config.SecurityConfigurer;
import osama.atyponfinalproject.model.bean.User;
import osama.atyponfinalproject.model.dao.UserDao;

@WebServlet(urlPatterns = {"/","/login"})
public class LoginPageServlet extends BaseServlet{

	private UserDao userDao = UserDao.getInstance();
	private SecurityConfigurer security = SecurityConfigurer.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request)) {
			forward(request, response, Router.HOME);
			System.out.println("to home");
		}
		System.out.println("to login");
		
		forward(request,response,Router.LOGIN);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || username.equals("")) {
			redirect(response, Router.LOGIN, new HashMap<String, String>(){{put("error", "1");}});
			return;
			
		}else if(password == null || password.equals("")) {
			redirect(response, Router.LOGIN, new HashMap<String, String>(){{put("error", "2");}});
			return;
		}
		
		HttpSession session = request.getSession();
		
		try {
			User user = userDao.getByName(username);
			System.out.println(user.getRole());
			if(user != null && password.equals(security.getDecodedPassword(user.getPassword()))) {
				session.setAttribute("user", user);
				redirect(response, Router.HOME, null);
			}else {
				redirect(response, Router.LOGIN, new HashMap<String, String>(){{put("error", "3");}});
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirect(response, Router.LOGIN, new HashMap<String, String>(){{put("error", "3");}});

		}
	}
	
}
