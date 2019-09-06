
public interface Schedulable {
	public RecurrenceType recurrable();
	
	public int countOccurences();
	
	public String toString();
		
	public void updateEvent();
}

enum RecurrenceType{
	
	NONE,
	DAILY,
	WEEKLY,
	MONTHLY;
	
	public static RecurrenceType getTypeForString(String aString) {
			
		if(aString.equalsIgnoreCase(DAILY.name())) {
			return DAILY;
		}
		else if(aString.equalsIgnoreCase(WEEKLY.name())) {
			return WEEKLY;
		}
		else if(aString.equalsIgnoreCase(NONE.name())) {
			return NONE;
		}
		else if(aString.equalsIgnoreCase(MONTHLY.name())) {
			return MONTHLY;
		}
		else {
			throw new IllegalArgumentException("Invalid Option, Please try again.");
		}
	}
}