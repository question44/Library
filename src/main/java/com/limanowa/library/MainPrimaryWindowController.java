/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.limanowa.library.model.Account.Person;
import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.Database.DBRepo;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.DependencyInjector;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class MainPrimaryWindowController implements Initializable {
    private User user = null;
    private LoggedInfo loggedInfo = null;
    private final FXMLLoader loader = new FXMLLoader();
    
    @FXML
    private void btnTagsAction(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Tags.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Lista tagów");
        TagsController controller = loader.<TagsController>getController();
        controller.setLoggedInfo(loggedInfo);
        controller.setUser(user);
        stage.show();
    }
    
    @FXML
    private void btnSearchAction(ActionEvent event){
        
    }
    
    @FXML
    private void btnCatalogAction(ActionEvent event) throws Exception{
        ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Catalog.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Katalog zawartości");
        stage.show();
    }
    
    @FXML
    private void btnLoginAction(ActionEvent event) throws Exception{
       ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Logowanie");
        stage.show();
    }
    
    @FXML
    private void btnRegisterAction(ActionEvent event) throws Exception{
       ((Node)event.getSource()).getScene().getWindow().hide();
        
        loader.setLocation(getClass().getResource("/fxml/Register.fxml"));
        loader.load();
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Rejestracja");
        stage.show();
    }
    
    @FXML
    private void btnEndAction(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
