package model;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Question> questions;
	private Question currentQuestion;
	private int qIndex;
	private int nrOfQuestions;
	private int points;
	private boolean finished;
	
	public QuizBean(ArrayList<Question> questions) {
		this.questions = questions;
		qIndex = 0;
		points = 0;
		finished = false;
		nrOfQuestions = this.questions.size();
	}
	
	public Question nextQuestion() {
		finished = qIndex >= nrOfQuestions;
		if(!finished) {
			currentQuestion = questions.get(qIndex);
			qIndex++;
			return currentQuestion;
		}
		return null;
	}
	
	public boolean guess(String guess) {
		boolean correct = currentQuestion.getCorrectAns().equalsIgnoreCase(guess);
		if(correct) {
			points++;
		}
		return correct;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public int getPoints() {
		return points;
	}

	public int getqIndex() {
		return qIndex;
	}

	public int getNrOfQuestions() {
		return nrOfQuestions;
	}
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}

}
