import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class Meeting extends Event {


	ArrayList<Date> _Reoccurrences = new ArrayList<Date>();
	public Meeting(Date aStartDate, Date aEndDate, RecurrenceType aRecurrence, String aName, String aDescription) {
		super(aStartDate, aEndDate, aRecurrence, aName, aDescription);
		this.set_Occurrences(countOccurences());
	}
	
	@Override
	public int countOccurences() {
		int tOccurrences = 1 ;
		int tDaysTotal = (int) ChronoUnit.DAYS.between(_StartDate.toInstant(),_EndDate.toInstant());
		if(this._Recurrable == RecurrenceType.WEEKLY) {
			tOccurrences = tDaysTotal/7;
			int tLeftOver = tDaysTotal %7;
			int distance =  this._Day - _StartDate.getDay();
			if(distance == 0) {
				//1 week
				tOccurrences += 1;
			}
			else if(distance > 0) {
				//leftOver days include day we are looking for
				if (tLeftOver > distance) {
					tOccurrences += 1;
				}
			}
			else if(distance < 0) {
				distance += 7;
				if(tLeftOver > distance) {
					tOccurrences += 1;
				}
			}
			return tOccurrences;
		}
		else if(this._Recurrable == RecurrenceType.DAILY) {
			return tDaysTotal > 0 ? tDaysTotal : tOccurrences;
		}
		else if(this._Recurrable == RecurrenceType.MONTHLY) {
			tDaysTotal = tDaysTotal/30;
			 //(int) _StartDate.toInstant().until(_EndDate.toInstant(), ChronoUnit.MONTHS);
			 //(int) ChronoUnit.MONTHS.between(_StartDate.toInstant(), _EndDate.toInstant());
			 return tDaysTotal > 0 ? tDaysTotal : tOccurrences;
		}
		//occurs only once
		return tOccurrences;
	}

	@Override
	public String toString() {
		StringBuilder tEvent = new StringBuilder();
		tEvent.append("Name: " + this._Name + "\n");
		tEvent.append("Description: " + this._Description + "\n");
		Format sdf = new SimpleDateFormat("MM/dd/yyyy");
		tEvent.append("From " + sdf.format(this._StartDate) +"\n");
		tEvent.append("To " + sdf.format(this._EndDate) +"\n");
		if(this._Recurrable != RecurrenceType.NONE) {
			tEvent.append("Recurrence Type: "+this._Recurrable);
		}
		return tEvent.toString();
	}
	

	@Override
	public void updateEvent() {
		this._Name = JOptionPane.showInputDialog("Enter Name: ", this._Name);
		Format sdf = new SimpleDateFormat("MM/dd/yyyy");
		String tSDate = JOptionPane.showInputDialog("Enter Start Date: ", sdf.format(this._StartDate));
		String tEDate = JOptionPane.showInputDialog("Enter End Date: ", sdf.format(this._EndDate));
		this._Description = JOptionPane.showInputDialog("Enter Description: ", this._Description);
		this._Recurrable = RecurrenceType.getTypeForString(JOptionPane.showInputDialog(
				"Enter Recurrence (none, daily, weekly, monthly):", this._Recurrable.toString()));
		Calendar tCalendar = Calendar.getInstance();
		String[] tDateArr = tSDate.split("/");
		tCalendar.set(Integer.parseInt(tDateArr[0]), Integer.parseInt(tDateArr[1])-1, Integer.parseInt(tDateArr[2]));
		this._StartDate = tCalendar.getTime();
		tDateArr = tEDate.split("/");
		tCalendar.set(Integer.parseInt(tDateArr[0]), Integer.parseInt(tDateArr[1])-1, Integer.parseInt(tDateArr[2]));
		this._EndDate = tCalendar.getTime();
		set_Occurrences(_Occurrences);
	}

}
