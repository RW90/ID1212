package integration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Question;
public class DatabaseHandler {
	
	//private ArrayList<Question> questions;
	private SessionFactory sessionFactory;
	
	public DatabaseHandler() {
		//questions = new ArrayList<Question>();
		//mockInit();
		sessionFactory = new Configuration().configure().buildSessionFactory();
		
;	}
	
	public void addQuestion(Question question) {
		//questions.add(question);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(question);
		session.getTransaction().commit();
		session.close();
	}
	
	public ArrayList<Question> getQuestions() {
		//return questions;
		Session session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Question> criteria = builder.createQuery(Question.class);
		criteria.from(Question.class);
		List<Question> qList = session.createQuery(criteria).getResultList();
		ArrayList<Question> returnList = new ArrayList<Question>(qList);
		return returnList;
	}
	
	/*
	private void mockInit() {
		Question q1 = new Question(0, "What is green?", "Grass", "Water", "Sky", "Blaj");
		Question q2 = new Question(1, "Which gem is red?", "Ruby", "Diamond", "Emerald", "Saphire");
		Question q3 = new Question(2, "Which fruit is orange?", "Pumpkin", "Apple", "Banana", "Pear");
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
	}
	*/
	
	public static void main(String[] args) {
		Question question = new Question();
		question.setText("Which fruit is orange?");
		question.setCorrectAns("Pumpkin");
		question.setWrongAns1("Apple");
		question.setWrongAns2("Banana");
		question.setWrongAns3("Pear");
		
		Question question2 = new Question();
		question2.setText("Which gem is red?");
		question2.setCorrectAns("Ruby");
		question2.setWrongAns1("Diamond");
		question2.setWrongAns2("Emerald");
		question2.setWrongAns3("Saphire");
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(question);
		session.save(question2);
		session.getTransaction().commit();
		session.close();
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		// kan vara frå hibernate denna ska importeras
		CriteriaQuery<Question> criteria = builder.createQuery(Question.class); 
		criteria.from(Question.class);
		List<Question> qList = session.createQuery(criteria).getResultList();
		
		for(Question q : qList) {
			System.out.println(q.getText());
			System.out.println("1. " + q.getCorrectAns());
			System.out.println("2. " + q.getWrongAns1());
			System.out.println("3. " + q.getWrongAns2());
			System.out.println("4. " + q.getWrongAns3());
		}
	}
	
}
