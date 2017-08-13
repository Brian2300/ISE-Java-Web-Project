package entity;

public class weeklyRequestForm {
	private int rf_id;
	private String question;
	private String answer;
	private int QA_Coins;
	private int week;
	private String smu_id;
	
	
	public weeklyRequestForm(int rf_id, String question, String answer, int qaCoins, int week, String smu_id) {
		this.rf_id = rf_id;
		this.question = question;
		this.answer = answer;
		QA_Coins = qaCoins;
		this.week = week;
		this.smu_id = smu_id;
	}


	public int getRf_id() {
		return rf_id;
	}


	public void setRf_id(int rf_id) {
		this.rf_id = rf_id;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public int getQA_Coins() {
		return QA_Coins;
	}


	public void setQA_Coins(int qaCoins) {
		QA_Coins = qaCoins;
	}


	public int getWeek() {
		return week;
	}


	public void setWeek(int week) {
		this.week = week;
	}


	public String getSmu_id() {
		return smu_id;
	}


	public void setSmu_id(String smu_id) {
		this.smu_id = smu_id;
	}
	
	
	
	

}
