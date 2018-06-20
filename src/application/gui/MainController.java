package application.gui;

import java.io.IOException;
import java.util.Optional;

import application.data.Contact;
import application.data.ContactData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class MainController {
	
	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private TableView<Contact> contactTable;
	
	@FXML
	private ContextMenu contactContext;
	
	ContactData data;
	
	public void initialize() {
		 data = new ContactData();
		 data.loadContacts();
		 contactTable.setItems(data.getContacts());
		 contactTable.getSelectionModel().select(0);
	}
	
	@FXML
	public void newContactDialog() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Contact");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("createContactDialog.fxml"));
		
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the dialog");
			e.printStackTrace();
			return;
		}
		
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        	ContactController contactController = fxmlLoader.getController();
        	Contact contact = contactController.processResults();
        	data.addContact(contact);
        	try {
				data.saveContacts();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	contactTable.setItems(data.getContacts());
        	contactTable.getSelectionModel().select(contact);
        }
	}
	
	@FXML
	public void edit() {
		Contact contact = data.getContact((contactTable.getSelectionModel().getSelectedItem()));
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Edit Contact");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("createContactDialog.fxml"));
		
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the dialog");
			e.printStackTrace();
			return;
		}
		
		ContactController contactController = fxmlLoader.getController();
		contactController.setContact(contact);
		
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        	try {
        		contact = contactController.editContact(contact);
				data.saveContacts();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	contactTable.setItems(data.getContacts());
        	contactTable.getSelectionModel().select(contact);
        }
	}
	
	@FXML
	public void close() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Quit");
		dialog.setHeaderText("Are you sure you want to quit?");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("exitConfirmDialog.fxml"));
		
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
		
		Optional<ButtonType> result = dialog.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.YES) {
        	Platform.exit();
        }
	}
	
	@FXML
	public void delete() {
		Contact contact = contactTable.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: " + contact.getFirstName() + " " + contact.getLastName());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            data.deleteContact(contact);
            try {
				data.saveContacts();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
