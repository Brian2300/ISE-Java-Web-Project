package entity;

public class ForumQuestion {
	private int Question_ID;
	private String question;
	private String tag;
	
	public ForumQuestion(int question_ID, String question, String tag) {		
		Question_ID = question_ID;
		this.question = question;
		this.tag = tag;
	}
	
	public int getQuestion_ID() {
		return Question_ID;
	}

	public String getQuestion() {
		return question;
	}

	public String getTag() {
		return tag;
	}

	public void setQuestion_ID(int question_ID) {
		Question_ID = question_ID;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	

}
