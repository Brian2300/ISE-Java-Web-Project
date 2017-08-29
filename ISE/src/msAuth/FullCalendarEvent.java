package msAuth;

public class FullCalendarEvent {
	private String id;
	private String title;
	private String start;
	private String end;
	private boolean allDay;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getStart(){
		return start;
	}
	
	public void setStart(String start){
		this.start = start;
	}
	
	public String getEnd(){
		return end;
	}
	
	public void setEnd(String end){
		this.end = end;
	}
	
	public boolean getAllDay(){
		return allDay;
	}
	
	
	public void setAllDay(boolean allDay){
		this.allDay = allDay;
	}
}
