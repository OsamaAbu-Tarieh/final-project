package osama.atyponfinalproject.model.bean;

public class Course{

	private static final Course instance = new Course();
	
	private Integer courseId;
	private String courseName;
	
	private Course() {}

	
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public static Course getInstance() {
		return instance;
	}
	
}
