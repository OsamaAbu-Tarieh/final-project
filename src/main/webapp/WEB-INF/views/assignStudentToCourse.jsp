<%@ page import="java.util.*"%>
<%@ page import="osama.atyponfinalproject.model.bean.Course"%>
<%@ page import="osama.atyponfinalproject.model.bean.Student"%>
<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Assign Student page </h1>
		<font color="green">
			<% 
				String error = request.getParameter("error");
				if(error != null){
					out.print(error);
				}
			%>
		</font>
		<br><br>
      	<form action="/assignStudent" method="POST">
      		Select student:
        	<select name="studentOpt">
        	<%
	        ArrayList<Student> studentOptions = 
	            (ArrayList<Student>)request.getAttribute("studentList");
	        for(Student s : studentOptions){
	        %>
	            <option value="<%=s.getStudentName()%>"><%=s.getStudentName()%></option>
	        <%
	            }
	        %>
	        </select>
	        <br><br>
        	Select course:
        	<select name="courseOpt">
        	<%
	        ArrayList<Course> courseOptions = 
	            (ArrayList<Course>)request.getAttribute("courseList");
	        for(Course c : courseOptions){
	        %>
	            <option value="<%=c.getCourseName()%>"><%=c.getCourseName()%></option>
	        <%
	            }
	        %>
	        </select>
        	<br><br>
        	<input type="submit" value="Save Changes">
        </form>
      	 
        <br><br>
        <hr/>
		
	</body>
</html>