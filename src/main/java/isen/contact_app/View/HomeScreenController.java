package isen.contact_app.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import isen.contact_app.Entities.People;
import isen.contact_app.Service.PeopleService;
import isen.contact_app.Dao.PeopleDao;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;


public class HomeScreenController {

    PeopleDao peopleDao = new PeopleDao();
    People people = new People();
    @FXML
    TextField firstNameTxt = new TextField();
    @FXML
    TextField lastNameTxt = new TextField();
    @FXML
    TextField nickNameTxt = new TextField();
    @FXML
    DatePicker birthDayTxt = new DatePicker();
    // TextField birthDayTxt = new TextField();
    @FXML
    TextField emailTxt = new TextField();
    @FXML
    TextField addressTxt = new TextField();
    @FXML
    TextField phoneTxt = new TextField();

    @FXML
    TableView<People> tableView = new TableView<>();

    @FXML
    TableColumn<People,String> firstNameCol= new TableColumn<>("firstname");

    @FXML
    TableColumn<People,String>lastNameCol=new TableColumn<>("lastname");
    @FXML
    TableColumn<People,String> nickNameCol = new TableColumn<>("nickname");
    @FXML
    TableColumn<People,String> phoneNumberCol = new TableColumn<>("phone Number");
    @FXML
    TableColumn<People,String> emailAddressCol = new TableColumn<>("email Address");
    @FXML
    TableColumn<People,String> birthDateCol = new TableColumn<>("birth Date");
    @FXML
    TableColumn<People,String> addressCol = new TableColumn<>("address");
    @FXML
    Button addButton = new Button();

    @FXML
    Button deleteButton = new Button();
    @FXML
    Button exportButton = new Button();



    @FXML
    public void initialize(){
        //
        deleteButton.getStyleClass().setAll("btn","btn-danger");
        addButton.getStyleClass().setAll("btn","btn-success");
        exportButton.getStyleClass().setAll("btn","btn-warning");


        //
        firstNameTxt.setPromptText("First Name");
        lastNameTxt.setPromptText("Last Name");
        nickNameTxt.setPromptText("Nick name");
        emailTxt.setPromptText("Email");
        addressTxt.setPromptText("Address");
        phoneTxt.setPromptText("Phone Number");
        birthDayTxt.setPromptText("Birth Date egs 1999-02-20");


        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        nickNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNickName()));
        phoneNumberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailAddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailAddress()));
        birthDateCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getBirthDate().toString()));

        addressCol.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getAddress()));
//                birthDateCol.setCellValueFactory(cellData-> {
//                    return new Date(Long.valueOf(cellData.getValue().getBirth_date().toString()));
//                });


        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setFirstName(t.getNewValue());
            peopleDao.updatePeople("firstname",t.getNewValue(), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });
        //Sets cellFactory to be able to edit the last name column
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setLastName(t.getNewValue());
            peopleDao.updatePeople("lastname",t.getNewValue(), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });
        nickNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nickNameCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setNickName(t.getNewValue());
            peopleDao.updatePeople("nickname",t.getNewValue(), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setNickName(t.getNewValue());
            peopleDao.updatePeople("phoneNumber",t.getNewValue(), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });
        emailAddressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailAddressCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setMailAddress(t.getNewValue());
            peopleDao.updatePeople("emailAddress",t.getNewValue(), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setAddress(t.getNewValue());
            peopleDao.updatePeople("address",t.getNewValue(), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });
        birthDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        birthDateCol.setOnEditCommit(t -> {
            t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setBirthDate(Date.valueOf(t.getNewValue()));
            peopleDao.updatePeopleDate("birthDate",Date.valueOf(t.getNewValue()), t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).getId());
        });



        tableView.getColumns().addAll(firstNameCol,lastNameCol,nickNameCol,phoneNumberCol,emailAddressCol,addressCol,birthDateCol);

        tableView.setItems(PeopleService.getPeople());

        addButton.setOnAction(e -> {
            System.out.println(birthDayTxt.getValue());
            PeopleService.addPeople(new People(0,firstNameTxt.getText(),lastNameTxt.getText(),nickNameTxt.getText(),
                    phoneTxt.getText(),addressTxt.getText(),emailTxt.getText(),  Date.valueOf((birthDayTxt.getValue()))));
            firstNameTxt.clear();lastNameTxt.clear();nickNameTxt.clear();phoneTxt.clear();addressTxt.clear();
            addressTxt.clear();emailTxt.clear();
            tableView.setItems(PeopleService.getPeople());
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, people, t1) -> {
            if(tableView.getSelectionModel().getSelectedItem() !=null)
                deleteButton.setDisable(false);
            this.people=tableView.getSelectionModel().getSelectedItem();
        });
        this.people=null;
        deleteButton.setDisable(true);
        deleteButton.setOnAction(e-> PeopleService.deletePeople(people));
        exportButton.setOnAction(e-> {
            for(People p:PeopleService.getPeople())
                PeopleService.exportToFile(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data Exported", ButtonType.OK);


            alert.showAndWait();


        });


    }



}

