<%@ page import="java.util.*"%>
<%@ page import="osama.atyponfinalproject.model.bean.StudentCourse"%>
<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Course page </h1>
		<h3>Displaying Student Course</h3>
      	<table border ="1" width="500" align="center">
     		<tr bgcolor="00FF7F">
          		<th><b>Course Name</b></th>
          		<!--<th><b>Course Grade</b></th>-->
         	</tr> 
        <%
        ArrayList<StudentCourse> std = 
            (ArrayList<StudentCourse>)request.getAttribute("courseList");
        for(StudentCourse s:std){
        %>
            <tr>
                <td><%=s.getCourseName()%></td>
                <!--<td><%=s.getGrade()%></td>-->
            </tr>
        <%
            }
        %>
        </table> 
        <hr/>
		
		
		
	</body>
</html>