/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.google.inject.Injector;
import com.limanowa.library.model.Account.AccountFactory.AccountFactory;
import com.limanowa.library.model.Account.Person;
import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.InjectorInstance;
import com.limanowa.library.model.other.LoggedInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class LoginController implements Initializable {
    private DBRepoInterface  repo = null;
    private Injector injector = null;
    int accountType;
    @FXML
    private Label txtError;
    
    @FXML
    private TextField txtUsername;
    
    @FXML 
    private TextField txtPassword;
    //private User user;
    
    @FXML
    private void btnLoginAction(ActionEvent event) throws Exception{
        Person person = new Person();
        User user;
        person.setLogin(this.txtUsername.getText());
        person.setPassword(this.txtPassword.getText());
        
        accountType = repo.CheckUser(person);
        System.out.println("type "+accountType);
        if(accountType == -1){
            this.txtError.setVisible(true);
        }
        else{
            AccountFactory factory = new AccountFactory();
            user = factory.createAccount(accountType);
            user.fillInformation(repo.getData(person));
            
            LoggedInfo loggedInfo = new LoggedInfo();
            loggedInfo.setLogged(true);
            loggedInfo.setUserId(user.getUserId());
            
            System.out.println(user.getFirstName());
            System.out.println(user.getUserId());
            
            ((Node)event.getSource()).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainExtraWindow.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Witamy po zalogowaniu");
            MainExtraWindowController controller = loader.<MainExtraWindowController>getController();
            controller.setUser(user);
            controller.setLoggedInfo(loggedInfo);
            stage.show();
        }
    }
    
    @FXML
    private void btnBackAction(ActionEvent event) throws Exception{
        
        ((Node)event.getSource()).getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainPrimaryWindow.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Aplikacja domowej biblioteki");
        stage.show();
    }
    
    public LoginController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
