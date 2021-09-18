<%@ page import="java.util.*"%>
<%@ page import="osama.atyponfinalproject.model.bean.User"%>
<html>
	<head>
		<title>Home Page</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	</head>
	<body>
		<h1>This is Admin page </h1>
		<h3>Displaying Admin List</h3>
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
          		<th><b>Admin Name</b></th>
          		<th></th>
         	</tr> 
        <%
        ArrayList<User> adminList = 
            (ArrayList<User>)request.getAttribute("adminList");
        for(User admin : adminList){
        %>
            <tr>
                <td><%=admin.getUsername()%></td>
                <td><a href="/admin?operation=delete&userId=<%=admin.getId()%>"><i class="material-icons">delete</i></a></td>
            </tr>
        <%
            }
        %>
        </table>
        <br><br>
        
        <form action="/admin" method="POST">
        
        	<label> Admin Name : <input type="text" name="adminname"/></label><br><br>
        	<label> Admin Password : <input type="text" name="adminpassword"/></label><br><br>
 
        	<input type="submit" value="Add Admin">
        </form>
        
        <hr/>
		
		
		
	</body>
</html>
