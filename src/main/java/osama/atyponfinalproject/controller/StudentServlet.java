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
import osama.atyponfinalproject.model.bean.Student;
import osama.atyponfinalproject.model.dao.StudentDao;
import osama.atyponfinalproject.model.dao.UserDao;
import osama.atyponfinalproject.model.bean.User;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends BaseServlet{

	private StudentDao studentDao =  StudentDao.getInstance(); 
	private UserDao userDao = UserDao.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Student> allStudents = new ArrayList<>();
		
		try {
			allStudents = studentDao.getAll();
			System.out.println("from doGet student : ");
			System.out.println(allStudents.size());
			for(Student s : allStudents) {
				System.out.println("//-> " + s.getId() + "    " + s.getStudentName() + "    " + s.getStudentMajor() + "    ");
			}
			request.setAttribute("studentList", allStudents);
			forward(request,response, Router.STUDENT);
		} catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.HOME, new HashMap<String, String>(){{put("error", errorMsg);}});
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentName = request.getParameter("studentname");
		String studentPass = request.getParameter("studentpassword");
		String studentAge = request.getParameter("studentage");
		String studentMajor = request.getParameter("studentmajor");
		
		
		User user = User.getInstance();
		
		user.setUsername(studentName);
		user.setPassword(studentPass);
		user.setRole("student");
		
		try {
			User saveUser = userDao.save(user);
			System.out.println("from student servlet after save user : ");
			System.out.println(saveUser.getId());
			System.out.println(saveUser.getUsername());
			System.out.println(saveUser.getPassword());
			System.out.println(saveUser.getRole());
			System.out.println("---------------------------------------");
			if(saveUser != null) {
				System.out.println("-------------------In IF--------------------");
				Student student = Student.getInstance();
				student.setStudentName(studentName);
				student.setStudentAge(Integer.parseInt(studentAge));
				student.setStudentMajor(studentMajor);
				student.setUser(saveUser);
				studentDao.save(student);
				redirect(response, Router.STUDENT, new HashMap<String, String>() {{put("error", "Successfull Add Student");}});
			}
		} catch (Exception e) {
			final String errorMsg = e.getMessage();
			redirect(response, Router.HOME, new HashMap<String, String>() {{put("error", errorMsg);}});
		}
	}
	
}
