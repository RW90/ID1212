<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin interface</title>
</head>
<body>
Add another question to the quiz! <br><br>
<form action="admin" method="post" >
	Question text: <input name="questionText" type="text"/><br>
	Correct answer: <input name="correctAns" type="text"/><br>
	Wrong answer: <input name="wrongAns1" type="text"/><br>
	Wrong answer: <input name="wrongAns2" type="text"/><br>
	Wrong answer: <input name="wrongAns3" type="text"/><br>
	<input type="submit" value="Add question!"><br>
</form>
<a href="Quiz.jsp">Take quiz!</a>
</body>
</html>