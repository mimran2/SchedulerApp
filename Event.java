import java.util.Date;

enum EventType{
	MEETING,
	EVENT;
}
public abstract class Event implements Schedulable {

	
	
	RecurrenceType _Recurrable; 
	Date _StartDate;
	Date _EndDate;
	String _Description;
	String _Name;
	int _Day;
	int _Occurrences = 1;

	public Event(Date aStartDate, Date aEndDate, RecurrenceType aRecurrence, String aName, String aDescription) {
		this._Description = aDescription;
		this._Name = aName;
		this._StartDate = aStartDate;
		this._EndDate = aEndDate;
		this._Recurrable = aRecurrence;
		this._Day = aStartDate.getDay();
	}
	
	abstract public int countOccurences();

	abstract public String toString();

	abstract public void updateEvent();
	
	@Override
	public RecurrenceType recurrable() {
		return _Recurrable;
	}

	public Date get_StartDate() {
		return _StartDate;
	}

	public void set_StartDate(Date _StartDate) {
		this._StartDate = _StartDate;
	}

	public Date get_EndDate() {
		return _EndDate;
	}

	public void set_EndDate(Date _EndDate) {
		this._EndDate = _EndDate;
	}

	public String get_Description() {
		return _Description;
	}

	public void set_Description(String _Description) {
		this._Description = _Description;
	}

	public String get_Name() {
		return _Name;
	}

	public void set_Name(String _Name) {
		this._Name = _Name;
	}

	public int get_Day() {
		return _Day;
	}

	public void set_Day(int _Day) {
		this._Day = _Day;
	}
	
	public int get_Occurrences() {
		return _Occurrences;
	}

	public void set_Occurrences(int _Occurrences) {
		this._Occurrences = _Occurrences;
	}
	
}
