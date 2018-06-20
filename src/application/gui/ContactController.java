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
		notesField.appendText(contact.getNotes());
	}
	
	public void initialize() {
		notesField.setWrapText(true);
		System.out.println("Run");
	}
	
	public Contact processResults() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String phoneNumberFeild = phoneNumberField.getText();
		String notesFeild = notesField.getText();
		
		Contact contact = new Contact(firstName, lastName, phoneNumberFeild, notesFeild);
		
		return contact;
	}
	
}
