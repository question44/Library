/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.google.inject.Injector;
import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.InjectorInstance;
import com.limanowa.library.model.other.ApprovalInfo;
import com.limanowa.library.model.other.Item;
import com.limanowa.library.model.other.LoggedInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class TagsController implements Initializable {
    private LoggedInfo loggedInfo = null;
    private User user = null;
    private Item item = null;
    private Injector injector = null;
    private DBRepoInterface repo = null;
    private FXMLLoader loader = new FXMLLoader();
    private ObservableList<String> listOfTags = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ListView listTag;
    
    @FXML
    private ListView listItem;
    
    @FXML
    private Button btnGo;
    
    @FXML 
    private Button btnBack;
    
    @FXML 
    private Button btnLogBack;
    
    @FXML
    private void btnGoAction(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/ItemDetail.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle(item.getTitle());
            ItemDetailController controller = loader.<ItemDetailController>getController();
            controller.setLoggedInfo(loggedInfo);
            controller.setItem(item);
            controller.setUser(user);
            stage.show();
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
    private void btnLogBackAction(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/MainExtraWindow.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Aplikacja domowej biblioteki");
            MainExtraWindowController controller = loader.<MainExtraWindowController>getController();
            controller.setLoggedInfo(loggedInfo);
            controller.setUser(user);
            stage.show();
    }
    
    public TagsController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setList();
    }    
    
    private void setList(){
        listOfTags = repo.getAllTags();
        listTag.setItems(listOfTags);
        listTag.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //listItem.setItems(repo.getItemForTags(newValue.toString()));
            }
        
        });
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
        if(loggedInfo != null){
            if(loggedInfo.isLogged()){
                btnBack.setVisible(false);
                btnLogBack.setVisible(true);
            }else{
                btnBack.setVisible(false);
                btnLogBack.setVisible(true);
            }
        }
        
    } 
}
