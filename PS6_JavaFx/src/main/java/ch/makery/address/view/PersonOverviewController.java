package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.UUID;

import base.PersonDAL;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import domain.PersonDomainModel;


public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML Label middleNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        
        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            middleNameLabel.setText(person.getMiddleName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(person.getBirthday().toString());
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            middleNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {

        	
        	UUID perID = selectedPerson.getPersonID();
        	System.out.println("Try to delete: " + perID.toString());
        	
        	//TODO: Delete the person, call the deletePerson(perID) method
        	//		in the DAL
        	 
            personTable.getItems().remove(selectedIndex);
            
            
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
        	//PS6 - Calling the addPerson method
        	PersonDomainModel per = new PersonDomainModel();
        	per.setPersonID(tempPerson.getPersonID());
        	per.setFirstName(tempPerson.getFirstName());
        	per.setMiddleName(tempPerson.getMiddleName());
        	per.setLastName(tempPerson.getLastName());
        	per.setCity(tempPerson.getCity());
        	per.setStreet(tempPerson.getStreet());
        	per.setPostalCode(tempPerson.getPostalCode());
        	per.setBirthday(tempPerson.getBirthday());
        	
        	//TODO: Delete the person, call the addPerson(perID) method
        	//		in the DAL
        	        	
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
            	
            	//PS6 - Calling the updatePerson method
            	PersonDomainModel updatePer = new PersonDomainModel();            	
            	updatePer.setPersonID(selectedPerson.getPersonID());
            	updatePer.setFirstName(selectedPerson.getFirstName());
            	updatePer.setMiddleName(selectedPerson.getMiddleName());
            	updatePer.setLastName(selectedPerson.getLastName());
            	updatePer.setCity(selectedPerson.getCity());
            	updatePer.setStreet(selectedPerson.getStreet());
            	updatePer.setPostalCode(selectedPerson.getPostalCode());
            	updatePer.setBirthday(selectedPerson.getBirthday());
            	

            	
            	//TODO: Delete the person, call the updatePerson(perID) method
            	//		in the DAL
            	
            	
                showPersonDetails(selectedPerson);
                mainApp.RefreshPersonTable();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
}