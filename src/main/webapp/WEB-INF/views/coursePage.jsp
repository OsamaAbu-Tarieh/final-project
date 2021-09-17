<%@ page import="java.util.*"%>
<%@ page import="osama.atyponfinalproject.model.bean.Course"%>
<%@ page import="osama.atyponfinalproject.model.bean.User"%>
<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Courses page </h1>
		<h3>Displaying Courses List</h3>
		<font color="green">
			<% 
				String error = request.getParameter("error");
				if(error != null){
					out.print(error);
				}
			%>
		</font>
      	<table border ="1" width="500" align="center">
     		<tr bgcolor="00FF7F">
          		<th><b>Course Name</b></th>
         	</tr> 
        <%
        ArrayList<Course> courses = 
            (ArrayList<Course>)request.getAttribute("courseList");
        for(Course c:courses){
        %>
            <tr>
                <td><a href="/studentByCourse?courseId=<%=c.getCourseName()%>"><%=c.getCourseName()%></a></td>
            </tr>
        <%
            }
        %>
        </table> 
        <br><br>
        <% 
        User user = (User)request.getSession().getAttribute("user");
			
		if(user.getRole().equals("admin")){%>
        <form action="/course" method="POST">        
        	<label> Course Name : <input type="text" name="coursename"/></label><br><br>

        	<input type="submit" value="Add course">
        </form>
        <% } %>
        <hr/>
		
	</body>
</html>