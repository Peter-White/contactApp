package application.gui;

import application.data.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactController {

	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	@FXML
	private TextField phoneNumberField;
	
	@FXML
	private TextArea notesField;
	
	public void setContact(Contact contact) {
		firstNameField.setText(contact.getFirstName());
		lastNameField.setText(contact.getLastName());
		phoneNumberField.setText(contact.getPhoneNumber());
		
		if(contact.getNotes().equals(" ")) {
			notesField.clear();
		} else {
			notesField.appendText(contact.getNotes());
		}
	}
	
	public void initialize() {
		notesField.setWrapText(true);
	}
	
	public boolean valid() {
		if(firstNameField.getText().isEmpty() || 
			lastNameField.getText().isEmpty() || 
			phoneNumberField.getText().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public Contact processResults() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String phoneNumberFeild = phoneNumberField.getText();
		String notesFeild;
		
		if(notesField.getText().equals("")) {
			notesFeild = " ";
		} else {
			notesFeild = notesField.getText();
		}
		
		Contact contact = new Contact(firstName, lastName, phoneNumberFeild, notesFeild);
		
		return contact;
	}
	
	public Contact editContact(Contact contact) {
		contact.setFirstName(firstNameField.getText());
		contact.setLastName(lastNameField.getText());
		contact.setPhoneNumber(phoneNumberField.getText());
		contact.setNotes(notesField.getText());
		return contact;
	}
	
}
