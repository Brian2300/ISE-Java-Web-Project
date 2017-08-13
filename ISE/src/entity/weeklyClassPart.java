package entity;

public class weeklyClassPart {	
	private String student_id;
	private int week;
	private int attendance;
	private int QA_Coins;
	
	public weeklyClassPart(String student_id, int week, int attendance, int qaCoins) {
		this.student_id = student_id;
		this.week = week;
		this.attendance = attendance;
		QA_Coins = qaCoins;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public int getQA_Coins() {
		return QA_Coins;
	}

	public void setQA_Coins(int qaCoins) {
		QA_Coins = qaCoins;
	}
	
	
	
	

}
