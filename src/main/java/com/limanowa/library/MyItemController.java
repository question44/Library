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
import com.limanowa.library.model.other.LoggedInfo;
import com.limanowa.library.model.other.SetOrderInfo;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class MyItemController implements Initializable {
    private LoggedInfo loggedInfo = null;
    private User user = null;
    private Injector injector = null;
    private DBRepoInterface repo = null;
    final private FXMLLoader loader = new FXMLLoader();
    private ObservableList<SetOrderInfo> tableList = FXCollections.observableArrayList();
    private SetOrderInfo setOrderInfo;
    
    
    @FXML
    private Button btnReturn;
    
    @FXML
    private Button btnBackLog;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private TableView<SetOrderInfo> tableView;
    
    @FXML
    private TableColumn<SetOrderInfo,String> colTitle;
    
    @FXML
    private TableColumn<SetOrderInfo,String> colCategory;
    
    @FXML
    private TableColumn<SetOrderInfo,String> colOwner;
    
    @FXML
    private void btnReturnAction(ActionEvent event) throws Exception{
        repo.RemoveFromOrders(setOrderInfo.getOrderId());
        repo.RemoveFromSetOrders(setOrderInfo.getOrderId());
        repo.setAvalibity(setOrderInfo.getCategoryId(), setOrderInfo.getItemId(), 1);
        setTable();
    }
    
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
    
    public MyItemController(){
        this.setOrderInfo = new SetOrderInfo();
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnBackLog.setVisible(false);
        this.btnReturn.setVisible(false);
    }
    
    private void setTable(){
        this.colOwner.setCellValueFactory(new PropertyValueFactory<SetOrderInfo, String>("username"));
        this.colCategory.setCellValueFactory(new PropertyValueFactory<SetOrderInfo, String>("category"));
        this.colTitle.setCellValueFactory(new PropertyValueFactory<SetOrderInfo, String>("title"));
        this.tableList = repo.getAllSetOrders(user.getLogin());
        this.tableView.setItems(this.tableList);
        
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setOrderInfo = tableView.getSelectionModel().getSelectedItem();
                btnReturn.setVisible(true);
            }
        });
    }
    
    public void setUser(User user){
        this.user = user;
        setTable();
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
}
