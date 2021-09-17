package osama.atyponfinalproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osama.atyponfinalproject.config.Router;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends BaseServlet{

	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		request.getSession().invalidate();
		redirect(response, Router.LOGIN, null);
	}
	
}
