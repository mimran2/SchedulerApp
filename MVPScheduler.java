import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class MVPScheduler {
	static ArrayList<Event> _MeetingsList = new ArrayList<Event>();
	static ArrayList<Event> _EventsList = new ArrayList<Event>();
	public static void main(String[] args) {
		int tOptionChoice = 0;
		
		do {
			try {
				tOptionChoice = Integer.parseInt(JOptionPane.showInputDialog(getMenu()));
				handleChoice(tOptionChoice);
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid choice");
			}
			
		}
		while (tOptionChoice != 0);

	}
	
	public static String getMenu() {
		StringBuilder tMenuBuilder = new StringBuilder();
		tMenuBuilder.append("\t\t\t\t\t\t\t\t\tScheduler Menu \n");
		tMenuBuilder.append("Select an option(1-8) to proceed or 0 to exit \n");
		tMenuBuilder.append("1. Schedule a Meeting \n");
		tMenuBuilder.append("2. Schedule an Event (ex: Holidays/Vacations) \n");
		tMenuBuilder.append("3. View All Meetings \n");
		tMenuBuilder.append("4. View All Events \n");
		tMenuBuilder.append("5. Edit Meeting \n");
		tMenuBuilder.append("6. Edit Events \n");
		tMenuBuilder.append("7. Get Meeting totals \n");
		tMenuBuilder.append("8. Get Event totals \n");



		
		return tMenuBuilder.toString();
	}
	
	public static void handleChoice(int aChoice) {
		switch (aChoice) {
		case 1:
			scheduleEvent(EventType.MEETING);
			break;
		case 2:
			scheduleEvent(EventType.EVENT);
			break;
		case 3:
			JOptionPane.showMessageDialog(null, viewEvent(EventType.MEETING));
			break;
		case 4:
			JOptionPane.showMessageDialog(null, viewEvent(EventType.EVENT));
			break;
		case 5:
			editEvent(EventType.MEETING);
			break;
		case 6:
			editEvent(EventType.EVENT);
			break;
		case 7:
			getTotals(EventType.MEETING);
			break;
		case 8:
			getTotals(EventType.EVENT);
		}
	}
	
	public static void scheduleEvent(EventType aEventType ) {
		String tName = JOptionPane.showInputDialog(aEventType.name() + " Name: ");
		String tDescription = JOptionPane.showInputDialog("Description: ");
		Date tStartDate = getDateInput("a Start");
		Date tEndDate = getDateInput("an End");
		RecurrenceType tRecurrence = getRecurrenceInput();
		
		Meeting tMeeting = new Meeting(tStartDate, tEndDate, tRecurrence, tName, tDescription);
		if(tRecurrence == RecurrenceType.WEEKLY) {
			int tDOW = 0;
			String tDay = JOptionPane.showInputDialog("Enter Day for occurence (Mon, Tue, Wed, Thu, Fri, Sat, Sun): ");
			switch(tDay) {
			case "Mon":
				tDOW = 2;
				break;
			case "Tue":
				tDOW = 3;
				break;
			case "Wed":
				tDOW = 4;
				break;
			case "Thu":
				tDOW = 5;
				break;
			case "Fri":
				tDOW = 6;
				break;
			case "Sat":
				tDOW = 7;
				break;
			case "Sun":
				tDOW = 0;
				break;
			}
			tMeeting.set_Day(tDOW);
		}
		if(aEventType == EventType.EVENT) {
			_EventsList.add(tMeeting);
		}
		else {
			_MeetingsList.add(tMeeting);

		}
		
	}
	
	//start or end date is the type 
	public static Date getDateInput(String aType) {
		int tMonth = -1;
		int tDate = 0;
		int tYear = 0;
		Calendar tCalendarDate = Calendar.getInstance();
		String tDateFull = "";
		while(tMonth == -1 || tDate == 0 || tYear == 0) {
			try{
				tDateFull = JOptionPane.showInputDialog("Enter " + aType + " date (mm/dd/yyyy) or press enter to use today's date: ");		
				String[] tDateArr = tDateFull.split("/");
				tMonth = Integer.parseInt(tDateArr[0]);
				tDate = Integer.parseInt(tDateArr[1]);
				tYear = Integer.parseInt(tDateArr[2]);
			}

			catch(NumberFormatException e) {
				if(tDateFull.equals("")) {
					return new Date();
				}
				JOptionPane.showMessageDialog(null, "Invalid choice: " + e.toString());
			}
		}
		tCalendarDate.set(tYear, tMonth-1, tDate);
		return tCalendarDate.getTime();
	}
	
	public static RecurrenceType getRecurrenceInput() {
		RecurrenceType tRecurrenceType = null;
		
		while(tRecurrenceType == null) {
			String tInput = JOptionPane.showInputDialog("Select recurrence type: None, Daily, Weekly, Monthly");
			
			try{
				tRecurrenceType = RecurrenceType.getTypeForString(tInput);
			}
			catch(IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
		return tRecurrenceType;
		
	}
	
	public static String viewEvent(EventType aType) {
		StringBuilder tStringEvents = new StringBuilder();
		tStringEvents.append("View All "+aType +"\n");
		int tIndex = 0;
		for (Event tEvent : (aType == EventType.EVENT ? _EventsList : _MeetingsList)) {
			tStringEvents.append(tIndex +". " + tEvent.toString() + "\n");
			tIndex++;
		}
		return tStringEvents.toString();
	}

	public static void editEvent(EventType aType) {
		String tEvents = viewEvent(aType);
		//remove View All header
		tEvents = tEvents.substring(tEvents.indexOf("\n")+1);
		if(tEvents.isEmpty()){
			return;
		}
		Integer tIndex = null;
		while(tIndex == null) {
			try {
				tIndex = Integer.parseInt(JOptionPane.showInputDialog("Select Index of Event to Edit: \n" + tEvents));
			}
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Entry, Please try again");
			}
			if (tIndex != null) {
				if(tIndex < 0 || tIndex >= (aType == EventType.EVENT ? _EventsList.size() : _MeetingsList.size())) {
					JOptionPane.showMessageDialog(null, "Invalid Index, Please try again");
				}
			}	
		}
		if(aType == EventType.EVENT) {
			_EventsList.get(tIndex).updateEvent();
		}
		else {
			
			_MeetingsList.get(tIndex).updateEvent();
		}
			
	}
	
	public static void getTotals(EventType aType) {
		StringBuilder tEventTotalStr = new StringBuilder();
		tEventTotalStr.append(aType.name() + " Totals \n");
		int tOverallTotals = 0;
		for(Event tEvent : aType == EventType.MEETING ? _MeetingsList : _EventsList) {
			tOverallTotals += tEvent.get_Occurrences();
			tEventTotalStr.append(tEvent.get_Name() + ": " + tEvent.get_Occurrences() +" \n");
		}
		tEventTotalStr.append("Grand Total: "+ tOverallTotals);
		JOptionPane.showMessageDialog(null, tEventTotalStr.toString());
	}
	
	
	
	

}
