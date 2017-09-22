package entity;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Transaction {
private Student from_stu;
private Student to_stu;
private String timestamp;
private double amount;

public Transaction(Student from_stu, Student to_stu, double amount, String timestamp) {
	this.from_stu = from_stu;
	this.to_stu = to_stu;
	this.amount = amount;
	this.timestamp = timestamp;
}

public Transaction(Student from_stu, Student to_stu, double amount) {
	this.from_stu = from_stu;
	this.to_stu = to_stu;
	this.amount = amount;
	Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
	this.timestamp =time_stamp.toString();
}

public Student getFrom_stu() {
	return from_stu;
}
public void setFrom_stu(Student from_stu) {
	this.from_stu = from_stu;
}
public Student getTo_stu() {
	return to_stu;
}
public void setTo_stu(Student to_stu) {
	this.to_stu = to_stu;
}
public String getTimestamp() {
	return timestamp;
}
public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}


}
