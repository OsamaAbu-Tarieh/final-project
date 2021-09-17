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
import osama.atyponfinalproject.model.bean.Student;
import osama.atyponfinalproject.model.bean.StudentCourse;
import osama.atyponfinalproject.model.dao.CourseDao;
import osama.atyponfinalproject.model.dao.StudentByCourseDao;
import osama.atyponfinalproject.model.dao.StudentDao;

@WebServlet(urlPatterns = "/assignStudent")
public class AssignStudentToCourseServlet extends BaseServlet{

	private CourseDao courseDao = CourseDao.getInstance();
	private StudentDao studentDao = StudentDao.getInstance();
	private StudentByCourseDao studentCourseDao = StudentByCourseDao.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Course> coursesList = new ArrayList<>();
		List<Student> studentList = new ArrayList<>();
		
		try{
			coursesList = courseDao.getAll();
			studentList = studentDao.getAll();
			request.setAttribute("courseList", coursesList);
			request.setAttribute("studentList", studentList);
			forward(request, response, Router.ASSIGNSTUDENT);
		}catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.ASSIGNSTUDENT, new HashMap<String,String>(){{put("error", errorMsg);}});
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String student = request.getParameter("studentOpt");
		String course = request.getParameter("courseOpt");
		
		//Integer studentId = Integer.parseInt(student);
		//Integer courseId = Integer.parseInt(course);
		
		System.out.println(student + " | " + course + " | ");		
		
		StudentCourse studentCourse = StudentCourse.getInstance();
		
		studentCourse.setCourseName(course);
		studentCourse.setStudentName(student);
		
		try {		
			StudentCourse successAssignStudent = studentCourseDao.save(studentCourse);
			System.out.println(successAssignStudent);
			redirect(response, Router.HOME, new HashMap<String,String>(){{put("error", "Successfull Assign Student");}});
		} catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.ASSIGNSTUDENT, new HashMap<String,String>(){{put("error", errorMsg);}});
		}
	}
	
}
