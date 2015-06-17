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
import com.limanowa.library.model.ItemFactory.ItemFactory;
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
public class SearchController implements Initializable {
    private LoggedInfo loggedInfo = null;
    private User user = null;
    private Item item = null;
    private Injector injector = null;
    private DBRepoInterface repo = null;
    private FXMLLoader loader = new FXMLLoader();
    private ObservableList<String> listOfItems = FXCollections.observableArrayList();
    private String searchText;
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnLogBack;
    
    @FXML
    private Button btnGo;
    
    @FXML
    private ListView listView;
    
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
            controller.setFrom("Wyszukiwarka");
            controller.setSearch(searchText);
            stage.show();
    }
    
    public SearchController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private void setList(){
        listOfItems = repo.getSearchedItems(this.searchText);
        listView.setItems(listOfItems);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int type = repo.getCategoryIdForPassFromTagWindow(newValue.toString());
                ItemFactory factory = new ItemFactory();
                item = factory.createItem(type);
                item.fillInformation(newValue.toString());
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
    public void setSearchText(String text){
        this.searchText = text;
        setList();
    }
}
