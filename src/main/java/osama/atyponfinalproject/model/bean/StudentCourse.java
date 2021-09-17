package osama.atyponfinalproject.model.bean;

import osama.atyponfinalproject.model.dao.CourseDao;

public class StudentCourse {

	private static final StudentCourse instance = new StudentCourse();

	private StudentCourse() {}
	
	private Integer id;
	private String studentName;
	private String courseName;
	private Integer grade;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	public static StudentCourse getInstance() {
		return instance;
	}
}
