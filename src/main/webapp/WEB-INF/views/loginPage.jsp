<html>
	<head>
		<title>Home Page</title>
	</head>
	<body>
		<h1> This is Login page </h1>
		<p>
			<font color="red">
			<% 
				String error = request.getParameter("error");
				if("1".equals(error)){
					out.print("Username is mandatory");
				}else if("2".equals(error)){
					out.print("Password is mandatory");
				}else if("3".equals(error)){
					out.print("Invalid username or password");
				}else if(error != null){
					out.print(error);
				}
			%>
			</font>
		</p>
		<form action="/login" method="POST">
			<label>Username : <input name="username" type="text"/></label>
			<br><br>
			<label>Password : <input name="password" type="password"/></label>
			<br><br>
			<input type="submit" value="login">
		</form>
	</body>
</html>