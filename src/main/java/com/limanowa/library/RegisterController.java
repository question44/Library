/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.google.inject.Injector;
import com.limanowa.library.model.Account.Person;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.InjectorInstance;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class RegisterController implements Initializable {

    private final FXMLLoader loader = new FXMLLoader();
    Injector injector;
    @FXML
    private ComboBox comboBoxCategory;
    
    @FXML
    private TextField txtFirstname;
    
    @FXML
    private TextField txtLastname;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtBirthDay;
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private TextField txtPassword;

    public RegisterController() {
        this.injector = new InjectorInstance().getInstance();
    }
    
    @FXML
    private void btnBackAction(ActionEvent event) throws IOException{
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        loader.setLocation(getClass().getResource("/fxml/MainPrimaryWindow.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Aplikacja domowej biblioteki");
        stage.show();
    }
    
    @FXML
    private void btnRegisterAction(ActionEvent event) throws IOException{
        DBRepoInterface repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
        
        Person person = new Person();
        person.setFirstName(this.txtFirstname.getText());
        person.setLastName(this.txtLastname.getText());
        person.setBirthDate((this.txtBirthDay.getText()));
        person.setEmail(this.txtEmail.getText());
        person.setLogin(this.txtLogin.getText());
        person.setPassword(this.txtPassword.getText());
        
        if(this.comboBoxCategory.getValue().toString().equals("Rodzic")){
            person.setAccountType(3);
        }
        if(this.comboBoxCategory.getValue().toString().equals("Dziecko")){
            person.setAccountType(2);
        }
        if(this.comboBoxCategory.getValue().toString().equals("Spoza domu")){
            person.setAccountType(4);
        }
        
        try{
            if(!repo.AddUser(person)){
            } else {
                ((Node)event.getSource()).getScene().getWindow().hide();
                
                loader.setLocation(getClass().getResource("/fxml/RegisterMessage.fxml"));
                loader.load();
                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.setTitle("Potwierdzenie rejestracji");
                stage.show();
            }
        }catch(IllegalArgumentException e){
            System.out.println("");
        }      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> comboBoxList = FXCollections.observableArrayList(
                "Rodzic","Dziecko","Spoza domu"
        );
        comboBoxCategory.setItems(comboBoxList);
    }    
    
}
