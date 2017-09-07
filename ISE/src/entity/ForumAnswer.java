package entity;

public class ForumAnswer {
	private int answer_id;
	private String student_id;
	private int question_id;
	private String answer;

	public ForumAnswer(int answer_id, String student_id, int question_id, String answer) {
		this.answer_id = answer_id;
		this.student_id = student_id;
		this.question_id = question_id;
		this.answer = answer;
	}

	public int getAnswer_id() {
		return answer_id;
	}

	public String getStudent_id() {
		return student_id;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer_id(int answer_id) {
		this.answer_id = answer_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
