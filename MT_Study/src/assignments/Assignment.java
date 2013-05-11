/**
 * 
 */
package assignments;

/**
 * @author Chris
 *
 */
public class Assignment {

	private String Title;
	private String date_due;
	private int identifier;
	
	
	public Assignment() {
		Title = "";
		date_due = "";
		identifier = 0;
	}
	
	public Assignment(String Title, String description, String date_due, String section_covered) {
		this.Title = Title;
		this.date_due = date_due;
	}
	
	public String getTitle() {
		return Title;
	}
	
	public void setTitle(String title) {
		Title = title;
	}
	

	public String getDate_due() {
		return date_due;
	}
	
	public void setDate_due(String date_due) {
		this.date_due = date_due;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
}
