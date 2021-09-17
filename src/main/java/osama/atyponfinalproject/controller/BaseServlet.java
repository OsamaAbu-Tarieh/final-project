package osama.atyponfinalproject.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osama.atyponfinalproject.config.Router;
import osama.atyponfinalproject.model.bean.User;

public class BaseServlet extends HttpServlet{

	protected void forward(HttpServletRequest request, HttpServletResponse response, Router router) throws ServletException, IOException {
		request.getRequestDispatcher(router.getPage()).forward(request, response);
	}
	
	protected void redirect(HttpServletResponse response, Router router, Map<String, String> params) throws IOException {
		String redirectTo = router.getUri() + (params == null ? "" : "?");
		if(params != null) {
			for(String key : params.keySet()) {
				redirectTo += key + "=" + params.get(key);
			}
		}
		
		response.sendRedirect(redirectTo);
	}
	
	
	protected User getLoggedInUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	protected boolean isLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute("user") != null;
	}
	
	protected boolean hasRole(HttpServletRequest request , String role) {
		if(!isLoggedIn(request)) return false;
		switch(role) {
		case "admin":
			return getLoggedInUser(request).getRole().equals("admin");
		case "student":
			return getLoggedInUser(request).getRole().equals("student");
		default:
			return false;
		}
	}
	
}
