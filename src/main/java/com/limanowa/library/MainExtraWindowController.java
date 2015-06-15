package com.limanowa.library;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.inject.Injector;
import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.InjectorInstance;
import com.limanowa.library.model.other.LoggedInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class MainExtraWindowController implements Initializable {
    private Injector injector = null;
    private DBRepoInterface repo = null;
    
    private final FXMLLoader loader = new FXMLLoader();
    private LoggedInfo loggedInfo = new LoggedInfo();
    private User user;
    @FXML
    private Label lblWelcome;
    
    @FXML
    public TextField txtSearch;
    
    @FXML
    private Button btnApproval;
    
    @FXML
    private Button btnMessages;
    
    @FXML
    private Button btnAddItem;
    
    
    @FXML
    private void btnMessagesAction(ActionEvent event) throws IOException{
        
        repo.UpdateReadedMessage(user.getLogin());
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Messages.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Wiadomosci");
        MessagesController controller = loader.<MessagesController>getController();
        controller.setLoggedInfo(loggedInfo);
        controller.setUser(user);
        stage.show();
    }
    
    @FXML 
    private void btnApprovalAction(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Approval.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Lista do zatwierdznia");
        ApprovalController controller = loader.<ApprovalController>getController();
        controller.setLoggedInfo(loggedInfo);
        controller.setUser(user);
        stage.show();
    }
    
    @FXML
    private void btnEndAction(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    private void btnAddItemAcition(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/AddItem.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Dodaj produkt");
        AddItemController controller = loader.<AddItemController>getController();
        controller.setLoggedInfo(loggedInfo);
        controller.setUser(user);
        stage.show();
    }
    
    @FXML
    private void btnCatalogAction(ActionEvent event) throws Exception{
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Catalog.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Katalog produktów");
        CatalogController controller = loader.<CatalogController>getController();
        controller.setLoggedInfo(loggedInfo);
        controller.setUser(user);
        stage.show();   
    }
    
    @FXML
    private void btnMyItemAction(ActionEvent event) throws Exception{
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/MyItem.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Moje wypożyczenia");
        MyItemController controller = loader.<MyItemController>getController();
        controller.setLoggedInfo(loggedInfo);
        controller.setUser(user);
        stage.show();   
    }
    
    @FXML
    private void btnLogOutAction(ActionEvent event) throws Exception{
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
    private void btnSearchAction(ActionEvent event){
        System.out.println(user.getClass());
    }
    
    public MainExtraWindowController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void setUser(User user) {
        this.user = user;
        this.lblWelcome.setText("Witaj "+this.user.getFirstName());
        if(repo.CheckMessages(user.getLogin())){
            this.btnMessages.setStyle("-fx-border-style:solid; -fx-border-width:3px; -fx-border-color:green");
        }
        System.out.println(user.getClass().getName());
        if(user.getClass().getName().equals("com.limanowa.library.model.Account.ParentUser")){
            this.btnAddItem.setVisible(true);
        }
    }
    public void setLoggedInfo(LoggedInfo loggedInfo){
        this.loggedInfo = loggedInfo;
    }
}
