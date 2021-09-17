package osama.atyponfinalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osama.atyponfinalproject.model.bean.StudentCourse;
//import osama.atyponfinalproject.model.dao.GradeDao;
import osama.atyponfinalproject.model.dao.StudentDao;
import osama.atyponfinalproject.config.Router;
import osama.atyponfinalproject.model.bean.User;

@WebServlet(urlPatterns = "/studentByCourse")
public class StudentByCourseServlet extends BaseServlet{

	private StudentDao stdDao =  StudentDao.getInstance();
	List<StudentCourse> studentCourse = new ArrayList<>();
	//private GradeDao gradeDao = new GradeDao();
	private Integer courseId;

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String course_id = request.getParameter("courseId");
		System.out.println(course_id);
		User user = (User) request.getSession().getAttribute("user");
		//courseId = Integer.valueOf(course_id);
		Integer userId = user.getId();
		try {
			studentCourse = stdDao.getStudentsByCourse(course_id);
			request.setAttribute("studentCourseList", studentCourse);
			forward(request, response, Router.STUDENTBYCOURSE);
		} catch (Exception e) {
			e.printStackTrace();
			final String errorMsg = e.getMessage();
			redirect(response, Router.LOGIN, new HashMap<String, String>() {
				{
					put("error", errorMsg);
				}
			});
		}
		
	}
	
}
