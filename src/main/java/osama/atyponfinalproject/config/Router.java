package osama.atyponfinalproject.config;

public enum Router {
	HOME("/home", "/WEB-INF/views/homePage.jsp"),
	ACCESSDENIED("/accessDenied", "/WEB-INF/views/accessDenied.jsp"),
	STUDENT("/student" , "/WEB-INF/views/studentPage.jsp"),
	COURSE("/course","/WEB-INF/views/coursePage.jsp"),
	ADMIN("/admin","/WEB-INF/views/adminPage.jsp"),
	MYCOURES("/myCourse","/WEB-INF/views/myCoursePage.jsp"),
	ASSIGNSTUDENT("/assignStudent","/WEB-INF/views/assignStudentToCourse.jsp"),
	STUDENTBYCOURSE("/studentByCourse" , "/WEB-INF/views/studentByCourse.jsp"),
	LOGIN("/login","/WEB-INF/views/loginPage.jsp");
	
	Router(String uri,String page){
		this.uri = uri;
		this.page = page;
	}
	
	private final String uri;
	private final String page;
	
	public String getUri() {
		return uri;
	}
	
	public String getPage() {
		return page;
	}
}
