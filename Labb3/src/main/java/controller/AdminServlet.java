package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import integration.DatabaseHandler;
import model.Question;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DatabaseHandler dbHandler;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() {
    	dbHandler = new DatabaseHandler();
    }
    
    private Question extractQuestion(HttpServletRequest request) {
    	String questionText = request.getParameter("questionText");
    	String correctAns = request.getParameter("correctAns");
    	String wrongAns1 = request.getParameter("wrongAns1");
    	String wrongAns2 = request.getParameter("wrongAns2");
    	String wrongAns3 = request.getParameter("wrongAns3");
    	Question question = new Question(1, questionText, correctAns, wrongAns1, wrongAns2, wrongAns3);
    	return question;
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Question question = extractQuestion(request);
		dbHandler.addQuestion(question);
		response.sendRedirect("Admin.jsp");
	}

}
