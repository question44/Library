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
import java.util.Observable;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class AddItemController implements Initializable {
    private User user = null;
    private LoggedInfo loggedInfo = null;
    private Injector injector = null;
    private DBRepoInterface repo = null;
    private FXMLLoader loader = new FXMLLoader();
    
    private ObservableList<String> avalibleTags = FXCollections.observableArrayList();
    private ObservableList<String> tagsForNewItem = FXCollections.observableArrayList();
    private String itemToAdd;
    private String itemToRemove;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox cbItemCategory;
    
    @FXML
    private ComboBox cbItemSubcategory;
    
    @FXML
    private Label lblVar;
    
    @FXML 
    private TextField txtTitle;
    
    @FXML 
    private TextField txtVar;
    
    @FXML 
    private TextArea txtDescription;
    
    @FXML 
    private TextField txtNewTag;
    
    @FXML
    private Button btnNewTag;
    
    @FXML
    private Button btnAddToList;
    
    @FXML
    private Button btnRemoveFromList;
    
    @FXML
    private ListView listTag;
    
    @FXML
    private ListView listTagForItem;
    
    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnBackLog;
    
    @FXML
    private Label lblAdded;
    
    @FXML
    private void btnBackLogAction(ActionEvent event) throws IOException{
        System.out.println("wychodze");
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/MainExtraWindow.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Aplikacja domowej biblioteki");
            MainExtraWindowController controller = loader.<MainExtraWindowController>getController();
            controller.setUser(user);
            controller.setLoggedInfo(loggedInfo);
            stage.show();
    }
    
    @FXML
    private void btnRemoveFromListAction(ActionEvent event){
        try{
            if(tagsForNewItem.size()==0);
            else{
                tagsForNewItem.remove(itemToRemove);
            }
            setNewItemTags();
        }
        catch(NullPointerException ex){
            System.out.println("Pusta lista");
        }
    }
    
    @FXML
    private void btnAddToListAction(ActionEvent event){
        try{
        boolean check = false;
        if(tagsForNewItem.size() == 0){
            tagsForNewItem.add(itemToAdd);
            check = true;
        }
        else{
            for(String x: tagsForNewItem){
                if(x.equals(itemToAdd)){
                    check = true;
                }
            }
        }
        if(check == false){
            tagsForNewItem.add(itemToAdd);
        }
        setNewItemTags();}
        catch(NullPointerException ex){
            System.out.println("pusta lista");
        }
    }
    
    @FXML
    private void btnNewTagAction(ActionEvent event){
        String s = this.txtNewTag.getText();
        boolean check = false;
        for(String x: avalibleTags){
            if(x.equals(s)) check = true;
        }
        if(!check){
            try{
                if(s.charAt(0) == '#' && ((s.charAt(1) >= 'A' && s.charAt(1) <= 'Z') || (s.charAt(1) >= 'a' && s.charAt(1) <= 'z'))){
                    repo.addTag(s);
                    setAvalibleTags();
                }
            }catch(StringIndexOutOfBoundsException ex){
                System.out.println("Nic nie wpisano");
            }
        }
    }
    
    @FXML
    private void btnAddAction(ActionEvent event){
        ItemFactory factory = new ItemFactory();
        int itemType = 0;
        int idItem = 0;
        if(this.cbItemCategory.getValue().toString().equals("Ksiazki")){
            itemType = 1;
        }
        
        if(this.cbItemCategory.getValue().toString().equals("Filmy")){
            itemType = 2;
        }
        
        if(this.cbItemCategory.getValue().toString().equals("Muzyka")){
            itemType = 3;
        }
        Item itemReadyToAdd = factory.createItem(itemType);
        
        itemReadyToAdd.setAvalibility(1);
        itemReadyToAdd.setDescription(txtDescription.getText());
        itemReadyToAdd.setUserId(loggedInfo.getUserId());
        itemReadyToAdd.setTitle(txtTitle.getText());
        itemReadyToAdd.setSubcategoryId(repo.getSubCategoryIdDependsOnName(cbItemSubcategory.getValue().toString()));
        
        itemReadyToAdd.addItem(itemReadyToAdd,txtVar.getText());
        idItem = itemReadyToAdd.getLastId();
        for(String x: tagsForNewItem){
            repo.addAndBindTag(repo.getTagIdDependsOnName(x), idItem, itemType);
        }
        this.btnAdd.setVisible(false);
        this.lblAdded.setVisible(true);
    }
    
    
    public AddItemController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    private void setAvalibleTags(){
        avalibleTags = repo.getAllTags();
        listTag.setItems(avalibleTags);
    }
    
    private void setNewItemTags(){
        listTagForItem.setItems(tagsForNewItem);
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> comboBoxCategoryList = FXCollections.observableArrayList(
                "Ksiazki","Filmy","Muzyka"
        );
        setAvalibleTags();
        
        this.lblVar.setText("");
        this.cbItemCategory.setItems(comboBoxCategoryList);
        this.cbItemCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue.toString().equals("Ksiazki")){
                    lblVar.setText("Autor");
                }
                if(newValue.toString().equals("Muzyka")){
                    lblVar.setText("Zespol");
                }
                if(newValue.toString().equals("Filmy")){
                    lblVar.setText("Rezyser");
                }
                cbItemSubcategory.setItems(repo.getSubCategoriesViaCategoryName(newValue.toString()));
            }
        });
        listTag.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try{
                    itemToAdd = newValue.toString();
                    System.out.println(itemToAdd);
                }
                catch(NullPointerException ex){
                    System.out.println("nic nie wybrano");
                }
            }
        });
        
        listTagForItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try{
                    itemToRemove = newValue.toString();
                    System.out.println(itemToAdd);
                }catch(NullPointerException ex){
                    System.out.println("nic nie wybrano");
                }
            }
        });
    }    
    
    public void setUser(User user){
        this.user = user;
    }
    
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
    }
}
