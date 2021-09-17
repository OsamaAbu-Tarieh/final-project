package osama.atyponfinalproject.model.bean;

public class Student{

	private static final Student instance = new Student();
	
	private Integer studentId;
	private String studentName;
	private Integer studentAge;
	private String studentMajor;
	private User user;

	private Student() {}

	public Integer getId() {
		return studentId;
	}

	public void setId(Integer id) {
		this.studentId = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentMajor() {
		return studentMajor;
	}

	public void setStudentMajor(String studentMajor) {
		this.studentMajor = studentMajor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(Integer studentAge) {
		this.studentAge = studentAge;
	}
	
	public static Student getInstance() {
		return instance;
	}
	
	
}
