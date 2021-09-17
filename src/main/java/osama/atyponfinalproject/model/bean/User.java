package osama.atyponfinalproject.model.bean;

public class User {
	
	private static final User instance = new User();
	
	private Integer id;
	private String username;
	private String password;
	private String role;
	
	
	private User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	public static User getInstance() {
		return instance;
	}
	

}
