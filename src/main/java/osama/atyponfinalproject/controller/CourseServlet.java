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
import osama.atyponfinalproject.model.bean.Course;
import osama.atyponfinalproject.model.bean.User;
import osama.atyponfinalproject.model.dao.CourseDao;

@WebServlet(urlPatterns = "/course")
public class CourseServlet extends BaseServlet{

	private CourseDao courseDao = CourseDao.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Course> allCourses = new ArrayList<>();
		try {
			User user = (User)request.getSession().getAttribute("user");
			if(user.getRole().equals("admin")) {
				allCourses = courseDao.getAll();
			}
			request.setAttribute("courseList", allCourses);
			forward(request,response, Router.COURSE);
		} catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.HOME, new HashMap<String, String>(){{put("error", errorMsg);}});
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("coursename");
		
		Course course = Course.getInstance();
		
		course.setCourseName(courseName);
		
		try {
			courseDao.save(course);
			redirect(response, Router.COURSE, new HashMap<String, String>() {{put("error", "Successfull Add Course");}});
		} catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.COURSE, new HashMap<String, String>() {{put("error", errorMsg);}});
		}
		
	}
}
