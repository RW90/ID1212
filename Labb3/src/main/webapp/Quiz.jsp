<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="model.Question" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quiz</title>
<%!
	private boolean isLoggedOut(HttpSession session) {
		return session.getAttribute("user") == null;
	}

	private boolean isFinished(HttpSession session) {
		if(session.getAttribute("finished") == null) {
			return false;
		} else {
			boolean finished = (boolean) session.getAttribute("finished");
			return finished;
		}
	}
%>
</head>
<body>
<%
	if(isLoggedOut(session)) {
		response.sendRedirect("Login.jsp");
	}
%>
Welcome <%=session.getAttribute("user") %>!     
<form method="get" action="quiz">
<input type="submit" value="New quiz!">
</form>
<a href="Admin.jsp"> Go to admin interface</a>
 <br>
 <br>
<%
	Question question = (Question) session.getAttribute("currentQuestion");
	if(question != null) {
%>
		Question <%=session.getAttribute("questionNr")%> / <%=session.getAttribute("questionNrTot")%> <br>
		<b><%=question.getText()%></b><br>
		<form method="post" action="quiz">
			<input type="radio" name="answer" value="<%= question.getCorrectAns() %>" required> <%= question.getCorrectAns() %>
			<br>
			<input type="radio" name="answer" value="<%= question.getWrongAns1() %>" required> <%= question.getWrongAns1() %>
			<br>
			<input type="radio" name="answer" value="<%= question.getWrongAns2() %>" required> <%= question.getWrongAns2() %>
			<br>
			<input type="radio" name="answer" value="<%= question.getWrongAns3() %>" required> <%= question.getWrongAns3() %>
			<br>
			<input type="submit" value="Send answer">
		</form>
<%	
	} else if(isFinished(session)) {		
%>
	Quiz finished! You got <%=session.getAttribute("points")%> / <%=session.getAttribute("questionNrTot")%> correct!
<% 
	}
%>
</body>
</html>