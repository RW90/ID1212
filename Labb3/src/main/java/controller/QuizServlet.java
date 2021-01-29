package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import integration.DatabaseHandler;
import model.QuizBean;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DatabaseHandler dbHandler;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() {
    	dbHandler = new DatabaseHandler();
    }
    
    private void updateQuizSession(QuizBean quiz, HttpSession session) {
    	session.setAttribute("currentQuestion", quiz.nextQuestion());
    	session.setAttribute("points", quiz.getPoints());
    	session.setAttribute("questionNr", quiz.getqIndex());
    	session.setAttribute("questionNrTot", quiz.getNrOfQuestions());
    	session.setAttribute("finished", quiz.isFinished());
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuizBean quiz = new QuizBean(dbHandler.getQuestions());
		HttpSession session = request.getSession();
		session.setAttribute("quiz", quiz);
		updateQuizSession(quiz, session);
		response.sendRedirect("Quiz.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		QuizBean quiz = (QuizBean) session.getAttribute("quiz");
		String answer = request.getParameter("answer");
		quiz.guess(answer);
		updateQuizSession(quiz, session);
		response.sendRedirect("Quiz.jsp");
	}

}
