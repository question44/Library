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
public class ApprovalController implements Initializable {
    private LoggedInfo loggedInfo = null;
    private User user = null;
    private Injector injector = null;
    private ApprovalInfo toApprove = null;
    private DBRepoInterface repo = null;
    private FXMLLoader loader = new FXMLLoader();
    private ObservableList<ApprovalInfo> tableList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<ApprovalInfo> tableView;
    
    @FXML 
    private TableColumn<ApprovalInfo,String> username;
    
    @FXML 
    private TableColumn<ApprovalInfo,String> category;
    
    @FXML 
    private TableColumn<ApprovalInfo,String> title;
    
    @FXML
    private Button btnApprove;
    
    @FXML 
    private Button btnBack;
    
    @FXML
    private Button btnReturn;
   
    @FXML
    private Button btnRefuse;
    
    @FXML
    private void btnReturnAction(ActionEvent envent) throws Exception{
        repo.sendReturnMessage(toApprove,user.getLogin());
        this.btnReturn.setVisible(true);
        this.btnRefuse.setVisible(false);
        this.btnApprove.setVisible(false);
    }
    
    @FXML
    private void btnRefuseAction(ActionEvent envent) throws Exception{
        repo.sendRefuseMessage(toApprove,user.getLogin());
        repo.deleteOrder(toApprove.getOrderId());
        repo.setAvalibity(repo.getItemsCategory(toApprove.getOrderId()), toApprove.getItemId(), 1);
        setTable();
        this.btnReturn.setVisible(false);
        this.btnRefuse.setVisible(false);
        this.btnApprove.setVisible(false);
        
    }
    
    @FXML
    private void btnApproveAction(ActionEvent event) throws Exception{
        SetOrderInfo info = new SetOrderInfo();
        if(toApprove.getCategory().equals("Ksiazki")){
            info.setCategoryId(1);
        }
        if(toApprove.getCategory().equals("Filmy")){
            info.setCategoryId(2);
        }
        if(toApprove.getCategory().equals("Muzyka")){
            info.setCategoryId(3);
        }
        info.setOrderId(toApprove.getOrderId());
        info.setOwnerId(toApprove.getOwnerId());
        info.setItemId(toApprove.getItemId());
        info.setUserId(toApprove.getUsername());
        repo.setsetOrder(info);
        this.btnReturn.setVisible(true);
        this.btnRefuse.setVisible(false);
        this.btnApprove.setVisible(false);
    }
    
    @FXML
    private void btnBackAction(ActionEvent event) throws Exception{
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
    
    public ApprovalController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }   
    
    private void setTable(){
        this.username.setCellValueFactory(new PropertyValueFactory<ApprovalInfo, String>("username"));
        this.category.setCellValueFactory(new PropertyValueFactory<ApprovalInfo, String>("category"));
        this.title.setCellValueFactory(new PropertyValueFactory<ApprovalInfo, String>("title"));
        this.tableList = repo.getAllOrders(loggedInfo.getUserId());
        this.tableView.setItems(this.tableList);
        
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                toApprove = tableView.getSelectionModel().getSelectedItem();
                try{
                    if(repo.checkOrder(toApprove)){
                        btnApprove.setVisible(false);
                        btnReturn.setVisible(true);
                        btnRefuse.setVisible(false);
                    }
                    else{
                        btnApprove.setVisible(true);
                        btnReturn.setVisible(false);
                        btnRefuse.setVisible(true);
                    }
                }catch(NullPointerException ex){
                    System.out.println("Nic nie jest wybrane");
                }
            }
        });
    }
    
    public void setUser(User user){
        this.user = user;
    }
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
        setTable();
    }   
}
