package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String text;
	private String correctAns;
	private String wrongAns1;
	private String wrongAns2;
	private String wrongAns3;
	
	public Question() {
		
	}
	
	public Question(int id, String text, String correctAns, String wrongAns1, String wrongAns2, String wrongAns3) {
		this.id = id;
		this.text = text;
		this.correctAns = correctAns;
		this.wrongAns1 = wrongAns1;
		this.wrongAns2 = wrongAns2;
		this.wrongAns3 = wrongAns3;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getCorrectAns() {
		return correctAns;
	}
	
	public void setCorrectAns(String correctAns) {
		this.correctAns = correctAns;
	}
	
	public String getWrongAns1() {
		return wrongAns1;
	}
	
	public void setWrongAns1(String wrongAns1) {
		this.wrongAns1 = wrongAns1;
	}
	
	public String getWrongAns2() {
		return wrongAns2;
	}
	
	public void setWrongAns2(String wrongAns2) {
		this.wrongAns2 = wrongAns2;
	}
	
	public String getWrongAns3() {
		return wrongAns3;
	}
	
	public void setWrongAns3(String wrongAns3) {
		this.wrongAns3 = wrongAns3;
	}
}
