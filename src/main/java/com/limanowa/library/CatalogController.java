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
import com.limanowa.library.model.other.SubCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeCell;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class CatalogController implements Initializable {
    private Injector injector = null;
    private DBRepoInterface repo = null;
    private Item item;
    private int categoryToPass;
    private LoggedInfo loggedInfo;
    private User user;
    
    FXMLLoader loader = new FXMLLoader();
    
    @FXML 
    private Button btnDetail;
    
    @FXML
    private Button btnBack;
    
    @FXML 
    private Button btnBackLog;
    
    @FXML
    private TreeView<String> treeView;
    
    @FXML 
    private ListView<String> listView;
    
    @FXML
    private void tvMouseClickedAction(ActionEvent event) throws Exception{}
    
    @FXML
    private void btnBackAction(ActionEvent event) throws Exception{
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
    private void btnDetailClick(ActionEvent event) throws Exception{
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
    private void btnBackLogAction(ActionEvent event) throws Exception{
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
    
    public CatalogController(){
        loggedInfo = new LoggedInfo();
        categoryToPass = 0;
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnBackLog.setVisible(false);
        this.btnDetail.setVisible(false);
        setTree(repo.getSubCategories());
    }
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
        if(this.loggedInfo.isLogged()){
            this.btnBack.setVisible(false);
            this.btnBackLog.setVisible(true);
        }
        else{
            this.btnBackLog.setVisible(false);
        }
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public void setTree(List<SubCategory> list){
        TreeItem<String> rootNode = new TreeItem<String>("Biblioteka");
        rootNode.setExpanded(true);
        for(SubCategory s : list){
            TreeItem<String> empLeaf = new TreeItem<String>(s.getName());
            boolean found = false;
            for(TreeItem<String> catNode : rootNode.getChildren()){
                if(catNode.getValue().contentEquals(s.getCategory())){
                    catNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if(!found){
                TreeItem<String> catNode = new TreeItem<String>(s.getCategory());
                rootNode.getChildren().add(catNode);
                catNode.getChildren().add(empLeaf);
            }
        }
        
        this.treeView.setRoot(rootNode);
        treeViewChange();
    }
    
    private void treeViewChange(){
        try{
            this.treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                    ObservableList<String> list = FXCollections.observableArrayList();
                    try{
                        if(selectedItem.getValue().equals("Biblioteka")){
                           list = FXCollections.observableArrayList();
                           list.add("Wybierz kategorie ...");
                        }
                        
                            if(selectedItem.getParent().getValue().equals("Biblioteka")){
                                if(selectedItem.getValue().equals("Ksiazki")){
                                    list = repo.getAllBooks();
                                    categoryToPass = 1;
                                }
                                if(selectedItem.getValue().equals("Filmy")){
                                    list = repo.getAllMovies();
                                    categoryToPass = 2;
                                }
                                if(selectedItem.getValue().equals("Muzyka")){
                                    list = repo.getAllAlbums();
                                    categoryToPass = 3;
                                }
                            }
                        
                        if(selectedItem.getParent().getValue().equals("Ksiazki")){
                            list = repo.getBooks(selectedItem.getValue().toString());
                            categoryToPass = 1;
                        }
                        if(selectedItem.getParent().getValue().equals("Filmy")){
                            list = repo.getMovies(selectedItem.getValue().toString());
                            categoryToPass = 2;
                        }
                        if(selectedItem.getParent().getValue().equals("Muzyka")){
                            list = repo.getAlbums(selectedItem.getValue().toString());
                            categoryToPass = 3;
                        }    
                    }catch(NullPointerException ex){
                        System.out.println("nic nie jest wybrane");
                    }
                    listView.setItems(list);
                }
            });
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
        
        this.listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue.toString().equals(""));
                else{
                    btnDetail.setVisible(true);
                }
                System.out.println(newValue.toString());
                ItemFactory factory = new ItemFactory();
                item = factory.createItem(categoryToPass);
                item.fillInformation(newValue.toString());
            }
        });
    }
}
