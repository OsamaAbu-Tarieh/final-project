<%@page import="osama.atyponfinalproject.model.bean.User"%>
<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Home page </h1>
		<%
			String error = request.getParameter("error");
			if(error != null){
				out.print(error);
			}
		%>
		
		<p><font color="green">Welcome ${user.username}</font></p>
		
		<%
			User user = (User)request.getSession().getAttribute("user");
			
			if(user.getRole().equals("admin")){
		%>
				<a href="/student">Student</a>
				<br>
				<br>
				<a href="/course">Courses</a>
				<br>
				<br>
				<a href="/admin?operation=get">Admins</a>
				<br>
				<br>
				<a href="/assignStudent">Assign Student to course</a>
		<%
			}else if(user.getRole().equals("instructor")){
		%>
				<a href="/course">Courses</a>
		<%
			}else{
		%>
				<a href="/myCourse">My Course</a>
		<%
			}
		%>
		
		<br><br>
		<a href="/logout"><button>Logout</button></a>
	</body>
</html>