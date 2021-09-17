package osama.atyponfinalproject.model.bean;

import osama.atyponfinalproject.model.bean.Student;
import osama.atyponfinalproject.model.dao.CourseDao;
import osama.atyponfinalproject.model.bean.Course;

public class Grade {

	private static final Grade instance = new Grade();
	
	private Student student;
	private Course course;
	private Integer grade;
	
	private Grade() {}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	public static Grade getInstance() {
		return instance;
	}
}
