package osama.atyponfinalproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osama.atyponfinalproject.config.Router;

@WebServlet(urlPatterns = "/home")
public class HomePageServlet extends BaseServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request)) {
			forward(request, response, Router.HOME);
			return;
		}
		
		forward(request,response, Router.ACCESSDENIED);
	}
	
}
