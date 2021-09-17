<%@ page import="java.util.*"%>
<%@ page import="osama.atyponfinalproject.model.bean.Student"%>
<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Student page </h1>
		<h3>Displaying Student List</h3>
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
          		<th><b>Student Name</b></th>
          		<th><b>Student Age</b></th>
          		<th><b>Student Major</b></th>
         	</tr> 
        <%
        ArrayList<Student> std = 
            (ArrayList<Student>)request.getAttribute("studentList");
        for(Student s:std){
        %>
            <tr>
                <td><%=s.getStudentName()%></td>
                <td><%=s.getStudentAge()%></td>
                <td><%=s.getStudentMajor()%></td>
            </tr>
        <%
            }
        %>
        </table>
        <br><br>
        
        <form action="/student" method="POST">
        
        	<label> Student Name : <input type="text" name="studentname"/></label><br><br>
        	<label> Student Password : <input type="text" name="studentpassword"/></label><br><br>
        	<label> Student Age : <input type="number" name="studentage" min="17" max="100"/></label><br><br>
        	<label> Student Major : <input type="text" name="studentmajor"/></label><br><br>
        
        	<input type="submit" value="Add Student">
        </form>
        
        <hr/>
		
		
		
	</body>
</html>