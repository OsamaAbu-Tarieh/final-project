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
import osama.atyponfinalproject.model.bean.StudentCourse;
import osama.atyponfinalproject.model.bean.User;
import osama.atyponfinalproject.model.dao.StudentByCourseDao;

@WebServlet(urlPatterns = "/myCourse")
public class MyCourseServlet extends BaseServlet{

	private StudentByCourseDao studentCourseDao = StudentByCourseDao.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<StudentCourse> myCourses = new ArrayList<>();
		try {
			User user = (User)request.getSession().getAttribute("user");
			myCourses = studentCourseDao.getByStudent(user.getUsername());
			request.setAttribute("courseList", myCourses);
			forward(request,response, Router.MYCOURES);
		}catch (Exception e) {
			e.printStackTrace();
			final String errorMsg = e.getMessage();
			redirect(response, Router.HOME, new HashMap<String, String>(){{put("error", errorMsg);}});
		}
	}
	
}
