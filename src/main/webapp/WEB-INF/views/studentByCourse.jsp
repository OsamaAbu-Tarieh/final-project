<%@ page import="java.util.*"%>
<%@ page import="osama.atyponfinalproject.model.bean.StudentCourse"%>
<%@ page import="osama.atyponfinalproject.model.bean.User"%>
<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Student Course page </h1>
		<h3>Displaying Student Course List</h3>
		
      	<form action="/studentByCourse" method="POST">
      	<table border ="1" width="500" align="center">
     		<tr bgcolor="00FF7F">
     			<th><b>Id</b></th>
     			<th><b>Student Name</b></th>
          		<th><b>Course Name</b></th>
          		<!--<th><b>Grade</b></th>-->
         	</tr> 
        <%
        ArrayList<StudentCourse> studentCourse = 
            (ArrayList<StudentCourse>)request.getAttribute("studentCourseList");
        for(StudentCourse std : studentCourse){
        %>
            <tr>
                <td><%=std.getId()%></td>
                <td><%=std.getStudentName()%></td>
                <td><%=std.getCourseName()%></td>
                
            	<!--<td><input type="number" id="<%=std.getId()%>" name="studentGrade<%=std.getId()%>" value="<%=std.getGrade()%>" max="100" min="0"></td>-->
                
            </tr>
        <%
            }
        %>
        </table> 
        <br><br>
        <!--<input type="submit" value="save" style="display: block;margin-right: auto;margin-left: auto;">-->
        </form>
        <hr/>
		
	</body>
</html>