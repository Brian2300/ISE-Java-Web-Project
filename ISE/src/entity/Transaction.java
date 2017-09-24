package entity;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Transaction {
private Post post;
private Student from_stu;
private Student to_stu;
private double amount;
private String timestamp;
private String type;



public Transaction(Post post, Student from_stu, Student to_stu, double amount, String timestamp, String type){
	this.post = post;
	this.from_stu = from_stu;
	this.to_stu = to_stu;
	this.type = type;
	this.amount = amount;
	this.timestamp = timestamp;
}



public Transaction(Post post, Student from_stu, double amount,String timestamp, String type) {
	this.post = post;
	this.from_stu = from_stu;
	this.type = type;
	this.amount = amount;
	this.timestamp = timestamp;
}

public Post getPost() {
	return post;
}

public void setPost(Post post) {
	this.post = post;
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

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
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
